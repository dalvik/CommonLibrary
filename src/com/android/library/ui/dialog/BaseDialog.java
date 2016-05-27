package com.android.library.ui.dialog;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.android.library.R;
import com.android.library.net.base.AbstractDataRequestListener;
import com.android.library.net.base.IDataCallback;
import com.android.library.ui.base.BaseCommonActivity;
import com.android.library.ui.utils.ToastUtils;

/**
 * 
 * @description: 弹出框
 * @author: 23536
 * @date: 2016年1月10日 上午10:25:35
 */
public class BaseDialog extends DialogFragment implements IDataCallback {

    protected static final String ARGS_STYLE = "STYLE";
    protected static final String ARGS_THEME = "THEME";
    protected static final String ARGS_CANCELABLE = "CANCELABLE";

    protected static final String ARGS_TITLE = "TITLE";
    protected static final String ARGS_CONTENT = "CONTENT";
    protected static final String ARGS_CONFIRM = "CONFIRM";

    protected BaseCommonActivity activity;

    protected int content;
    protected int title;
    protected int confirm;

    public BaseDialog() {
        super();
    }

    /**
     * @param style
     * @param theme
     * @param cancelable
     * @return
     */
    private static BaseDialog newInstance(int style, int theme, boolean cancelable) {
        BaseDialog dialogFragment = new BaseDialog();

        Bundle bundle = new Bundle();
        bundle.putInt(ARGS_STYLE, style);
        bundle.putInt(ARGS_THEME, style);
        bundle.putBoolean(ARGS_CANCELABLE, cancelable);
        dialogFragment.setArguments(bundle);
        return dialogFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            int style = args.getInt(ARGS_STYLE, 1);
            int theme = args.getInt(ARGS_THEME, 0);
            switch (style) {
                case STYLE_NORMAL:
                case STYLE_NO_TITLE:
                case STYLE_NO_FRAME:
                case STYLE_NO_INPUT:
                    setStyle(style, theme);//设置样式
                    break;
                default:
                    setStyle(STYLE_NO_TITLE, theme);//设置样式
            }

            boolean cancelable = args.getBoolean(ARGS_CANCELABLE, true);
            setCancelable(cancelable);

            title = args.getInt(ARGS_TITLE, 0);
            content = args.getInt(ARGS_CONTENT, 0);
            confirm = args.getInt(ARGS_CONFIRM, 0);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setDialogWidth();
        activity = (BaseCommonActivity) getActivity();
    }

    protected void setText(TextView tv, int resID) {
        if (resID > 0) {
            tv.setText(resID);
        }
    }


    /**
     * 设置对话框宽度
     */
    protected void setDialogWidth() {
        // 设置宽度
        Window window = getDialog().getWindow();
        DisplayMetrics dm = new DisplayMetrics();
        window.getWindowManager().getDefaultDisplay().getMetrics(dm);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = dm.widthPixels * 6 / 7; // 屏幕的6/7
        getDialog().getWindow().setAttributes(params);
    }

    /**
     * 用于DataManager 回调
     *
     * @param what
     * @param result
     * @param arg2
     * @param obj
     */
    @Override
    public final void onCallback(int what, int result, int arg2, Object obj) {
        activity.hideWaitingDialog();
        switch (result) {
            case AbstractDataRequestListener.RESULT_NET_ERROR:
                ToastUtils.showToast(R.string.no_net);
                break;
            default:
                if (what > 200 && what < 400) {
                    ToastUtils.showToast(R.string.system_err);
                } else {
                    handleResponseData(what, result, arg2, obj);
                }
                break;
        }
    }

    protected void handleResponseData(int what, int result, int arg2, Object obj) {
    }
}
