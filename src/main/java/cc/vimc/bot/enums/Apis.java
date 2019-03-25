package cc.vimc.bot.enums;

public class Apis {

    /**
     * 发送私聊消息
     */
    public static final String SEND_PRIVATE_MSG = "/send_private_msg";
    /**
     * 发送群消息
     */
    public static final String SEND_GROUP_MSG = "/send_group_msg";
    /**
     * 发送讨论组消息
     */
    public static final String SEND_DISCUSS_MSG  = "/send_discuss_msg";
    /**
     * 发送消息
     */
    public static final String SEND_MSG  = "/send_msg";
    /**
     * 撤回消息
     */
    public static final String DELETE_MSG  = "/delete_msg";
    /**
     * 发送好友赞
     */
    public static final String SEND_LIKE  = "/send_like";
    /**
     * 群组踢人
     */
    public static final String SET_GROUP_KICK  = "/set_group_kick";
    /**
     *  群组单人禁言
     */
    public static final String SET_GROUP_BAN  = "/set_group_ban";
    /**
     * 群组匿名用户禁言
     */
    public static final String SET_GROUP_ANONYMOUS_BAN  = "/set_group_anonymous_ban";
    /**
     *  群组全员禁言
     */
    public static final String SET_GROUP_WHOLE_BAN  = "/set_group_whole_ban";
    /**
     *  群组设置管理员
     */
    public static final String SET_GROUP_ADMIN   = "/set_group_admin";
    /**
     * 群组匿名
     */
    public static final String SET_GROUP_ANONYMOUS   = "/set_group_anonymous";
    /**
     * 设置群名片（群备注）
     */
    public static final String SET_GROUP_CARD   = "/set_group_card";
    /**
     * 退出群组
     */
    public static final String SET_GROUP_LEAVE   = "/set_group_leave";
    /**
     * 获取语音
     */
    public static final String GET_RECORD   = "/get_record";

    /**
     * 获取群成员信息
     */
    public static final String GET_GROUP_MEMBER_INFO   = "/get_group_member_info";

    /**
     * 获取群成员列表
     */
    public static final String GET_GROUP_MEMBER_LIST  = "/get_group_member_list";

}
