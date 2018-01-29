package com.example.bsproperty.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.bsproperty.R;
import com.example.bsproperty.adapter.BaseAdapter;
import com.example.bsproperty.bean.NoticeBean;
import com.example.bsproperty.bean.NoticeListBean;
import com.example.bsproperty.bean.PamentRecordBean;
import com.example.bsproperty.bean.PamentRecordListBean;
import com.example.bsproperty.net.ApiManager;
import com.example.bsproperty.net.BaseCallBack;
import com.example.bsproperty.net.OkHttpTools;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by yezi on 2018/1/27.
 */

public class PriceFragment extends BaseFragment {
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.sl_list)
    SwipeRefreshLayout slList;
    private PriceFragment.MyAdapter adapter;
    private ArrayList<PamentRecordBean> mData;

    @Override
    protected void loadData() {
        OkHttpTools.sendGet(mContext, ApiManager.RECORD_LIST + "1004").build().execute(new BaseCallBack<PamentRecordListBean>(mContext, PamentRecordListBean.class) {
            @Override
            public void onResponse(PamentRecordListBean pamentRecordListBean) {
                mData = pamentRecordListBean.getData();
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
        adapter = new PriceFragment.MyAdapter(mContext, R.layout.item_record, mData);
        rvList.setAdapter(adapter);
    }

    @Override
    public int getRootViewId() {
        return R.layout.fragment_price;
    }

    private class MyAdapter extends BaseAdapter<PamentRecordBean> {

        public MyAdapter(Context context, int layoutId, ArrayList<PamentRecordBean> data) {
            super(context, layoutId, data);
        }

        @Override
        public void initItemView(BaseViewHolder holder, PamentRecordBean pamentRecordBean, int position) {
            if (Integer.parseInt(pamentRecordBean.getType()) == 0) {
                holder.setText(R.id.tv_title, "水费" + "(" + ((Integer.parseInt(pamentRecordBean.getState()) == 0) ? "未缴清" : "已缴清") + ")");
            } else if (Integer.parseInt(pamentRecordBean.getType()) == 1) {
                holder.setText(R.id.tv_title, "电费" + "(" + ((Integer.parseInt(pamentRecordBean.getState()) == 0) ? "未缴清" : "已缴清") + ")");
            } else if (Integer.parseInt(pamentRecordBean.getType()) == 2) {
                holder.setText(R.id.tv_title, "燃气费" + "(" + ((Integer.parseInt(pamentRecordBean.getState()) == 0) ? "未缴清" : "已缴清") + ")");
            } else if (Integer.parseInt(pamentRecordBean.getType()) == 3) {
                holder.setText(R.id.tv_title, "停车费" + "(" + ((Integer.parseInt(pamentRecordBean.getState()) == 0) ? "未缴清" : "已缴清") + ")");
            } else if (Integer.parseInt(pamentRecordBean.getType()) == 4) {
                holder.setText(R.id.tv_title, "物管费" + "(" + ((Integer.parseInt(pamentRecordBean.getState()) == 0) ? "未缴清" : "已缴清") + ")");
            }
            Button button= (Button) holder.getView(R.id.btn_commit);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            holder.setText(R.id.tv_time, pamentRecordBean.getDate());
            holder.setText(R.id.tv_content, pamentRecordBean.getAmount() + "元");
        }
    }
}
