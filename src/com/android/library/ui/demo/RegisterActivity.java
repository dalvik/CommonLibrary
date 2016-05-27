package com.android.library.ui.demo;

import android.os.Bundle;

import com.android.library.R;
import com.android.library.ui.base.BaseSubActivity;
import com.android.library.ui.demo.pager.RegisterModifyPager;
import com.android.library.ui.demo.pager.RegisterVCodePager;

public class RegisterActivity extends BaseSubActivity {

    public static final int PWD_LIMIT = 8;
    public static final int PAGER_MODIFY = 2;
    RegisterVCodePager vCodePager;
    RegisterModifyPager modifyPager;
    int pager = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        setTitle(R.string.register);
        initUI();
    }


    /**
     * 初始化Intent 参数
     *
     * @return
     */
    @Override
    protected boolean initIntent() {
        /*try {
            pager = getIntent().getIntExtra(ActivityUtil.INTENT_PAGER, 0);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }*/
        return true;
    }

    protected void initUI() {
        initPager();
        if (pager == PAGER_MODIFY) {
            //modifyPager.setMobile(AppApplication.getUser().mobile);
            replaceFragment(R.id.container, modifyPager);
        } else {
            replaceFragment(R.id.container, vCodePager);
        }
    }

    protected void initPager() {
        vCodePager = new RegisterVCodePager();
        modifyPager = new RegisterModifyPager();
    }

    public void gotoModify(String mobile) {
        modifyPager.setMobile(mobile);
        replaceFragment(R.id.container, modifyPager);
    }

}
