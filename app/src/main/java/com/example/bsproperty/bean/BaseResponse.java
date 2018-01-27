package com.example.bsproperty.bean;

/**
 * Created by ALex on 2015/9/17.
 */
public class BaseResponse {

    private int state;  //请求结果状态 1 成功  -1失败
    private String message;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
