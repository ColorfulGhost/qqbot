package cc.vimc.bot.impl;


import cc.vimc.bot.dto.BotRequestDTO;
import cc.vimc.bot.dto.GroupMemberDTO;
import cc.vimc.bot.util.HttpUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Type;
import java.util.*;

import static cc.vimc.bot.enums.Apis.*;
import static cc.vimc.bot.enums.Fields.*;


@Service
public class BotApiImpl {

    @Value("${QQBOT_URL}")
    String qqbotUrl;

    @Async
    public void sendMsg(BotRequestDTO botRequestDTO, String message) {
        String messageType = botRequestDTO.getMessage_type();
        var request = new HashMap();
        request.put(MESSAGE_TYPE, botRequestDTO.getMessage_type());
        request.put(messageType.equals(PRIVATE) ? USER_ID : messageType + "_id",
                botRequestDTO.getGroup_id() == null ? botRequestDTO.getUser_id() : botRequestDTO.getGroup_id());
        request.put(MESSAGE, message);
        HttpUtils.httpPost(qqbotUrl + SEND_MSG, request, Collections.emptyMap());
    }

    @Async
    public void sendMsgGroup(String groupId, String message) {
        var request = new HashMap();
        request.put(MESSAGE_TYPE, GROUP);
        request.put(GROUP_ID, groupId);
        request.put(MESSAGE, message);
        HttpUtils.httpPost(qqbotUrl + SEND_MSG, request, Collections.emptyMap());
    }

    @Async
    public void sendMsgPrivate(String userId, String message) {
        var request = new HashMap();
        request.put(MESSAGE_TYPE, PRIVATE);
        request.put(USER_ID, userId);
        request.put(MESSAGE, message);
        HttpUtils.httpPost(qqbotUrl + SEND_MSG, request, Collections.emptyMap());

    }

    public Map<String,List<GroupMemberDTO>> getGroupMember(List<String> groupIds) {
        Map<String, String> request = new HashMap<>();
        if (CollectionUtils.isEmpty(groupIds)) {
            return Collections.emptyMap();
        }
        Map<String,List<GroupMemberDTO>> groupMemberDTOListMap = new HashMap<>();
        for (String groupId : groupIds) {
            request.put(GROUP_ID, groupId);
            var data = JSON.parseObject(HttpUtils.httpPost(qqbotUrl + GET_GROUP_MEMBER_LIST, request, Collections.emptyMap())).getString("data");
            List<GroupMemberDTO> memberDTOList = JSON.parseArray(data, GroupMemberDTO.class);
            groupMemberDTOListMap.put(groupId,memberDTOList);
        }


        return groupMemberDTOListMap;
    }

//    public List<String> getFriendList(){
//        HttpUtils.httpPost(qqbotUrl+_GET_FRIEND_LIST,Collections.emptyMap(),Collections.emptyMap());
//    }

    public List<String> getGroupList() {
        var groupIdList = new ArrayList<String>();
        var result = HttpUtils.httpPost(qqbotUrl + GET_GROUP_LIST, Collections.emptyMap(), Collections.emptyMap());
        var resultObj = JSON.parseArray(JSON.parseObject(result).getString("data"), JSONObject.class);
        resultObj.forEach(data -> groupIdList.add(data.getString("group_id")));
        return groupIdList;
    }


    @Async
    public void sendLike(String userId) {
        Map<String, String> request = new HashMap<>();
        request.put(USER_ID, userId);
        request.put(TIMES, "10");
        HttpUtils.httpPost(qqbotUrl + SEND_LIKE, request, Collections.emptyMap());
    }
}
