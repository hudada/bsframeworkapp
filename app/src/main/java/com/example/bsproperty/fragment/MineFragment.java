package com.example.bsproperty.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.bsproperty.R;
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


    @OnClick(R.id.btn_btn)
    public void onViewClicked() {
        startActivity(new Intent(mContext, RegisterActivity.class));
    }
}
