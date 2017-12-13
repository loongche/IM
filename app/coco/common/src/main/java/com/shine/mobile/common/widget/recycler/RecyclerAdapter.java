package com.shine.mobile.common.widget.recycler;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shine.mobile.common.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Chengzl
 * @version 1.0.0
 *
 */

public abstract class RecyclerAdapter<Data> extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder<Data>> implements View.OnLongClickListener, View.OnClickListener ,AdapterCallback<Data>{
    private final List<Data> mDataList;
    private AdapterListener<Data> mListener;

    /**
     *
     * 构造函数
     */
    public RecyclerAdapter(){
        this(null);
    }

    public RecyclerAdapter(AdapterListener<Data> listener){
        this(new ArrayList<Data>(),listener);
    }

    public RecyclerAdapter(List<Data>dataList ,AdapterListener<Data> listener){
        this.mDataList = dataList;
        this.mListener = listener;
    }

    /**
     * 创建一个ViewHolder
     * @param parent
     * @param viewType view类型 可以根据不同的type 创建不同的ViewHolder,不同的viewHolder会进入一个缓冲池
     *                   约定为XML布局的id
     * @return  viewHolder
     */
    @Override
    public ViewHolder<Data> onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View root = inflater.inflate(viewType,parent,false);
        ViewHolder<Data> holder = onCreateViewHolder(root,viewType);
        //设置View的tag 为ViewHolder 进行双向绑定
        root.setTag(R.id.tag_recycler_holder,holder);
        //设置事件点击
        root.setOnClickListener(this);
        root.setOnLongClickListener(this);

        //进行注解绑定
        holder.unbinder = ButterKnife.bind(holder,root);
        //绑定callback
        holder.callback = this;
        return holder;
    }

    /**
     * 重写默认的布局类型返回
     * @param position 坐标
     * @return 类型，其实重写后返回的都是XML文件的ID
     */
    @Override
    public int getItemViewType(int position) {
        return getItemViewType(position,mDataList.get(position));
    }

    /**
     * 得到布局的类型
     * @param position 坐标
     * @param data 当前的数据
     * @return XML文件的ID,用于创建ViewHolder
     */
    @LayoutRes
    protected abstract int getItemViewType(int position, Data data);

    /**
     * 绑定一个数据到Holder上
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder<Data> holder, int position) {
            //得到需要绑定的数据
        Data data = mDataList.get(position);
        //出发数据绑定方法
        holder.bind(data);


    }

    protected abstract ViewHolder<Data> onCreateViewHolder(View root,int viewType);


    /**
     * 得到当前集合数据量
     * @return
     */
    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    /**
     * 插入一条数据并通知插入
     * @param data Data
     */
    public void add(Data data){
        mDataList.add(data);
        notifyItemInserted(mDataList.size()-1);
    }

    /**
     * 插入一堆数据，并通知这段集合更新
     * @param dataList Data
     */
    public void add(Data... dataList){
        if (dataList != null && dataList.length >0){
            int startPos = mDataList.size();
            Collections.addAll(mDataList,dataList);
            notifyItemRangeInserted(startPos,dataList.length);
        }
    }

    /**
     * 插入一堆数据，并通知这段集合更新
     * @param dataList Data
     */
    public void add(Collection<Data> dataList){
        if (dataList != null && dataList.size() >0){
            int startPos = mDataList.size();
            mDataList.addAll(dataList);
            notifyItemRangeInserted(startPos,dataList.size());
        }
    }

    /**
     * 删除全部数据
     */
    public void clear(){
        mDataList.clear();
        notifyDataSetChanged();
    }

    /**
     * 替换一个新集合，其中包括了清空
     * @param dataList 一个新集合
     */
    public void replace(Collection<Data> dataList){
        mDataList.clear();
        if (dataList == null && dataList.size() == 0)
            return;
            mDataList.addAll(dataList);
            notifyDataSetChanged();
    }

    /**
     *  对ViewHolder进行数据更新
     * @param data
     * @param holder
     */
    @Override
    public void update(Data data, ViewHolder<Data> holder) {
        //得到当前ViewHolder 的坐标
        int pos = holder.getAdapterPosition();
        if (pos >= 0){
            //进行数据移除和更新
            mDataList.remove(pos);
            mDataList.add(pos,data);
            //通知这个item有更新
            notifyItemChanged(pos);
        }
    }

    @Override
    public void onClick(View v) {
        ViewHolder holder = (ViewHolder) v.getTag(R.id.tag_recycler_holder);
        if (this.mListener !=null){
            mListener.onItemClick(holder,mDataList.get(holder.getAdapterPosition()));
        }
    }

    @Override
    public boolean onLongClick(View v) {
        ViewHolder holder = (ViewHolder) v.getTag(R.id.tag_recycler_holder);
        if (this.mListener !=null){
            mListener.onItemLongClick(holder,mDataList.get(holder.getAdapterPosition()));
            return true;
        }
        return false;
    }

    /**
     * 设置适配器监听
     * @param adapterListener 适配器监听
     */
    public void setListener(AdapterListener<Data> adapterListener){
        this.mListener = adapterListener;
    }
    /**
     * 自定义监听器
     * @param <Data> 泛型
     */
    public interface AdapterListener<Data>{
        //当Cell点击时触发
        void onItemClick(ViewHolder holder,Data data);
        //当Cell长按时触发
        void onItemLongClick(ViewHolder holder, Data data);
    }

    /**
     *  对回调接口做一次实现
     * @param <Data>
     */
    public static class AdapterListenerImpl<Data> implements AdapterListener<Data>{


        @Override
        public void onItemClick(ViewHolder holder, Data data) {

        }

        @Override
        public void onItemLongClick(ViewHolder holder, Data data) {

        }
    }
    /**
     * 自定义的ViewHolder
     * @param <Data> 泛型类型
     */

    public static abstract class ViewHolder<Data> extends RecyclerView.ViewHolder{

        protected Data mData;
        protected Unbinder unbinder;
        private AdapterCallback callback;
        public ViewHolder(View itemView) {
            super(itemView);
        }
        /**
         * 数据绑定
         * 仅仅在内部类中有效
         */
        void bind(Data data){
            this.mData = data;
            onBind(data);
        }

        protected abstract void onBind(Data data);

        /**
         *
         * Holder 自己对自己的data进行更新操作
         * @param data data数据
         */
        public void updateData(Data data){
            if (this.callback != null){
                this.callback.update(data,this);
            }
        }
    }

}
