package com.android.library.ui.base;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.library.R;
import com.android.library.ui.sidebar.SlideLinearLayout;


public abstract class BaseActionBarActivity extends BaseCommonActivity {
    protected boolean isNeedAddWaitingView = false;
    /**
     * 标题栏
     */
    protected View actionView;
    private SlideLinearLayout rootSliedLayout;
    private View waitView = null;
    private View contentView = null;
    private View reloadView = null;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(createRootView(LayoutInflater.from(this).inflate(layoutResID, null)));
        initActionBar((RelativeLayout) findViewById(R.id.action_bar));
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(createRootView(view), params);
        initActionBar((RelativeLayout) findViewById(R.id.action_bar));
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(createRootView(view));
        initActionBar((RelativeLayout) findViewById(R.id.action_bar));
    }

    /**
     * 创建根View
     *
     * @param view
     * @return
     */
    private SlideLinearLayout createRootView(View view) {
        contentView = view;
        rootSliedLayout = new SlideLinearLayout(this);
        rootSliedLayout.setBackgroundResource(R.color.base_bg);
        rootSliedLayout.setOrientation(LinearLayout.VERTICAL);
        actionView = LayoutInflater.from(this).inflate(R.layout.action_bar, null);
        rootSliedLayout.addView(actionView, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        rootSliedLayout.addView(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        if (isNeedAddWaitingView) {
            waitView = addWaitingView(rootSliedLayout);
            showInnerWaiting();
        }
        return rootSliedLayout;
    }

    /**
     * 是否需要添加等待框
     *
     * @param isNeedAddWaitingView
     */
    public void addWaitingView(boolean isNeedAddWaitingView) {
        this.isNeedAddWaitingView = isNeedAddWaitingView;
    }

    /**
     * 添加等待框
     *
     * @param root
     */
    private View addWaitingView(ViewGroup root) {
        View waitView = LayoutInflater.from(this).inflate(R.layout.inner_waiting, null);
        root.addView(waitView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        return waitView;
    }

    /**
     * 显示等待框
     */
    private void showInnerWaiting() {
        if (waitView != null) {
            waitView.setVisibility(View.VISIBLE);
        }
        contentView.setVisibility(View.GONE);
    }

    /**
     * 隐藏等待框
     */
    public void hideInnerWaiting() {
        if (waitView != null) {
            waitView.setVisibility(View.GONE);
        }
        contentView.setVisibility(View.VISIBLE);
    }

    /**
     * 重新加载数据
     *
     * @author Sean.xie
     */
    public abstract void reload();

    @Override
    protected void onSessionTimeout() {
        super.onSessionTimeout();
        hideWaitingDialog();
        hideInnerWaiting();
    }

    protected void onSystemError(){
        super.onSystemError();
        hideWaitingDialog();
        hideInnerWaiting();
    }
    /**
     * 加载失败
     */
    @Override
    protected void onNetError() {
        hideWaitingDialog();
        hideInnerWaiting();
        if (null == reloadView) {
            reloadView = LayoutInflater.from(this).inflate(R.layout.inner_reload, null);
            ImageView reloadBtn = (ImageView) reloadView.findViewById(R.id.reload_btn);
            reloadBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    reloadView.setVisibility(View.GONE);
                    contentView.setVisibility(View.VISIBLE);
                    if (isNeedAddWaitingView) {
                        showInnerWaiting();
                    }
                    reload();
                }
            });
            rootSliedLayout.addView(reloadView, LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
        } else {
            reloadView.setVisibility(View.VISIBLE);
        }
        contentView.setVisibility(View.GONE);
    }

    /**
     * 初始化Action Bar
     *
     * @param layout
     */
    protected abstract void initActionBar(RelativeLayout layout);

    protected void enableSlideLayout(boolean enabled) {
        rootSliedLayout.enableSlide(enabled);
    }

    @Override
    public void finish() {
        super.finish();
        // overridePendingTransition(R.anim.fixedly, R.anim.fixedly);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        // overridePendingTransition(R.anim.right_in, R.anim.fixedly);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        // overridePendingTransition(R.anim.right_in, R.anim.fixedly);
    }

    @Override
    protected final boolean onHandleBiz(int what, int result, int arg2, Object obj) {
        hideWaitingDialog();
        hideInnerWaiting();
        return onHandleBiz(what,result,obj);
    }

    protected boolean onHandleBiz(int what,int result,Object obj){
        return true;
    }

}
