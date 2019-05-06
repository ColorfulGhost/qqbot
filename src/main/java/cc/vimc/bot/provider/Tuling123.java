package cc.vimc.bot.provider;

import cc.vimc.bot.dto.BaiduAGDTO;
import cc.vimc.bot.dto.TulingReponseDTO;
import cc.vimc.bot.dto.TulingRequestDTO;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Tuling123 {

    private static String TULINGURL = "http://openapi.tuling123.com/openapi/api/v2";

    public static String tulingRequest(TulingRequestDTO tulingRequestDTO) {
//        var message = botRequestDTO.getMessage();
//        //匹配CQ 图片 表情 语音
//        String regex = "\\[CQ:(image|face|record|at),file=(.*?),url=(.*?)\\]";
//        //图片和资源混合消息进行提取出文字消息
//        var extractMessage = new StringBuilder();
//        for (String text : Pattern.compile(regex).split(message)) {
//            extractMessage.append(text);
//        }
        //如果是图片 从图灵机器人到百度AI识别
        if (tulingRequestDTO.getReqType() == 1) {
            var resultMsg = new StringBuilder();
            for (BaiduAGDTO baiduAGDTO : ImageRec.advancedGeneral(tulingRequestDTO.getPerception().getInputImage().getUrl())) {
                resultMsg.append(baiduAGDTO.getKeyword()).append(" || ").append("相似度").append(baiduAGDTO.getScore() * 100).append("%\n");
            }
            return resultMsg.toString();
        }

        String reponse = null;
        try {
            reponse = HttpUtil.post(TULINGURL, JSONUtil.toJsonStr(tulingRequestDTO));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        var tulingReponseDTO = JSON.parseObject(reponse, TulingReponseDTO.class);
        var sendMessage = new StringBuilder();
        for (TulingReponseDTO.Results result : tulingReponseDTO.getResults()) {
            if ("text".equals(result.getResultType())) {
                sendMessage.append(result.getValues().getText());
            }
            if ("url".equals(result.getResultType())) {
                sendMessage.append(result.getValues().getUrl() + "\n");
            }
        }
//        var group_idOpt = Optional.of(botRequestDTO.getGroup_id());
//        if (group_idOpt.isPresent()) {
////            sendMsgGroup(botRequestDTO.getGroup_id(),"[CQ:at,qq="+botRequestDTO.getSender().getUser_id()+"]"+sendMessage.toString());
////        }else {
////            sendMsgPrivate(botRequestDTO.getUser_id(),sendMessage.toString());
////        }
        return sendMessage.toString();
    }
}
