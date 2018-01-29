package com.example.bsproperty.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.bsproperty.MyApplication;
import com.example.bsproperty.R;
import com.example.bsproperty.bean.UserObjBean;
import com.example.bsproperty.ui.LoginActivity;
import com.example.bsproperty.ui.RegisterActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

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

    @Override
    protected void loadData() {
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    public int getRootViewId() {
        return R.layout.fragment_mine;
    }

    @OnClick({R.id.btn_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_btn:
                if (btnBtn.getText().equals("登      陆")) {
                    startActivityForResult(new Intent(mContext, LoginActivity.class), 521);
                }else{
                    // 退出
                }

                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 109) {
            //更新UI
            tvMoney.setText(data.getExtras().getString("money") + "元");
            tvNumber.setText(data.getExtras().getString("number"));
            tvSex.setText(Integer.parseInt(data.getExtras().getString("sex"))==1?"男":"女");
            tvTel.setText(data.getExtras().getString("tel"));
            tvUsername.setText(data.getExtras().getString("username"));
            btnBtn.setText("退      出");
        }
    }
}
