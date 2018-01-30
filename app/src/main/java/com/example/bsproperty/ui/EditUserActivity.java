package com.example.bsproperty.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.bsproperty.MyApplication;
import com.example.bsproperty.R;
import com.example.bsproperty.bean.UserBean;
import com.example.bsproperty.bean.UserObjBean;
import com.example.bsproperty.net.ApiManager;
import com.example.bsproperty.net.BaseCallBack;
import com.example.bsproperty.net.OkHttpTools;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditUserActivity extends BaseActivity {

    @BindView(R.id.btn_back)
    Button btnBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.btn_right)
    Button btnRight;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_tel)
    EditText etTel;
    @BindView(R.id.rb_sex1)
    RadioButton rbSex1;
    @BindView(R.id.rb_sex2)
    RadioButton rbSex2;
    UserBean userBean;
    UserBean newUser;

    @Override
    protected void initView(Bundle savedInstanceState) {
        userBean = MyApplication.getInstance().getUserBean();
        if (userBean != null) {
            tvNumber.setText(userBean.getNumber());
            tvTitle.setText("修改用户信息");
            Log.e("test",userBean.getTel()+"::"+userBean.getName());
            etTel.setText(userBean.getTel());
            btnRight.setText("保存");
            etUsername.setText(userBean.getName());
            int sex = Integer.parseInt(userBean.getSex());
            if (sex == 0) {
                rbSex2.setChecked(true);
            } else {
                rbSex1.setChecked(true);
            }
        }

    }

    @Override
    protected int getRootViewId() {
        return R.layout.activity_edit_user;
    }

    @Override
    protected void loadData() {
    }

    @OnClick({R.id.btn_back, R.id.btn_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                this.finish();
                break;
            case R.id.btn_right:
                newUser=new UserBean();
                if (etUsername.getText() != null && !etUsername.getText().toString().equals("")) {
                    newUser.setName(etUsername.getText()+"");
                } else {
                    showToast(EditUserActivity.this, "用户名不能为空！");
                    return;
                }
                if (etTel.getText()!= null && !etTel.getText().toString().equals("")) {
                    newUser.setTel(etTel.getText()+"");
                } else {
                    showToast(EditUserActivity.this, "手机号不能为空！");
                    return;
                }
                if (rbSex1.isChecked()){
                    newUser.setSex(""+1);
                }else{
                    newUser.setSex(""+0);
                }
                newUser.setNumber(userBean.getNumber());
                newUser.setBalance(userBean.getBalance());
                OkHttpTools.postJson(EditUserActivity.this, ApiManager.EDITUSER, newUser)
                        .build().execute(new BaseCallBack<UserObjBean>(EditUserActivity.this, UserObjBean.class) {
                    @Override
                    public void onResponse(UserObjBean userObjBean) {
                        // 更新全局User对象
                        userBean.setSex(userObjBean.getData().getSex());
                        userBean.setTel(userObjBean.getData().getTel());
                        userBean.setName(userObjBean.getData().getName());
                        userBean.setBalance(userObjBean.getData().getBalance());
                        showToast(EditUserActivity.this, "用户信息已修改！");
                        setResult(RESULT_OK);
                        EditUserActivity.this.finish();
                    }
                });
                break;
        }
    }
}
