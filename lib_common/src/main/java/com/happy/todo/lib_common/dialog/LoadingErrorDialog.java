package com.happy.todo.lib_common.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.happy.todo.lib_common.R;


/**
 * 加载错误提示话框
 */
public class LoadingErrorDialog extends Dialog {


    private static LoadingErrorDialog loadDialog;

    private boolean cancelable;

    private String tipMsg;
    private final TextView tv_loading_error;
    private final TextView tv_loading_title;


    public LoadingErrorDialog(final Context ctx, boolean cancelable, String tipMsg) {
        super(ctx);

        this.cancelable = cancelable;
        this.tipMsg = tipMsg;

        this.getContext().setTheme(R.style.My_Dialog_Fullscreen);
        setContentView(R.layout.dialog_loading);
        // 必须放在加载布局后
        setparams();
        tv_loading_error = findViewById(R.id.tv_loading_error);
        tv_loading_title = findViewById(R.id.tv_loading_title);
        RelativeLayout rl_parent =  findViewById(R.id.rl_parent);
        rl_parent.setOnClickListener(v -> dismiss());

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!TextUtils.isEmpty(tipMsg)) {
            tv_loading_error.setText(tipMsg);
        }
    }

    private void setparams() {
//        this.setCancelable(cancelable);
        this.setCancelable(true);
        this.setCanceledOnTouchOutside(true);
        WindowManager windowManager = getWindow().getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        // Dialog宽度
        lp.width = (int) (display.getWidth() * 0.7);
        Window window = getWindow();
        window.setAttributes(lp);
        window.getDecorView().getBackground().setAlpha(0);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!cancelable) {
                Toast.makeText(getContext(), tipMsg, Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    public static void show(Context context) {
        show(context, null, true);
    }


    public static void show(Context context, String message) {
        show(context, message, true);
    }


    public static void show(Context context, int resourceId) {
        show(context, context.getResources().getString(resourceId), true);
    }


    private static void show(Context context, String message, boolean cancelable) {
        if (context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return;
            }
        }
        if (loadDialog != null && loadDialog.isShowing()) {
            return;
        }
        loadDialog = new LoadingErrorDialog(context, cancelable, message);
        loadDialog.show();
    }

    public static void dismiss(Context context) {
        try {
            if (context instanceof Activity) {
                if (((Activity) context).isFinishing()) {
                    loadDialog = null;
                    return;
                }
            }

            if (loadDialog != null && loadDialog.isShowing()) {
                Context loadContext = loadDialog.getContext();
                if (loadContext != null && loadContext instanceof Activity) {
                    if (((Activity) loadContext).isFinishing()) {
                        loadDialog = null;
                        return;
                    }
                }
                loadDialog.dismiss();
                loadDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            loadDialog = null;
        }
    }
}
