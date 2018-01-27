package com.example.bsproperty.net;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.allosaurusapp.model.BaseResponse;
import com.allosaurusapp.ui.BaseActivity;
import com.allosaurusapp.ui.LoginActivity;
import com.allosaurusapp.util.ToastUtil;
import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Yezi521 on 2017/11/10.
 */

public abstract class BaseCallBack<T> extends Callback<BaseResponse> {

    private Context mContext;
    private Class mClass;

    public BaseCallBack(Context context, Class mClass) {
        mContext = context;
        this.mClass = mClass;
    }

    @Override
    public BaseResponse parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
        Log.e("okhttp", "json: " + string);
        return (BaseResponse) new Gson().fromJson(string, mClass);
    }

    @Override
    public void onError(Call call, Exception e, int id) {
        if (mContext instanceof BaseActivity) {
            ((BaseActivity) mContext).dismissDialog();
        }
        if (e == null) {
            ToastUtil.showLong(mContext, "服务异常");
        } else {
            String msg = e.getMessage();
            if (TextUtils.isEmpty(msg)) {
                ToastUtil.showLong(mContext, "服务异常");
            } else {
                String start = "request failed , reponse's code is : ";
                if (msg.startsWith(start)) {
                    String[] msgs = msg.split(start);
                    if (msgs.length > 1 && msgs[1].equals("403")) {
                        UserPreference.clear(mContext);
                        mContext.startActivity(new Intent(mContext, LoginActivity.class));
                        ToastUtil.showLong(mContext, "身份认证失败，请重新登录");
                    }
                } else {
                    ToastUtil.showLong(mContext, "服务异常");
                }
            }
        }
    }

    @Override
    public void onResponse(BaseResponse response, int id) {
        if (mContext instanceof BaseActivity) {
            ((BaseActivity) mContext).dismissDialog();
        }
        if (response != null) {
            if (response.getState() == 1) {
                onResponse((T) response);
            } else {
                if (TextUtils.isEmpty(response.getMessage())) {
                    ToastUtil.showToast(mContext, "网络异常，请稍候再试");
                } else {
                    ToastUtil.showToast(mContext, response.getMessage());
                }
            }
        } else {
            ToastUtil.showToast(mContext, "网络异常，请稍候再试");
        }
    }

    public abstract void onResponse(T t);
}
