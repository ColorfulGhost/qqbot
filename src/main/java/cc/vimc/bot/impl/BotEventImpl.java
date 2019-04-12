package cc.vimc.bot.impl;

import cc.vimc.bot.dao.MessageDAO;
import cc.vimc.bot.dto.BotRequestDTO;

import cc.vimc.bot.dto.Sender;
import cc.vimc.bot.dto.TulingRequestDTO;
import cc.vimc.bot.mapper.BotMemoryMapper;
import cn.hutool.core.util.ReUtil;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


import javax.annotation.Resource;
import java.util.*;
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
    @Value("${tuling123.apikey}")
    String apiKey;

    public void botEventHandle(String request) {
        var botRequestDTO = new BotRequestDTO();
        var tulingRequestDTO = new TulingRequestDTO();
        try {
            botRequestDTO = JSON.parseObject(request, BotRequestDTO.class);
        } catch (Exception e) {
            logger.error("模型转换出错！", e);
            return;
        }
        var messageType = botRequestDTO.getMessage_type();
        var message = botRequestDTO.getMessage();

        //给图灵用户信息赋值
        var userInfo = new TulingRequestDTO.UserInfo();
        userInfo.setGroupId(botRequestDTO.getGroup_id());
        userInfo.setUserId(botRequestDTO.getUser_id());
        Optional<Sender> sender = Optional.of(botRequestDTO.getSender());
        userInfo.setUserIdName(sender.get().getNickname());
        userInfo.setApiKey(apiKey);
        tulingRequestDTO.setUserInfo(userInfo);
        //资源信息处理
        //匹配CQ 图片 表情 语音
        String regex = "\\[CQ:(image|face|record),file=(.*?),url=(.*?)\\]";
        var resourceList = ReUtil.findAll(regex, message, 0, new ArrayList<>());
        //图片和资源混合消息进行提取出文字消息
        var extractMessage = new StringBuilder();
        for (String text : Pattern.compile(regex).split(message)) {
            extractMessage.append(text);
        }

        //给图灵的数据
        var perception = new TulingRequestDTO.Perception();
        var inputText = new TulingRequestDTO.Perception.InputText();
        inputText.setText(extractMessage.toString());
        perception.setInputText(inputText);
        if (resourceList.stream().findFirst().isPresent()) {
            var resource = resourceList.stream().findFirst().get();
            //匹配第一个变量是否是image
            if ("image".equals(ReUtil.findAll(regex, resource, 1, new ArrayList<>()).stream().findFirst().get())) {
                tulingRequestDTO.setReqType(1);
                //匹配第三个变量url
                var inputURL = new TulingRequestDTO.Perception.InputURL();
                inputURL.setUrl(ReUtil.findAll(regex, resource, 3, new ArrayList<>()).stream().findFirst().get());
                perception.setInputImage(inputURL);

            }
        }
        tulingRequestDTO.setPerception(perception);


        //这里是处理所有消息的地方  如果存在条件则会在这里返回
        if (anyEvent(botRequestDTO)) {
            return;
        }
        if (!StringUtils.isEmpty(message)) {
            switch (messageType) {
                //处理组消息
                case GROUP:
                    groupEvent(botRequestDTO, tulingRequestDTO);
                    break;
                //处理私有消息
                case PRIVATE:
                    privateEvent(botRequestDTO, tulingRequestDTO);
                    break;
                default:
                    break;
            }
        }

    }

    /**
     * @param botRequestDTO
     * @return void
     * @Description 处理任意消息
     * @author wlwang3
     * @date 2019/3/14
     */
    private boolean anyEvent(BotRequestDTO botRequestDTO) {
        var message = botRequestDTO.getMessage();
        Optional<Sender> sender = Optional.of(botRequestDTO.getSender());
        var userId = sender.get().getUser_id();
        var nickName = botRequestDTO.getSender().getNickname();
        var messageType = botRequestDTO.getMessage_type();
        var groupId = botRequestDTO.getGroup_id();
        List<String> msgSplitList = Arrays.asList(message.split(" "));

        switch (msgSplitList.get(0)) {
            case HELP:
                return help(botRequestDTO);
            case FUCK:
                return fuck(botRequestDTO);
            case NICE_DAY:
                return niceDay(botRequestDTO, userId, nickName, messageType, groupId, msgSplitList);
            default:
                return false;
        }
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
        if (msgSplitList.size() < 2) {
            return true;
        }
        String id;
        var msgNickName = "狗群员们！";
        if (groupId == null) {
            id = userId;
            msgNickName = nickName;
        } else {
            id = botRequestDTO.getGroup_id();
        }
        var botMemoryDTO = botMemoryMapper.selectNiceDay(id, messageType);
        int niceDaySwitch;
        try {
            niceDaySwitch = Integer.parseInt(msgSplitList.get(1));
        } catch (NumberFormatException e) {
            logger.error("NumberFormatException ", e);
            return true;
        }
        var result = false;
        if (0 == niceDaySwitch || 1 == niceDaySwitch) {
            var selectNiceDay = botMemoryMapper.selectNiceDay(id, messageType);
            if (botMemoryDTO == null) {
                result = botMemoryMapper.insertNiceDay(id, messageType, niceDaySwitch);
            } else {
                if (selectNiceDay.getNiceDay() == 1 && niceDaySwitch == 1) {
                    botApi.sendMsg(botRequestDTO, "嗯！？我已经记住了呢！不用再提醒我啦！");
                    return true;
                }
                if (selectNiceDay.getNiceDay() == 0 && niceDaySwitch == 0) {
                    botApi.sendMsg(botRequestDTO, "QAQ！我已经是关闭状态了！为什么还要再关一次！");
                    return true;
                }
                result = botMemoryMapper.updateNiceDay(id, messageType, niceDaySwitch);
            }
            if (result) {
                if (niceDaySwitch == 1) {
                    botApi.sendMsg(botRequestDTO, "Hi~" + msgNickName + " なの酱记住啦！");
                }
                if (niceDaySwitch == 0) {
                    botApi.sendMsg(botRequestDTO, msgNickName + " QAQ 是不想要我了吗！");
                }
            }
        }
        return false;
    }

    /**
     * @param botRequestDTO bot请求模型
     * @return void
     * @Description 处理私有消息
     * @author wlwang3
     * @date 2019/3/14
     */
    private void privateEvent(BotRequestDTO botRequestDTO, TulingRequestDTO tulingRequestDTO) {

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
                botApi.tulingRequest(botRequestDTO, tulingRequestDTO);
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
    private void groupEvent(BotRequestDTO botRequestDTO, TulingRequestDTO tulingRequestDTO) {
        var message = botRequestDTO.getMessage();
        var nickName = botRequestDTO.getSender().getNickname();
        //如果复读
        if (messageDAO.repeatMessageCount(botRequestDTO.getGroup_id(), message)) {
            botApi.sendMsg(botRequestDTO, message);
        }
        var regexAt = "\\[CQ:(at),qq=(.*?)\\]";
        var atQQ = ReUtil.findAll(regexAt, message, 2, new ArrayList<>());
        if (atQQ.contains(botRequestDTO.getSelf_id())) {
            botApi.tulingRequest(botRequestDTO, tulingRequestDTO);
        }
        //处理MCQQ群里的消息
        if (mcGroupQQ.equals(botRequestDTO.getGroup_id())) {
            //命令
            switch (message) {
                case LIST:
                    botApi.sendMsg(botRequestDTO, minecraft.postPlayerList(nickName));
                    break;
                case TPS:
                    botApi.sendMsg(botRequestDTO, minecraft.sendCommand(TPS.substring(1)));
                    break;
                case POINTS_LEAD:
                    botApi.sendMsg(botRequestDTO, minecraft.sendCommand(POINTS_LEAD.substring(1)));
                    break;
                default:
                    break;
            }
        }
    }
}
