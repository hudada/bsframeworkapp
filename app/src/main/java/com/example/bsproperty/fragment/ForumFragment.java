package com.example.bsproperty.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bsproperty.R;
import com.example.bsproperty.adapter.BaseAdapter;
import com.example.bsproperty.bean.ForumBean;
import com.example.bsproperty.bean.ForumListBean;
import com.example.bsproperty.bean.NoticeBean;
import com.example.bsproperty.bean.NoticeListBean;
import com.example.bsproperty.net.ApiManager;
import com.example.bsproperty.net.BaseCallBack;
import com.example.bsproperty.net.OkHttpTools;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by yezi on 2018/1/27.
 */

public class ForumFragment extends BaseFragment {
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.sl_list)
    SwipeRefreshLayout slList;
    private MyAdapter adapter;
    private ArrayList<ForumBean> mData;

    @Override
    protected void loadData() {
        OkHttpTools.sendGet(mContext, ApiManager.HOME_LIST).build()
                .execute(new BaseCallBack<ForumListBean>(mContext, ForumListBean.class) {
                    @Override
                    public void onResponse(ForumListBean forumListBean) {
                        mData = forumListBean.getData();
                        adapter.notifyDataSetChanged(mData);
                    }
                });
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mData = new ArrayList<>();
        slList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
                slList.setRefreshing(false);
            }
        });
        rvList.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new MyAdapter(mContext, R.layout.item_forum, mData);
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, Object item, int position) {

            }
        });
        rvList.setAdapter(adapter);
    }

    @Override
    public int getRootViewId() {
        return R.layout.fragment_forum;
    }

    private class MyAdapter extends BaseAdapter<ForumBean> {

        public MyAdapter(Context context, int layoutId, ArrayList<ForumBean> data) {
            super(context, layoutId, data);
        }

        @Override
        public void initItemView(BaseViewHolder holder, ForumBean forumBean, int position) {
            holder.setText(R.id.tv_title, forumBean.getTitle());
            holder.setText(R.id.tv_name, "(" + forumBean.getNumber() + "业主发布)");
            holder.setText(R.id.tv_time, forumBean.getDate());
            holder.setText(R.id.tv_content, forumBean.getInfo());
            holder.setText(R.id.tv_count, forumBean.getCount() + "回复");
        }
    }
}
