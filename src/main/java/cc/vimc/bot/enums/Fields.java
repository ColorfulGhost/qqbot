package cc.vimc.bot.enums;

/**
 * @Description  https://cqhttp.cc/docs/4.7/#/API
 * @author wlwang3
 * @return
 * @date 2019/3/2
 */
public class Fields {
    
    /**
     * 对方 QQ 号
     */
    public static final String USER_ID = "user_id";
    /**
     * 要发送的内容
     */
    public static final String MESSAGE = "message";
    /**
     * 消息内容是否作为纯文本发送（即不解析 CQ 码），只在 message 字段是字符串时有效
     */
    public static final String AUTO_ESCAPE = "auto_escape";
    /**
     * 群号
     */
    public static final String GROUP_ID = "group_id";

    /**
     * 讨论组 ID（正常情况下看不到，需要从讨论组消息上报的数据中获得）
      */
    public static final String DISCUSS_ID = "discuss_id";
    /**
     * 消息类型，支持 private、group、discuss，分别对应私聊、群组、讨论组，如不传入，则根据传入的 *_id 参数判断
     */
    public static final String MESSAGE_TYPE = "message_type";
    /**
     * 赞的次数，每个好友每天最多 10 次
     */
    public static final String TIMES = "times";
    /**
     * 拒绝此人的加群请求
     */
    public static final String REJECT_ADD_REQUEST = "reject_add_request";
    /**
     * 禁言时长，单位秒，0 表示取消禁言 默认值	30 * 60
     */
    public static final String DURATION = "duration";
    /**
     * 可选，要禁言的匿名用户对象（群消息上报的 anonymous 字段）
     */
    public static final String ANONYMOUS = "anonymous";
    /**
     * 从上报的数据中获得
     */
    public static final String FLAG = "flag";
    /**
     * 是否设置
     */
    public static final String ENABLE = "enable";
    /**
     * 群名片内容，不填或空字符串表示删除群名片
     */
    public static final String CARD = "card";
    /**
     * 是否解散，如果登录号是群主，则仅在此项为 true 时能够解散
     */
    public static final String IS_DISMISS = "is_dismiss";
    /**
     * 专属头衔，不填或空字符串表示删除专属头衔
     */
    public static final String SPECIAL_TITLE = "special_title";
    /**
     * 是否同意请求
     */
    public static final String APPROVE = "approve";
    /**
     * 添加后的好友备注（仅在同意时有效）
     */
    public static final String REMARK = "remark";
    /**
     * QQ 昵称
     */
    public static final String NICKNAME = "nickname";
    /**
     * 是否不使用缓存（使用缓存可能更新不及时，但响应更快）
     */
    public static final String NO_CACHE = "no_cache";
    /**
     * 群名称
     */
    public static final String GROUP_NAME = "group_name";
}
