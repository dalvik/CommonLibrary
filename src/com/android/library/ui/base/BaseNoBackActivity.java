package com.android.library.ui.base;

import android.view.View;
import android.widget.TextView;

/**
 * 
 * @description: 没有返回按钮的界面
 * @author: 23536
 * @date: 2016年1月11日 下午5:02:57
 */
public abstract class BaseNoBackActivity extends BaseSubActivity {
    @Override
    protected void setBackView(TextView back) {
        back.setVisibility(View.GONE);
    }
}
