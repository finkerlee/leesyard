package com.lijiadayuan.lishijituan.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by lifanqiao on 16/3/31.
 */
public class TestAdapter extends BaseAdapter {

    private List<String> dataSource;

    public TestAdapter(List<String> dataSource){
        super();
        this.dataSource = dataSource;
    }

    /**
     * 获取item数量
     * @return
     */
    @Override
    public int getCount() {
        return dataSource.size();
    }

    /**
     * 获取当前的item
     * @param position
     * @return
     */
    @Override
    public Object getItem(int position) {
        return dataSource.get(position);
    }

    /**
     * 获取当前item的id
     * @param position
     * @return
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        return null;
    }
}
