package com.shine.mobile.common.app;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Chengzl
 * @version 1.0.0
 */

public abstract class Fragment extends android.support.v4.app.Fragment {
    private View mRoot;
    protected Unbinder mRootUnBinder;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //空间初始化之前初始化参数
        initArgs(getArguments());
    }

    /**
     * 初始化相关参数
     * @param bundle 参数bundle
     *
     */
    protected void initArgs(Bundle bundle){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRoot == null){
        int layId = getContentLayoutId();
        View root = inflater.inflate(layId, container, false);
        initWidget(root);
        mRoot = root;
        }else {
            if (mRoot.getParent() != null){
                //把当前Root从父控件中移除
                ((ViewGroup)mRoot.getParent()).removeView(mRoot);
            }
        }
        return mRoot;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // View 创建完成以后初始化数据
        initData();
    }

    /**
     * 得到当前页面的资源文件ID
     * @return 资源文件ID
     */
    protected abstract int getContentLayoutId();

    /**
     * 初始化控件
     */
    protected void initWidget(View root){
        mRootUnBinder = ButterKnife.bind(this, root);
    }
    /**
     * 初始化数据
     */
    protected void initData(){

    }

    /**
     *  返回按键触发时调用
     * @return 返回True 代表自己已经出去了返回逻辑，Activity不用自己finish。
     * 返回false代表我没有处理逻辑
     */
    public boolean onBackPressed(){

        return false;
    }
}
