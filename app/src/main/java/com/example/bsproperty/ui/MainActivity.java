package com.example.bsproperty.ui;

import android.os.Bundle;
import android.widget.RelativeLayout;

import com.example.bsproperty.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.activity_main)
    RelativeLayout activityMain;

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected int getRootViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void loadData() {

    }
}
