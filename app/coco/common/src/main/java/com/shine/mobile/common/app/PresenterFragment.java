package com.shine.mobile.common.app;

import android.content.Context;

/**
 * Created by Admin on 2017/9/27.
 */

public abstract class PresenterFragment extends Fragment {

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //在界面onAttach 之后就触发初始化Presenter
//        initPresenter();
    }

    /**
     * 初始化Presenter
     * @return Presenter
     */
//    protected abstract Presenter initPresenter();

    @Override
    protected int getContentLayoutId() {
        return 0;
    }
}
