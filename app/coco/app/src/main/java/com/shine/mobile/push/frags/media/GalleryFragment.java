package com.shine.mobile.push.frags.media;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shine.mobile.R;
import com.shine.mobile.common.widget.GalleryView;
import com.shine.mobile.push.activities.AccountActivity;

/**
 * Created by match on 2017/10/19.
 */

public class GalleryFragment extends BottomSheetDialogFragment implements GalleryView.SelectedChangeListener {
    private GalleryView mGallery;
    private OnSelectedListener mListener;
    public GalleryFragment() {

    }

    /**
     * 账户Activity显示入口
     * @param context Context
     */
    private static void show(Context context){
        context.startActivity(new Intent(context, AccountActivity.class));
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //使用默认的
        return new BottomSheetDialog(getContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View mRoot = inflater.inflate(R.layout.fragment_gallery,container,false);
        mGallery = (GalleryView) mRoot.findViewById(R.id.galleryView);
        return mRoot;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
        mGallery.setup(getLoaderManager(),this);
    }

    /**
     *
     * @param count
     */
    @Override
    public void onSelectedChanged(int count) {
        //如果选中一张图片
        if (count>0){
            //隐藏自己
            dismiss();
            if (mListener != null){
                //得到所有的选中的图片的路径
                String[] paths = mGallery.getSelectedPath();
                mListener.onSelectedImage(paths[0]);
                //取消和唤起者之间的引用，加快内存回收
                mListener = null;
            }
        }
    }

    /**
     * 设置事件监听并返回自己
     * @param listener OnSelectedListener
     * @return GalleryFragment
     */
    public GalleryFragment setListener(OnSelectedListener listener){
        mListener = listener;
        return this;
    }

    /**
     * 选中图片的监听器
     */
    public interface OnSelectedListener{
        void onSelectedImage(String path);
    }
}
