package com.example.bsproperty.net;

import android.content.Context;

import com.example.bsproperty.ui.BaseActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.request.RequestCall;

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
}
