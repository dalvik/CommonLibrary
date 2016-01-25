package com.android.library.ui.base;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.library.R;

/**
 * 二级页面Activity
 */
public abstract class BaseSubActivity extends BaseActionBarActivity {

    private PopupWindow popupMenu = null;

    private TextView title;
    private Button option;
    private Button optionLeft;
    private TextView backView;

    /**
     * 初始化Action Bar
     *
     * @param layout
     */
    protected void initActionBar(RelativeLayout layout) {
        enableSlideLayout(false);
        createActionBar(layout);
    }

    private View createActionBar(RelativeLayout layout) {
        // 初始化ActionBar样式
        View view = LayoutInflater.from(this).inflate(R.layout.common_action_bar, layout);
        title = (TextView) view.findViewById(R.id.title);
        option = (Button) view.findViewById(R.id.opt);
        optionLeft = (Button) view.findViewById(R.id.optLeft);
        backView = (TextView) view.findViewById(R.id.back);
        setActionBarBackground(view);
        setOptionView(option);
        setOptionLeftView(optionLeft);
        setBackView(backView);
        return view;
    }

    /**
     * Actionbar背景色
     *
     * @param view
     */
    protected void setActionBarBackground(View view) {
    }

    protected void setOptionLeftView(Button optionLeft) {
        optionLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                leftOptionBtn();
            }
        });
    }

    /**
     * 返回键按钮操作
     *
     * @param option
     */
    protected void setOptionView(TextView option) {
        option.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                rightOptionBtn();
            }
        });
    }

    /**
     * 初始化操作按钮
     *
     * @param option
     */
    /**
     * 右按钮操作键
     */
    public void leftOptionBtn() {
    }

    /**
     * 右按钮操作键
     */
    public void rightOptionBtn() {
    }

    protected void setBackView(TextView back) {
        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    protected void replaceFragment(int id, Fragment fragment) {
        replaceFragment(id, fragment, null);
    }

    protected void replaceFragment(int id, Fragment fragment, String tag) {
        FragmentManager fm = getSupportFragmentManager();
        // 开启Fragment事务
        FragmentTransaction transaction = fm.beginTransaction();

        transaction.replace(id, fragment, tag);
        // transaction.addToBackStack();
        // 事务提交
        transaction.commitAllowingStateLoss();
    }

    /**
     * 取Fragment
     *
     * @param tag
     * @return
     */
    protected Fragment getFragment(String tag) {
        FragmentManager fm = getSupportFragmentManager();
        return fm.findFragmentByTag(tag);
    }

    /**
     * 设置标题栏
     *
     * @param title
     */
    public void setTitle(int title) {
        this.title.setText(title);
    }

    /**
     * 设置右按钮显示
     *
     * @param opt
     */
    public void setOpt(int opt) {
        this.option.setText(opt);
    }

    /**
     * 设置右按钮显示
     *
     * @param opt
     */
    public void setOptImg(int opt) {
        this.option.setBackgroundResource(opt);
    }

    /**
     * 设置标题栏点击事件
     *
     * @param title
     */
    public void setTitleClickListener(OnClickListener onClickListener) {
        this.title.setOnClickListener(onClickListener);
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    protected void setTitleGone() {
        findViewById(R.id.action_bar_LL).setVisibility(View.GONE);
    }

    @Override
    public void reload() {
    }

    /**
     * Dispatch incoming result to the correct fragment.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
