package com.shine.mobile.push;


import com.shine.mobile.common.app.Activity;
import com.shine.mobile.R;
import com.shine.mobile.push.activities.MainActivity;
import com.shine.mobile.push.frags.assist.PermissionsFragment;

public class LaunchActivity extends Activity {

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_launch;
    }


    @Override
    protected void onResume() {
        super.onResume();

        if (PermissionsFragment.haveAll(this, getSupportFragmentManager())) {
            MainActivity.show(this);
            finish();
        }

    }
}
