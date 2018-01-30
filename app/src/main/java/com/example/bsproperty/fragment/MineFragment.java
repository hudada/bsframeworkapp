package com.example.bsproperty.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.bsproperty.MyApplication;
import com.example.bsproperty.R;
import com.example.bsproperty.bean.BaseResponse;
import com.example.bsproperty.bean.UserBean;
import com.example.bsproperty.bean.UserObjBean;
import com.example.bsproperty.net.ApiManager;
import com.example.bsproperty.net.BaseCallBack;
import com.example.bsproperty.net.OkHttpTools;
import com.example.bsproperty.ui.LoginActivity;
import com.example.bsproperty.ui.MainActivity;
import com.example.bsproperty.ui.RegisterActivity;
import com.example.bsproperty.utils.SpUtils;
import com.example.bsproperty.view.ModifyItemDialog;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;

/**
 * Created by yezi on 2018/1/27.
 */

public class MineFragment extends BaseFragment {
    @BindView(R.id.btn_btn)
    Button btnBtn;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.tv_tel)
    TextView tvTel;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_add)
    TextView tvAdd;

    private UserBean userBean;

    @Override
    protected void loadData() {
        userBean = MyApplication.getInstance().getUserBean();
        if (userBean == null) {
            btnBtn.setText("登      陆");
            tvAdd.setVisibility(View.GONE);
        } else {
            OkHttpTools.sendGet(mContext,ApiManager.REGISTER+userBean.getNumber())
                    .build().execute(new BaseCallBack<UserObjBean>(mContext,UserObjBean.class) {
                @Override
                public void onResponse(UserObjBean userObjBean) {
                    userBean=userObjBean.getData();
                    tvMoney.setText(userBean.getBalance() + "元");
                    tvNumber.setText(userBean.getNumber());
                    tvSex.setText(Integer.parseInt(userBean.getSex()) == 1 ? "男" : "女");
                    tvTel.setText(userBean.getTel());
                    tvUsername.setText(userBean.getName());
                    tvAdd.setVisibility(View.VISIBLE);
                    btnBtn.setText("退      出");
                }
            });

        }
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    public int getRootViewId() {
        return R.layout.fragment_mine;
    }

    @OnClick({R.id.btn_btn, R.id.tv_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_btn:
                if (btnBtn.getText().equals("登      陆")) {
                    startActivityForResult(new Intent(mContext, LoginActivity.class), 521);
                } else {
                    // 退出
                    SpUtils.cleanUserBean(mContext);
                    tvMoney.setText("");
                    tvNumber.setText("");
                    tvSex.setText("");
                    tvTel.setText("");
                    tvUsername.setText("");
                    btnBtn.setText("登      陆");
                }

                break;
            case R.id.tv_add:
                new ModifyItemDialog(mContext)
                        .setTitle("余额充值")
                        .setMessage("请输入需要充值的金额：")
                        .setCancelClick("取消", null)
                        .setOkClick("确认", new ModifyItemDialog.OnOkClickListener() {
                            @Override
                            public void onOkClick(final String etStr) {
                                if (!TextUtils.isEmpty(etStr)) {
                                    OkHttpTools.sendPut(mContext, ApiManager.RECORD_ADDMONEY + etStr + "/" + userBean.getNumber())
                                            .build().execute(new BaseCallBack<BaseResponse>(mContext, BaseResponse.class) {

                                        @Override
                                        public void onResponse(BaseResponse baseResponse) {
                                            DecimalFormat format=new DecimalFormat("#.00");
                                            userBean.setBalance(format.format(Double.parseDouble(userBean.getBalance())+Double.parseDouble(etStr))+"");
                                            Log.e("test",userBean.getBalance());
                                            tvMoney.setText(userBean.getBalance()+"元");
                                        }
                                    });

                                }

                            }
                        }).show();


                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 521:
                    //更新UI
                    tvMoney.setText(data.getExtras().getString("money") + "元");
                    tvNumber.setText(data.getExtras().getString("number"));
                    int sex = Integer.parseInt(data.getExtras().getString("sex"));
                    if (sex == 0) {
                        tvSex.setText("女");
                    } else if (sex == 1) {
                        tvSex.setText("男");
                    } else {
                        tvSex.setText("未填写");
                    }
                    tvAdd.setVisibility(View.VISIBLE);
                    tvTel.setText(data.getExtras().getString("tel"));
                    tvUsername.setText(data.getExtras().getString("username"));
                    btnBtn.setText("退      出");
                    break;
            }
        }
    }
}
