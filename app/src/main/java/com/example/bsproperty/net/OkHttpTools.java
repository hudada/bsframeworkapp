package com.example.bsproperty.net;

import android.content.Context;

import com.example.bsproperty.ui.BaseActivity;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.builder.PostStringBuilder;
import com.zhy.http.okhttp.request.RequestCall;

import okhttp3.MediaType;

/**
 * Created by yezi on 2018/1/27.
 */

public class OkHttpTools {
    public static GetBuilder sendGet(Context context, String url) {
        if (context instanceof BaseActivity) {
            ((BaseActivity) context).showProgress(context);
        }
        return OkHttpUtils
                .get()
                .url(url);
    }

    public static PostStringBuilder postJson(Context context, String url, Object bean) {
        if (context instanceof BaseActivity) {
            ((BaseActivity) context).showProgress(context);
        }
        return OkHttpUtils
                .postString()
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .url(url)
                .content(new Gson().toJson(bean));
    }
}
