package com.happy.todo.module_main.ui.version;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.happy.todo.lib_common.utils.language.LanguageUtil;
import com.happy.todo.module_main.R;
import com.happy.todo.lib_common.bean.VersionBean;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Jaminchanks on 2018-09-01.
 */
public class UpdateAppActivity extends Activity {

    private static final String EXT_VERSION_BEAN = "version_bean";
    private static final String EXT_FORCED_UPDATE = "is_forced_update";
    private boolean isForcedUpdate = false;

    public static Intent getIntent(Context context, VersionBean versionBean, boolean isForcedUpdate) {
        Intent intent = new Intent();
        intent.putExtra(EXT_VERSION_BEAN, versionBean);
        intent.putExtra(EXT_FORCED_UPDATE, isForcedUpdate);
        intent.setClass(context, UpdateAppActivity.class);
        return intent;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EventBus.getDefault().register(this);
        setContentView(R.layout.main_activity_update);
        initView();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LanguageUtil.newInstance().attachBaseContext(newBase));
    }


    @Override
    public Resources getResources() {
        Resources resources = super.getResources();
        Configuration configuration = new Configuration();
        configuration.setToDefaults();
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        return resources;
    }


    protected void initView() {
        VersionBean data = (VersionBean) getIntent().getSerializableExtra(EXT_VERSION_BEAN);
        isForcedUpdate = getIntent().getBooleanExtra(EXT_FORCED_UPDATE,false);

        TextView tvContent = findViewById(R.id.tv_update_content);
        TextView tv_app_version = findViewById(R.id.tv_app_version);
        if(isForcedUpdate){
            tvContent.setText(LanguageUtil.newInstance().isEnUs() ? fromHtml(data.getEn_compel_tip()) : fromHtml(data.getCompel_tip()));
        }else {
            tvContent.setText(data == null ? getString(R.string.app_forced_upgrade_hint) : fromHtml(data.getTip()));
        }
        tv_app_version.setText(data == null ? "" : data.getSystem_version());


        ImageView btnIgnore = findViewById(R.id.btn_ignore);
        Button btnUpdate = findViewById(R.id.btn_update);
        btnIgnore.setVisibility(isForcedUpdate ? View.GONE : View.VISIBLE);

        btnIgnore.setOnClickListener(v->{
            finish();
        });
        btnUpdate.setOnClickListener(v -> {
            if (!data.getAppurl().startsWith("http")) {
                finish();
                return;
            }

            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse(data.getAppurl());
            intent.setData(content_url);
            startActivity(intent);
        });
        //传递给ProfileFragment
        EventBus.getDefault().postSticky(data);
    }

    public Spanned fromHtml(String source) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(source);
        }
    }

    @Override
    public void onBackPressed() {
        if(!isForcedUpdate) {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
