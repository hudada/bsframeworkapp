package com.example.bsproperty.bean;

import java.io.Serializable;

/**
 * Created by wdxc1 on 2018/1/28.
 */

public class UserObjBean extends BaseResponse implements Serializable{
    private UserBean data;

    public UserBean getData() {
        return data;
    }

    public void setData(UserBean data) {
        this.data = data;
    }
}
