package com.example.bsproperty.ui;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by yezi on 2018/1/27.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(getRootViewId());
        ButterKnife.bind(this);
        initView(savedInstanceState);
        loadData();
    }

    protected abstract void initView(Bundle savedInstanceState);

    protected abstract int getRootViewId();

    protected abstract void loadData();
}
