package com.android.library.ui.demo;

import android.os.Bundle;
import android.view.View;

import com.android.library.R;
import com.android.library.ui.base.BaseSubActivity;

public class CenterChoosIndustryActivity extends BaseSubActivity implements View.OnClickListener {
    public static final String RESULT_INDUSTRY = "industry";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.center_choos_industry);
        setTitle(R.string.center_choos_industry);
    }

    @Override
    public void onClick(View v) {

    }
}
