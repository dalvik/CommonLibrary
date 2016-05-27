package com.android.library.ui.base;

import android.os.Bundle;

/**
 * 
 * @description: 自动显示加载等待条
 * @author: 23536
 * @date: 2016年1月11日 下午5:01:59
 */
public class BaseInnerLoadActivity extends BaseSubActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isNeedAddWaitingView = true;
        super.onCreate(savedInstanceState);
    }
}
