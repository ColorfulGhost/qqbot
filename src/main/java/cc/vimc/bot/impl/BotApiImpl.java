package cc.vimc.bot.impl;


import cc.vimc.bot.dto.BotRequestDTO;
import cc.vimc.bot.dto.GroupMemberDTO;
import cc.vimc.bot.util.HttpUtils;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    @Async
    public List<GroupMemberDTO> getGroupMember(String groupId, String userId) {
        Map<String, String> request = new HashMap<>();
        if (StringUtils.isEmpty(groupId)) {
            return Collections.emptyList();
        }
        request.put(GROUP_ID, groupId);
        List<GroupMemberDTO> groupMemberDTOList = new ArrayList<>();
        var result = "";
        if (StringUtils.isEmpty(userId)) {
            result = HttpUtils.httpPost(qqbotUrl + GET_GROUP_MEMBER_LIST, request, Collections.emptyMap());
            var data = JSON.parseObject(result).getString("data");
            groupMemberDTOList = JSON.parseArray(data, GroupMemberDTO.class);

        } else {
            request.put(USER_ID, userId);
            result = HttpUtils.httpPost(qqbotUrl + GET_GROUP_MEMBER_INFO, request, Collections.emptyMap());
            var data = JSON.parseObject(result).getString("data");
            var groupMemberDTO = JSON.parseObject(data, GroupMemberDTO.class);
            groupMemberDTOList.add(groupMemberDTO);
        }
        return groupMemberDTOList;

    }
}
