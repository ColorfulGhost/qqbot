package cc.vimc.bot.impl;


import cc.vimc.bot.dto.BotRequestDTO;
import cc.vimc.bot.dto.GroupMemberDTO;
import cc.vimc.bot.util.HttpUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import java.util.*;

import static cc.vimc.bot.enums.Apis.*;
import static cc.vimc.bot.enums.Fields.*;


@Service
public class BotApiImpl {
    @Value("${QQBOT_URL}")
    String qqbotUrl;

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
        HttpUtils.post(qqbotUrl , SEND_MSG, request);
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
        HttpUtils.post(qqbotUrl ,SEND_GROUP_MSG, request);
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
        HttpUtils.post(qqbotUrl , SEND_PRIVATE_MSG, request);
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
            var data = JSON.parseObject(HttpUtils.post(qqbotUrl , GET_GROUP_MEMBER_LIST, request)).getString("data");
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
        var result = HttpUtils.post(qqbotUrl ,GET_GROUP_LIST, Collections.emptyMap());
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
        HttpUtils.post(qqbotUrl ,SEND_LIKE, request);
    }
}
