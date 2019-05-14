package cc.vimc.bot.impl;

import cc.vimc.bot.dto.*;
import cc.vimc.bot.model.BotMemory;
import cc.vimc.bot.provider.ImageRec;
import cc.vimc.bot.dao.MessageDAO;

import cc.vimc.bot.mapper.BotMemoryMapper;
import cc.vimc.bot.provider.TranslateUtil;
import cc.vimc.bot.provider.Tuling123;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StopWatch;


import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import static cc.vimc.bot.enums.Commands.*;
import static cc.vimc.bot.enums.Fields.GROUP;
import static cc.vimc.bot.enums.Fields.PRIVATE;


@Service
public class BotEventImpl {

    private static final Logger logger = LoggerFactory.getLogger(BotEventImpl.class);
    @Autowired
    BotApiImpl botApi;

    @Autowired
    MinecraftImpl minecraft;
    @Resource
    MessageDAO messageDAO;
    @Autowired
    BotMemoryMapper botMemoryMapper;

    @Value("${MASTER_QQ}")
    String masterQQ;

    @Value("${MC_GROUP_QQ}")
    String mcGroupQQ;
    private Cache<String, Integer> cacheJP = CacheBuilder.newBuilder()
            .expireAfterAccess(5, TimeUnit.MINUTES)
            .build();

    private Cache<String, Integer> cacheTulingOut = CacheBuilder.newBuilder()
            .expireAfterWrite(12, TimeUnit.HOURS)
            .build();

    private void dataAssembly(BotRequestDTO botRequestDTO, TulingRequestDTO tulingRequestDTO) {
        if (botRequestDTO==null){
            return;
        }
        var message = botRequestDTO.getMessage();
        //给图灵用户信息赋值
        var userInfo = new TulingRequestDTO.UserInfo();
        userInfo.setGroupId(botRequestDTO.getGroup_id());
        userInfo.setUserId(botRequestDTO.getUser_id());
        Optional<Sender> sender = Optional.of(botRequestDTO.getSender());
        userInfo.setUserIdName(sender.get().getNickname());
        var apiKeys = List.of("7de376756c95455fa55fa1d1f41eb745", "ee7dedc086414baaa4bab04d9deb5d11");
        userInfo.setApiKey(apiKeys.get((int) (Math.random() * apiKeys.size())));
        tulingRequestDTO.setUserInfo(userInfo);
        //资源信息处理
        //匹配CQ 图片 表情 语音
        String regex = "\\[CQ:(image|face|record),file=(.*?),url=(.*?)\\]";
        var resourceList = ReUtil.get(regex, message, 0);
        //图片和资源混合消息进行提取出文字消息
        var extractMessage = new StringBuilder();
        for (String text : Pattern.compile(regex).split(message)) {
            extractMessage.append(text);
        }

        //给图灵的数据
        var perception = new TulingRequestDTO.Perception();
        var inputText = new TulingRequestDTO.Perception.InputText();
        var cacheKey = cacheTranslateJP(botRequestDTO);
        var requestText = extractMessage.toString();
        var regexAt = "\\[CQ:(at),qq=(.*?)\\](.*)";
        var at = ReUtil.get(regexAt, requestText, 2);
        if (botRequestDTO.getMessage_type().equals(GROUP) && at != null && at.equals(botRequestDTO.getSelf_id())) {
            requestText = ReUtil.get(regexAt, requestText, 3);
        }

        if (cacheJP.getIfPresent(cacheKey) == 1) {
            //中文转日文
            requestText = TranslateUtil.Translate(requestText, TranslateUtil.JA_ZH);
        }
        inputText.setText(requestText);
        perception.setInputText(inputText);
        if (resourceList != null) {
            //匹配第一个变量是否是image
            if ("image".equals(ReUtil.findAll(regex, resourceList, 1, new ArrayList<>()).stream().findFirst().get())) {
                tulingRequestDTO.setReqType(1);
                //匹配第三个变量url
                var inputURL = new TulingRequestDTO.Perception.InputURL();
                inputURL.setUrl(ReUtil.findAll(regex, resourceList, 3, new ArrayList<>()).stream().findFirst().get());
                perception.setInputImage(inputURL);

            }
        }
        tulingRequestDTO.setPerception(perception);
    }

