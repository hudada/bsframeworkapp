package com.example.bsproperty.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.bsproperty.R;
import com.example.bsproperty.bean.AccountBean;
import com.example.bsproperty.bean.UserBean;
import com.example.bsproperty.bean.UserObjBean;
import com.example.bsproperty.net.ApiManager;
import com.example.bsproperty.net.BaseCallBack;
import com.example.bsproperty.net.OkHttpTools;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.btn_back)
    Button btnBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_number)
    EditText etNumber;
    @BindView(R.id.et_pass)
    EditText etPass;
    @BindView(R.id.btn_login)
    Button btnLogin;

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected int getRootViewId() {
        return R.layout.activity_login;
    }

    @Override
    protected void loadData() {

    }

    @OnClick({R.id.btn_back, R.id.btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_login:
                String number = etNumber.getText().toString().trim();
                String pass = etPass.getText().toString().trim();
                if (TextUtils.isEmpty(number)) {
                    showToast(LoginActivity.this, "门牌号不能为空！");
                    return;
                }
                if (TextUtils.isEmpty(pass)) {
                    showToast(LoginActivity.this, "密码不能为空！");
                    return;
                }
                OkHttpTools.postJson(LoginActivity.this, ApiManager.LOGIN, new AccountBean(number, pass)).build()
                        .execute(new BaseCallBack<UserObjBean>(LoginActivity.this, UserObjBean.class) {
                            @Override
                            public void onResponse(UserObjBean userObjBean) {
                                showToast(LoginActivity.this, "登陆成功！");
                                UserBean userBean=userObjBean.getData();
                                Intent intent=new Intent();
                                intent.putExtra("username",userBean.getName());
                                intent.putExtra("sex",userBean.getSex());
                                intent.putExtra("tel",userBean.getTel());
                                intent.putExtra("money",userBean.getBalance());
                                intent.putExtra("number",userBean.getNumber());
                                setResult(109,intent);
                                LoginActivity.this.finish();
                            }
                        });
                break;
        }
    }

}
