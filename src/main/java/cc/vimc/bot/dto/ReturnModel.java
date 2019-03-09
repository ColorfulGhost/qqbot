package cc.vimc.bot.dto;

import java.io.Serializable;

public class ReturnModel<T> implements Serializable {

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    private int code=0;
    private String msg = "未知消息";
    private T data;

}
