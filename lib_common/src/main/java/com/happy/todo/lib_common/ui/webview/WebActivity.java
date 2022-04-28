package com.happy.todo.lib_common.ui.webview;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.happy.todo.lib_common.R;
import com.happy.todo.lib_common.R2;
import com.happy.todo.lib_common.base.BaseActivity;
import com.happy.todo.lib_common.event.LoginEvent;
import com.happy.todo.lib_common.http.api.AppApi;
import com.happy.todo.lib_common.router.RouterPath;
import com.happy.todo.lib_common.utils.AppLogUtil;
import com.happy.todo.lib_common.utils.ToolbarHelper;
import com.just.agentweb.AgentWeb;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

@Route(path = RouterPath.COMMON_WEB)
public class WebActivity extends BaseActivity {

    @BindView(R2.id.toolbar)
    Toolbar mToolbar;
    @BindView(R2.id.tv_bar_title)
    TextView mTvBarTitle;
    @BindView(R2.id.iv_back)
    ImageView ivBack;
    @BindView(R2.id.iv_share)
    ImageView ivShare;
    @BindView(R2.id.iv_collection)
    ImageView ivCollection;
    @BindView(R2.id.layout_content)
    FrameLayout mLayoutContent;

    private static final String EXTRA_URL = "EXTRA_URL";
    private static final String TITLE = "EXTRA_TITLE";
    private static final String EXTRA_ACT_ID = "EXTRA_ACT_ID";

    private static final String JS_BRIDGE = "ebbly";
    private static final String JS_TAG = "window.ebbly";

    private AgentWeb mAgentWeb;
    private String mUrl;
    private String mTitle;
    private boolean isNotCanFinish = false;//是否能点击退出



    public static void start(Context context, String url) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra(EXTRA_URL, url);
        context.startActivity(intent);
    }

    public static void start(Context context, String url, int act_id) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra(EXTRA_URL, url);
        intent.putExtra(EXTRA_ACT_ID, act_id);
        context.startActivity(intent);
    }

    @Override
    protected void initView() {
        Uri uri = getIntent().getData();
        if (uri != null) {
            String go = uri.getQueryParameter("go");
            String token = uri.getQueryParameter("token");
            String encryption_code = uri.getQueryParameter("encryption_code");
            if ("pending".equals(go)) {
                //从网页跳转过来
                String url = AppApi.WEB_URL_INIT;
                String account = uri.getQueryParameter("account");
//            ?account=ming%40test.com&l=en-us&type=app
                StringBuilder builder = new StringBuilder();
                builder.append(url);
                builder.append("?account=");
//                builder.append("ming%40test.com");
                builder.append(account);
                builder.append("&l=en-us&type=app");
                builder.append("&encryption_code=");
                builder.append(encryption_code);
                builder.append("&token=");
                builder.append(token);
                AppLogUtil.e("地址===》" + builder.toString());
                mUrl = builder.toString();
                mTitle = getIntent().getStringExtra(TITLE);
                isNotCanFinish = true;
            }
        } else {
            mUrl = getIntent().getStringExtra(EXTRA_URL);
            mTitle = getIntent().getStringExtra(TITLE);

        }
//        isNotCanFinish = getIntent().getBooleanExtra(EXTRA_IS_CAN_FINISH,true);
        if (!TextUtils.isEmpty(TITLE)) {
            mTvBarTitle.setVisibility(View.VISIBLE);
            mTvBarTitle.setText(mTitle);
        } else {
            mTvBarTitle.setVisibility(View.GONE);
        }

        if (TextUtils.isEmpty(mUrl)) {
            finish();
        } else {
            WebChromeClient webChromeClient = new WebChromeClient() {
                @Override
                public void onReceivedTitle(WebView view, String title) {
                    super.onReceivedTitle(view, title);
                    if (title != null) {
                        //mTvBarTitle.setText(title);
                    }
                }

                @Override
                public void onProgressChanged(WebView view, int newProgress) {
                    super.onProgressChanged(view, newProgress);
                }
            };

            mAgentWeb = AgentWeb.with(this)//传入Activity or Fragment
                    .setAgentWebParent(mLayoutContent,
                            new FrameLayout.LayoutParams(-1, -1))//传入AgentWeb 的父控件 ，如果父控件为
                    .useDefaultIndicator(ContextCompat.getColor(this, R.color.theme_color), 1)// 使用默认进度条
                    .setWebChromeClient(webChromeClient)
                    .setPermissionInterceptor((url, permissions, action) -> {
                        // TODO: 2017/12/27 缺少权限
                        return false;
                    })
                    .addJavascriptInterface(JS_BRIDGE, new Android2Js())
                    .addJavascriptInterface(JS_TAG, new Android2Js())
                    .createAgentWeb()
                    .ready()
                    .go(mUrl);
        }
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void initToolbar() {
        ToolbarHelper.setToolbarWithTitle(this, mToolbar, "");
        //点击XX是退出
        mToolbar.setNavigationOnClickListener(v -> {
            if (isNotCanFinish) {
                ARouter.getInstance().build(RouterPath.MAIN)
                        .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK)
                        .navigation();
            } else {
                if (mAgentWeb != null) {
                    if (!mAgentWeb.back()) {
                        super.onBackPressed();
                    }
                }
            }
        });
        ivBack.setOnClickListener(v -> {
            finish();
        });

        ivShare.setOnClickListener(v -> {

        });

        ivCollection.setOnClickListener(view -> {

        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    public void onPause() {
        if (mAgentWeb != null)
            mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();
    }

    @Override
    public void onResume() {
        if (mAgentWeb != null)
            mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        if (mAgentWeb != null)
            mAgentWeb.getWebLifeCycle().onDestroy();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (mAgentWeb != null) {
            if (!mAgentWeb.back()) {
                super.onBackPressed();
            }
        }
        super.onBackPressed();

    }


    /**
     * js调用的对象和方法
     */
    public class Android2Js {

    }

    //登录触发
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLogin(LoginEvent event) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

}