    /**
     * @param botRequestDTO
     * @return void
     * @Description 处理任意消息 处理完成会返回true
     * @author wlwang3
     * @date 2019/3/14
     */
    public boolean anyEvent(BotRequestDTO botRequestDTO) {
        var message = botRequestDTO.getMessage();
        Optional<Sender> sender = Optional.of(botRequestDTO.getSender());
        var userId = sender.get().getUser_id();
        var nickName = botRequestDTO.getSender().getNickname();
        var messageType = botRequestDTO.getMessage_type();
        var groupId = botRequestDTO.getGroup_id();
        List<String> msgSplitList = Arrays.asList(message.split(" "));
        if (CollectionUtils.isEmpty(msgSplitList)) {
            return true;
        } else {
            switch (msgSplitList.get(0)) {
                case HELP:
                    return help(botRequestDTO);
                case FUCK:
                    return fuck(botRequestDTO);
                case KG:
                    return kg(botRequestDTO);
                case QR:
                    return qr(botRequestDTO, msgSplitList.get(1));
                case NICE_DAY:
                    return niceDay(botRequestDTO, userId, nickName, messageType, groupId, msgSplitList);
                case JP:
                    return jp(botRequestDTO, msgSplitList.get(1));
                default:
                    return tuling(botRequestDTO);
            }
        }
    }

    private boolean tuling(BotRequestDTO botRequestDTO) {
        var tulingRequestDTO = new TulingRequestDTO();
        dataAssembly(botRequestDTO, tulingRequestDTO);
        var key = cacheTranslateJP(botRequestDTO);
        var sendMessage = "";

        var regexAt = "\\[CQ:(at),qq=(.*?)\\](.*)";
        var atQQ = ReUtil.get(regexAt, botRequestDTO.getMessage(), 2);


        if (botRequestDTO.getMessage_type().equals(GROUP) && atQQ != null && atQQ.equals(botRequestDTO.getSelf_id())) {
            sendMessage = Tuling123.tulingRequest(tulingRequestDTO);
            if (cacheJP.getIfPresent(key) == 1) {
                //中文转日文
                sendMessage = TranslateUtil.Translate(sendMessage, TranslateUtil.ZH_JA);
            }
            botApi.sendMsgGroup(botRequestDTO.getGroup_id(), sendMessage);
            return true;
        }
        if (botRequestDTO.getMessage_type().equals(PRIVATE)) {
            sendMessage = Tuling123.tulingRequest(tulingRequestDTO);
            if (cacheJP.getIfPresent(key) == 1) {
                //中文转日文
                sendMessage = TranslateUtil.Translate(sendMessage, TranslateUtil.ZH_JA);
            }
            botApi.sendMsgPrivate(botRequestDTO.getUser_id(), sendMessage);
            return true;
        }
        return false;
    }


    private String cacheTranslateJP(BotRequestDTO botRequestDTO) {
        var id = "";
        var type = "";
        if (botRequestDTO.getMessage_type().equals(GROUP)) {
            id = botRequestDTO.getGroup_id();
            type = GROUP;
        } else {
            id = botRequestDTO.getUser_id();
            type = PRIVATE;
        }
        var key = id + type;
        if (cacheJP.getIfPresent(key) == null) {
            var memory = botMemoryMapper.selectBotMemory(new BotMemory(id, type, null, null));
            if (memory != null) {
                cacheJP.put(key, memory.getTranslateJp());
            }
        }
        return key;
    }

