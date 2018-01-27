package com.example.bsproperty.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by yezi on 2018/1/27.
 */

public abstract class BaseFragment extends Fragment {

    protected Context mContext;
    protected View mRootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(getRootViewId(), container, false);
        ButterKnife.bind(this, mRootView);
        mContext = getActivity();
        initView(savedInstanceState);
        loadData();
        return mRootView;
    }

    protected abstract void loadData();

    protected abstract void initView(Bundle savedInstanceState);

    public abstract int getRootViewId();
}
