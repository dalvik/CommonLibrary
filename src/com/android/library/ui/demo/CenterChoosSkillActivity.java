package com.android.library.ui.demo;

import android.os.Bundle;
import android.view.View;

import com.android.library.R;
import com.android.library.ui.base.BaseSubActivity;

public class CenterChoosSkillActivity extends BaseSubActivity implements View.OnClickListener {
    public static final String RESULT_SKILL = "skill";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.center_choos_skill);
        setTitle(R.string.center_choos_skill);
    }

    @Override
    public void onClick(View v) {

    }
}
