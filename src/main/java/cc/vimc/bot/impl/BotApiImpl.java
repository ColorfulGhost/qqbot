package cc.vimc.bot.impl;


import cc.vimc.bot.dto.BotRequestDTO;
import cc.vimc.bot.dto.GroupMemberDTO;
import cc.vimc.bot.dto.TulingReponseDTO;
import cc.vimc.bot.dto.TulingRequestDTO;
import cn.hutool.core.util.ReUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.regex.Pattern;

import static cc.vimc.bot.enums.Apis.*;
import static cc.vimc.bot.enums.Fields.*;


@Service
public class BotApiImpl {

    @Value("${QQBOT_URL}")
    String qqbotUrl;

    @Value("${tuling123.apiurl}")
    String tulingUrl;


    public void tulingRequest(BotRequestDTO botRequestDTO, TulingRequestDTO tulingRequestDTO) {
//        var message = botRequestDTO.getMessage();
//        //匹配CQ 图片 表情 语音
//        String regex = "\\[CQ:(image|face|record|at),file=(.*?),url=(.*?)\\]";
//        //图片和资源混合消息进行提取出文字消息
//        var extractMessage = new StringBuilder();
//        for (String text : Pattern.compile(regex).split(message)) {
//            extractMessage.append(text);
//        }


        var reponse = HttpUtil.post(tulingUrl, JSONUtil.toJsonStr(tulingRequestDTO));
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
        sendMsg(botRequestDTO, sendMessage.toString());
    }

    /**
     * 发送消息  兼容群和私聊
     *
     * @param botRequestDTO
     * @param message
     */
//    @Async
    public void sendMsg(BotRequestDTO botRequestDTO, String message) {
        String messageType = botRequestDTO.getMessage_type();
        var request = new HashMap();
        request.put(MESSAGE_TYPE, botRequestDTO.getMessage_type());
        request.put(messageType.equals(PRIVATE) ? USER_ID : messageType + "_id",
                botRequestDTO.getGroup_id() == null ? botRequestDTO.getUser_id() : botRequestDTO.getGroup_id());
        request.put(MESSAGE, message);
        HttpUtil.post(qqbotUrl + SEND_MSG, request);
    }

    /**
     * 发送组消息
     *
     * @param groupId
     * @param message
     */
//    @Async
    public void sendMsgGroup(String groupId, String message) {
        var request = new HashMap();
        request.put(GROUP_ID, groupId);
        request.put(MESSAGE, message);
        HttpUtil.post(qqbotUrl + SEND_GROUP_MSG, request);
    }

    /**
     * 发送私聊消息
     *
     * @param userId
     * @param message
     */
//    @Async
    public void sendMsgPrivate(String userId, String message) {
        var request = new HashMap();
        request.put(USER_ID, userId);
        request.put(MESSAGE, message);
        HttpUtil.post(qqbotUrl + SEND_PRIVATE_MSG, request);
    }

    /**
     * 获取所有组成员
     *
     * @param groupIds
     * @return
     */
    public Map<String, List<GroupMemberDTO>> getGroupMember(List<String> groupIds) {
        Map<String, Object> request = new HashMap<>();
        if (CollectionUtils.isEmpty(groupIds)) {
            return Collections.emptyMap();
        }
        Map<String, List<GroupMemberDTO>> groupMemberDTOListMap = new HashMap<>();
        for (String groupId : groupIds) {
            request.put(GROUP_ID, groupId);
            var data = JSON.parseObject(HttpUtil.post(qqbotUrl + GET_GROUP_MEMBER_LIST, request)).getString("data");
            List<GroupMemberDTO> memberDTOList = JSON.parseArray(data, GroupMemberDTO.class);
            groupMemberDTOListMap.put(groupId, memberDTOList);
        }
        return groupMemberDTOListMap;
    }

    /**
     * 获取bot下所有的群
     *
     * @return
     */
    public List<String> getGroupList() {
        var groupIdList = new ArrayList<String>();
        var result = HttpUtil.post(qqbotUrl + GET_GROUP_LIST, Collections.emptyMap());
        var resultObj = JSON.parseArray(JSON.parseObject(result).getString("data"), JSONObject.class);
        resultObj.forEach(data -> groupIdList.add(data.getString("group_id")));
        return groupIdList;
    }

    /**
     * 给某该用户发送点赞
     *
     * @param userId
     */
//    @Async
    public void sendLike(String userId) {
        Map<String, Object> request = new HashMap<>();
        request.put(USER_ID, userId);
        request.put(TIMES, "10");
        HttpUtil.post(qqbotUrl + SEND_LIKE, request);
    }
}
