package com.shine.mobile.common.widget.recycler;

/**
 * @author chengzl Email:match_hk@163.com
 * @version 1.0.0
 */
public interface AdapterCallback<Data> {
    void update(Data data, RecyclerAdapter.ViewHolder<Data> holder);
}
