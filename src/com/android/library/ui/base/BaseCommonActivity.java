package com.android.library.ui.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ScrollView;

import com.android.library.BaseApplication;
import com.android.library.R;
import com.android.library.net.base.IDataCallback;
import com.android.library.net.manager.AbstractDataManager;
import com.android.library.ui.dialog.ConfirmDialog;
import com.android.library.ui.dialog.WaitingDialog;
import com.android.library.ui.utils.DialogUtils;
import com.android.library.ui.utils.PriorityRunnable;
import com.android.library.ui.utils.ToastUtils;

public abstract class BaseCommonActivity extends FragmentActivity implements IDataCallback {
    public static final String TAG = "CommonBaseActivity";
    public static final String INTENT_CLOSE = "activity.close";

    private static final String DLG_CONFIRMDIALOG = "Confirm";
    private static final String DLG_WAITING = "WAITING";

    protected BaseCommonActivity activity;
    private BroadcastReceiver receiver;
    private WaitingDialog waitingDialog = null;
    private ConfirmDialog confirmDialog = null;

    /**
     * 显示等待框
     */
    public static void showWaitingDlg() {
        if (BaseApplication.curContext == null) {
            return;
        }
        if (BaseApplication.curContext instanceof BaseCommonActivity) {
            ((BaseCommonActivity) BaseApplication.curContext).showWaitingDialog();
        }
    }

    /**
     * 隐藏等待框
     */
    public static void hideWaitingDlg() {
        if (BaseApplication.curContext == null) {
            return;
        }
        if (BaseApplication.curContext instanceof BaseCommonActivity) {
            ((BaseCommonActivity) BaseApplication.curContext).hideWaitingDialog();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        if (!initIntent()) {
            ToastUtils.showToast(R.string.intent_err);
            finish();
            return;
        }
        registerCloseListener();
    }

    /**
     * 初始化Intent 参数
     *
     * @return
     */
    protected boolean initIntent() {
        return true;
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        BaseApplication.curContext = this;
        PriorityRunnable.decreaseBase();
        //MobclickAgent.onResume(this);
    }

    @Override
    public final void onCallback(int what, int result, int arg2, Object obj) {
        try {
            switch (result) {
                case AbstractDataManager.RESULT_ERROR:
                    onNetError();
                    break;
                default:
                    if (result < 400) {
                        if (result == AbstractDataManager.RESULT_SID_TIMEOUT) {
                            onSessionTimeout();
                        } else {
                            if (BaseApplication.DEBUG) {
                                //TODO
                                //ToastUtils.showToast(((AbstractNetData) obj).desc);
                            }
                            onSystemError();
                        }
                    } else {
                        if (!onHandleBiz(what, result, arg2, obj)) {
                          //TODO
                            //ToastUtils.showToast(((AbstractNetData) obj).desc);
                        }
                    }
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            onSystemError();
        }
    }

    /**
     * 回话超时
     */
    protected void onSessionTimeout() {
    }

    /**
     * 处理业务逻辑
     *
     * @param what
     * @param result
     * @param arg2
     * @param obj
     * @return 如果返回ture 表示,已经处理相关数据, false父类继续处理(打印未知错误信息)
     */
    protected boolean onHandleBiz(int what, int result, int arg2, Object obj) {
        return true;
    }

    /**
     * 网络异常
     */
    protected void onNetError() {
    }

    /**
     * 系统异常
     */
    protected void onSystemError() {
        ToastUtils.showToast(R.string.system_err);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        hideWaitingDialog();
        hideConfirmDialog();
        super.onDestroy();
        unRegisterCloseListener();
    }

    /**
     * 关闭所有Activity
     */
    public void finishAll() {
        sendBroadcast(new Intent(INTENT_CLOSE));
    }

    /**
     * 注册关闭监听
     */
    protected void registerCloseListener() {
        try {
            IntentFilter filter = new IntentFilter(INTENT_CLOSE);
            receiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    finish();
                }
            };
            registerReceiver(receiver, filter);
        } catch (Exception e) {
        }
    }

    /**
     * 关闭注册
     */
    protected void unRegisterCloseListener() {
        try {
            unregisterReceiver(receiver);
        } catch (Exception e) {
        }
    }


    /**
     * 显示 等待中... 对话框
     */
    public void showWaitingDialog() {
        showWaitingDialog(R.string.processing);
    }

    /**
     * 显示等待对话框
     *
     * @param contentId
     */
    public void showWaitingDialog(int contentId) {
        try {
            hideWaitingDialog();
            waitingDialog = DialogUtils.newWaitingDialog(contentId);
            waitingDialog.show(getSupportFragmentManager(), DLG_WAITING);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 隐藏等待对话框
     */
    public void hideWaitingDialog() {
        try {
            if (waitingDialog != null) {
                waitingDialog.dismissAllowingStateLoss();
                waitingDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示确认对话框
     *
     * @param contentId
     * @param titleId
     * @param confirmId
     * @param cancelId
     * @param listener
     */
    public void showConfirmDialog(int contentId, int titleId, int confirmId, int cancelId, ConfirmDialog.OnResultListener listener) {
        hideConfirmDialog();
        confirmDialog = DialogUtils.newConfirmDialog(contentId, titleId, confirmId, cancelId, listener);
        confirmDialog.show(getSupportFragmentManager(), DLG_CONFIRMDIALOG);
    }

    /**
     * 隐藏确认对话框
     */
    public void hideConfirmDialog() {
        try {
            if (confirmDialog != null && confirmDialog.getDialog().isShowing()) {
                confirmDialog.dismissAllowingStateLoss();
                confirmDialog = null;
            }
        } catch (Exception e) {
        }
    }

    /**
     * 向下滚动
     *
     * @param scroll
     */
    public void scrollDown(final ScrollView scroll) {
        BaseApplication.handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                scroll.scrollBy(0, 200);
            }
        }, 200);
    }
}