    private boolean jp(BotRequestDTO botRequestDTO, String commandContent) {
        String id = "";
        if (botRequestDTO.getMessage_type().equals(GROUP)) {
            id = botRequestDTO.getGroup_id();
        }
        if (botRequestDTO.getMessage_type().equals(PRIVATE)) {
            id = botRequestDTO.getUser_id();
        }
        int memoryJp = Integer.parseInt(commandContent);
        var botMemory = new BotMemory(id, botRequestDTO.getMessage_type(), null, memoryJp);
        var botMemoryResult = botMemoryMapper.selectBotMemory(new BotMemory(id, botRequestDTO.getMessage_type(), null, null));
        var sendMessage = new StringBuilder().append("日语模式状态：");
        if (memoryJp == 1) {
            sendMessage.append("开启");
        }
        if (memoryJp == 0) {
            sendMessage.append("关闭");
        } else {
            return true;
        }
        var saveMemory = new BotMemory(id, botRequestDTO.getMessage_type(), null, memoryJp);
        if (Optional.of(botMemoryResult).isPresent()) {
            var result = botMemoryMapper.updateBotMemory(saveMemory);
            if (result) {
                botApi.sendMsg(botRequestDTO, sendMessage.toString());
            }
        } else {
            var result = botMemoryMapper.insertBotMemory(saveMemory);
            if (result) {
                botApi.sendMsg(botRequestDTO, sendMessage.toString());
            }
        }
        cacheJP.put(botMemory.getId() + botMemory.getType(), Integer.parseInt(commandContent));
        return true;
    }

