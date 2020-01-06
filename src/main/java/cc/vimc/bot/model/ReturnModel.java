package cc.vimc.bot.model;

import lombok.Data;

import java.io.Serializable;
@Data
public class ReturnModel<T> implements Serializable {
    private int code=0;
    private String msg = "未知消息";
    private T data;

}
