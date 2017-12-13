package com.shine.mobile.push.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.shine.mobile.common.app.Activity;
import com.shine.mobile.common.app.Application;
import com.shine.mobile.common.widget.PortraitView;
import com.shine.mobile.R;
import com.shine.mobile.push.frags.main.ActiveFragment;
import com.shine.mobile.push.frags.main.ContactFragment;
import com.shine.mobile.push.frags.main.GroupFragment;
import com.shine.mobile.push.helper.NavHelper;

import net.qiujuer.genius.ui.Ui;
import net.qiujuer.genius.ui.widget.FloatActionButton;


import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import pub.devrel.easypermissions.EasyPermissions;

import static com.shine.mobile.common.app.Application.showToast;

public class MainActivity extends Activity
        implements BottomNavigationView.OnNavigationItemSelectedListener,
        NavHelper.OnTabChangedListener<Integer> {
    @BindView(R.id.appbar)
    View mLayAppBar;
    @BindView(R.id.im_portrait)//头像
    PortraitView mPortrait;
    @BindView(R.id.txt_title)//title
    TextView mTitle;
    @BindView(R.id.lay_container)//内容容器
    FrameLayout mContainer;
    @BindView(R.id.btn_action)
    FloatActionButton mAction;
    @BindView(R.id.navigation)
    BottomNavigationView mNavigation;//导航栏
    private NavHelper<Integer> mNavHelper;
    // 权限回调的标示
    private static final int RC = 0x0100;
    /**
     * MainActivity 显示的入口
     *
     * @param context 上下文
     */
    public static void show(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }

    @Override
    protected void initWindows() {
        super.initWindows();
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        //初始化帮助类
        mNavHelper = new NavHelper<>(this, R.id.lay_container, getSupportFragmentManager(), this);
        mNavHelper.add(R.id.action_home, new NavHelper.Tab<>(ActiveFragment.class, R.string.title_home)).add(R.id.action_group,
                new NavHelper.Tab<>(GroupFragment.class, R.string.title_group)).
                add(R.id.action_contact, new NavHelper.Tab<>(ContactFragment.class, R.string.title_contact));
        mNavigation.setOnNavigationItemSelectedListener(this);
        //加载本地标题栏的资源图片
        Glide.with(this).load(R.drawable.bg_src_morning).centerCrop()
                .into(new ViewTarget<View, GlideDrawable>(mLayAppBar) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        this.view.setBackground(resource.getCurrent());
                    }
                });

    }

    @Override
    protected void initData() {
        super.initData();
        // 从底部导中接管我们的Menu，然后进行手动的触发第一次点击
        Menu menu = mNavigation.getMenu();
        // 触发首次选中Home
        menu.performIdentifierAction(R.id.action_home, 0);
    }

    /**
     * 点击搜索
     */
    @OnClick(R.id.im_search)
    void onSearchMenuClick() {
        /**
         * 点击悬停action
         */
    }

    @OnClick(R.id.btn_action)
    void onActionClick() {
        AccountActivity.show(this);
    }

    /**
     * 当我们的底部导航被点击的时候触发
     *
     * @param item MenuItem
     * @return True 代表我们能够处理这个点击
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return mNavHelper.performClickMenu(item.getItemId());
    }

    /**
     * NavHelper 处理后回调的方法
     *
     * @param newTab 新的Tab
     * @param oldTab 旧的Tab
     */
    @Override
    public void onTabChanged(NavHelper.Tab<Integer> newTab, NavHelper.Tab<Integer> oldTab) {
        // 从额外字段中取出我们的Title资源Id
        mTitle.setText(newTab.extra);

        //对浮动按钮进行隐藏
        //旋转  Y轴位移，弹性插值器，时长
        float transY = 0;
        float rotation = 0;
        if (Objects.equals(newTab.extra,R.string.title_home)){
            //主页面时隐藏
            transY = Ui.dipToPx(getResources(),76);
        }else {
            //transY 默认为0 则显示
            if (Objects.equals(newTab.extra,R.string.title_group)){
                //群
                mAction.setImageResource(R.drawable.ic_group_add);
                rotation = 360;
            }else {
                //联系人
                mAction.setImageResource(R.drawable.ic_contact_add);
                rotation = 360;
            }
        }
        mAction.animate().rotation(rotation).translationY(transY).setInterpolator(new AnticipateOvershootInterpolator(1)).setDuration(480).start();
    }

}