    private boolean kg(BotRequestDTO botRequestDTO) {
        try {
            String regex = "\\[CQ:(image|face|record),file=(.*?),url=(.*?)\\]";
            if ("image".equals(ReUtil.get(regex, botRequestDTO.getMessage(), 1))) {
                var imageUrl = ReUtil.get(regex, botRequestDTO.getMessage(), 3);
                var baiduAGDTOList = ImageRec.advancedGeneral(imageUrl);
                if (CollectionUtils.isEmpty(baiduAGDTOList)) {
                    botApi.sendMsg(botRequestDTO, "没有查询到该图片相关信息呀！");
                    return true;
                }
                var resultMessage = new StringBuilder();
                for (BaiduAGDTO agdto : baiduAGDTOList) {
                    resultMessage.append(agdto.getKeyword() + " || " + agdto.getScore() * 100 + "%相似度\n");
                }

                botApi.sendMsg(botRequestDTO, resultMessage.toString());
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return true;
    }

    private boolean qr(BotRequestDTO botRequestDTO, String commandContent) {
        var stopWatch = new StopWatch();
        stopWatch.start();

        QrConfig config = new QrConfig(400, 400);
        config.setMargin(1);
        BufferedImage userImage = null;
        try {
            URL url = new URL("https://q1.qlogo.cn/g?b=qq&nk=" + botRequestDTO.getUser_id() + "&s=100");
            userImage = ImageIO.read(url);
        } catch (Exception e) {

            logger.error(e.getMessage(), e);
        }
        config.setImg(userImage);
        var qrFile = QrCodeUtil.generate(commandContent, config);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            ImageIO.write(qrFile, "png", out);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
        var base64 = Base64.getEncoder().encodeToString(out.toByteArray());
        stopWatch.stop();
        botApi.sendMsg(botRequestDTO, "[CQ:image,file=base64://" + base64 + "]" + "\n生成二维码耗时：" + stopWatch.getLastTaskTimeMillis() / 1000.0 + "秒");
        return true;
    }

    private boolean help(BotRequestDTO botRequestDTO) {
        var sendMessage = new StringBuilder();
        ALL_COMMANDS.forEach((k, v) -> sendMessage.append(k).append(" : ").append(v).append("\n"));
        botApi.sendMsg(botRequestDTO, sendMessage.toString().substring(0, sendMessage.length() - 1));
        return true;
    }

    private boolean fuck(BotRequestDTO botRequestDTO) {
        var huaQ = new StringBuilder();
        var random = new Random(System.currentTimeMillis());
        var randomInt = random.nextInt(20) % 10;
        IntStream.range(0, randomInt).forEach(value -> huaQ.append(ALL_COMMANDS.get(FUCK)));
        botApi.sendMsg(botRequestDTO, huaQ.toString());
        return true;
    }

    private boolean niceDay(BotRequestDTO botRequestDTO, String userId, String nickName, String messageType, String groupId, List<String> msgSplitList) {
        String id;
        var msgNickName = "狗群员们！";
        if (groupId == null) {
            id = userId;
            msgNickName = nickName;
        } else {
            id = botRequestDTO.getGroup_id();
        }
        var botMemoryDTO = botMemoryMapper.selectBotMemory(new BotMemory(id, messageType, null, null));
        int niceDaySwitch;
        try {
            niceDaySwitch = Integer.parseInt(msgSplitList.get(1));
        } catch (NumberFormatException e) {
            logger.error("NumberFormatException ", e);
            return true;
        }
        var result = false;
        if (0 == niceDaySwitch || 1 == niceDaySwitch) {
            if (botMemoryDTO == null) {
                result = botMemoryMapper.insertBotMemory(new BotMemory(id, messageType, niceDaySwitch, null));
            } else {
                if (botMemoryDTO.getNiceDay() == 1 && niceDaySwitch == 1) {
                    botApi.sendMsg(botRequestDTO, "嗯！？我已经记住了！不要再提醒我了！");
                    return true;
                }
                if (botMemoryDTO.getNiceDay() == 0 && niceDaySwitch == 0) {
                    botApi.sendMsg(botRequestDTO, "QAQ！我已经是关闭状态了！还要再关一次！？");
                    return true;
                }
                result = botMemoryMapper.updateBotMemory(new BotMemory(id, messageType, niceDaySwitch, null));
            }
            if (result) {
                if (niceDaySwitch == 1) {
                    botApi.sendMsg(botRequestDTO, "Hi~" + msgNickName + " 我已经记住了！");
                    return true;
                }
                if (niceDaySwitch == 0) {
                    botApi.sendMsg(botRequestDTO, msgNickName + "是不想要我了吗○rz");
                    return true;
                }
            }
        }
        return true;
    }

    /**
     * @param botRequestDTO bot请求模型
     * @return void
     * @Description 处理私有消息
     * @author wlwang3
     * @date 2019/3/14
     */
    public void privateEvent(BotRequestDTO botRequestDTO) {

        var message = botRequestDTO.getMessage();
        var userId = botRequestDTO.getSender().getUser_id();
        // 这里处理主人QQ消息 //是权限更高的命令

        if (masterQQ.contains(userId) && message.startsWith("//")) {
            botApi.sendMsg(botRequestDTO, minecraft.sendCommand(message.substring(2)));
        }
        switch (message) {
            case LIGHT:
                break;
            default:
                break;
        }
    }

    /**
     * @param botRequestDTO
     * @return void
     * @Description 处理组消息
     * @author wlwang3
     * @date 2019/3/14
     */
    public void groupEvent(BotRequestDTO botRequestDTO) {
        var message = botRequestDTO.getMessage();
        //如果复读
        if (messageDAO.repeatMessageCount(botRequestDTO.getGroup_id(), message)) {
            botApi.sendMsg(botRequestDTO, message);
        }
        //处理MCQQ群里的消息
        if (mcGroupQQ.equals(botRequestDTO.getGroup_id())) {
            //命令
            switch (message) {
                case LIST:
                    break;
                case TPS:
                    botApi.sendMsg(botRequestDTO, minecraft.sendCommand(TPS.substring(1)));
                    break;
                case POINTS_LEAD:
                    botApi.sendMsg(botRequestDTO,
                            ReUtil.get("(\\d)\\.(.*)(\\s-\\s)(\\d+)",
                                    minecraft.sendCommand(POINTS_LEAD.substring(1)),
                                    0) + " 点卷");
                    break;
                default:
                    break;
            }
        }
    }
}