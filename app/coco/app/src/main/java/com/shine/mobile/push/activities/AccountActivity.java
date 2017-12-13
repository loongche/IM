package com.shine.mobile.push.activities;

import android.content.Context;
import android.content.Intent;

import com.shine.mobile.common.app.Activity;
import com.shine.mobile.common.app.Fragment;
import com.shine.mobile.R;
import com.shine.mobile.push.frags.account.UpdateInfoFragment;

/**
 * @author Chengzl
 * @version 1.0.0
 * 账户页面
 */
public class AccountActivity extends Activity {
    private Fragment mCurFragment;

    /**
     * 账户Activity显示的入口
     *
     * @param context Context
     */
    public static void show(Context context) {
        context.startActivity(new Intent(context, AccountActivity.class));
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_account;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        mCurFragment = new UpdateInfoFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.lay_container, mCurFragment)
                .commit();
    }

    // Activity中收到剪切图片成功的回调
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCurFragment.onActivityResult(requestCode, resultCode, data);
    }
}
