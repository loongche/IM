package com.shine.mobile.common.app;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.*;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import butterknife.ButterKnife;

/**
 * @author Chengzl
 * @version 1.0.0
 */

public abstract class Activity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 在界面未初始化之前调用的初始化窗口
        initWindows();
        if (initArgs(getIntent().getExtras())){
            setContentView(getContentLayoutId());
            initWidget();
            initData();
        }else {
            finish();
        }
    }

    /**
     * 初始化窗口
     */
    protected void initWindows(){

    }
    /**
     * 初始化相关参数
     * @param bundle 参数bundle
     * @return 如果参数正确返回true 如果参数错误返回 false
     */
    protected boolean initArgs(Bundle bundle){
        return true;
    }

    /**
     * 得到当前页面的资源文件ID
     * @return 资源文件ID
     */
    protected abstract int getContentLayoutId();

    /**
     * 初始化控件
     */
    protected void initWidget(){
        ButterKnife.bind(this);
    }
    /**
     * 初始化数据
     */
    protected void initData(){

    }

    /**
     * 点击导航
     * @return
     */
    @Override
    public boolean onSupportNavigateUp() {
        //点击界面导航返回时，finish当前页面
        finish();
        return super.onSupportNavigateUp();
    }

    /**
     * 点击手机物理返回键
     */
    @Override
    public void onBackPressed() {
        List<android.support.v4.app.Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null && fragments.size()>0){
            for (Fragment fragment :fragments){
                if (fragment instanceof com.shine.mobile.common.app.Fragment){
                    if (((com.shine.mobile.common.app.Fragment) fragment).onBackPressed()){
                        return;
                    }
                }
            }
        }
        //返回上一层Fragment
        super.onBackPressed();
        finish();
    }
}
