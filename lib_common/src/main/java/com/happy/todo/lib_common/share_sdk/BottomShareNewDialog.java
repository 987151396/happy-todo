/*
package com.ifreegroup.app.ebbly.lib_common.share_sdk;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import android.view.View;
import android.widget.Toast;

import com.ifreegroup.app.ebbly.lib_common.R;
import com.ifreegroup.app.ebbly.lib_common.utils.ResourceUtils;


*/
/**
 * @Describe：底部分享弹窗（兼容刘海屏）
 * @Date： 2019/03/19
 * @Author： dengkewu
 * @Contact：
 *//*

public class BottomShareNewDialog extends BottomSheetDialog implements View.OnClickListener {
    private String mShareTitle;
    private String mShareContent;
    private String mShareUrl;
    private String mShareImageUrl;
    private View inflate;
    private OnClickShareListener onClickShareListener;

    public final static String default_title = ResourceUtils.getString(R.string.user_share_title); // 默认内容
    public final static String default_content = ResourceUtils.getString(R.string.user_share_content); // 默认内容
    public final static String default_url = "https://www.ebbly.com/download.html"; // 默认链接
    public Activity mContext;
    private PlatformBean mPlatformBean;

    public BottomShareNewDialog(@NonNull Context context) {
        this(context, 0);
        this.mContext = (Activity) context;
    }

    public BottomShareNewDialog(@NonNull Context context, int theme) {
        super(context, theme);
        this.mContext = (Activity) context;
        inflate = View.inflate(getContext(), R.layout.dialog_bottom_share1, null);
        setContentView(inflate);
        mPlatformBean = new PlatformBean();
        mShareTitle = default_title;
        mShareContent = default_content;
        mShareUrl = default_url;
        initView();
    }

    private void initView() {
        inflate.findViewById(R.id.btn_clear).setOnClickListener(view -> dismiss());
        inflate.findViewById(R.id.tv_cancel).setOnClickListener(view -> dismiss());
        inflate.findViewById(R.id.btn_share_wechat).setOnClickListener(this);
        inflate.findViewById(R.id.btn_share_facebook).setOnClickListener(this);
        inflate.findViewById(R.id.btn_share_weibo).setOnClickListener(this);
        inflate.findViewById(R.id.btn_share_line).setOnClickListener(this);
        inflate.findViewById(R.id.btn_share_moments).setOnClickListener(this);
        inflate.findViewById(R.id.btn_share_whatsapp).setOnClickListener(this);
        inflate.findViewById(R.id.btn_share_twitter).setOnClickListener(this);
        inflate.findViewById(R.id.btn_share_qq).setOnClickListener(this);
        inflate.findViewById(R.id.btn_share_more).setOnClickListener(this);
        inflate.findViewById(R.id.btn_share_copy).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.btn_share_wechat) {
            ShareUtil.newInstance().share2WeChat(mShareTitle, mShareContent, mShareUrl,mShareImageUrl);
            if(onClickShareListener != null) onClickShareListener.setOnClickShareListener("wechat");
        } else if (i == R.id.btn_share_facebook) {
            ShareUtil.newInstance().share2FaceBook(mShareTitle, mShareContent, mShareUrl,mShareImageUrl);
            if(onClickShareListener != null) onClickShareListener.setOnClickShareListener("facebook");
        } else if (i == R.id.btn_share_weibo) {
            ShareUtil.newInstance().share2WeiBo(mShareTitle, mShareContent, mShareUrl,mShareImageUrl);
            if(onClickShareListener != null) onClickShareListener.setOnClickShareListener("weibo");
        } else if (i == R.id.btn_share_line) {
            ShareUtil.newInstance().share2Line(mShareTitle, mShareContent, mShareUrl);
            if(onClickShareListener != null) onClickShareListener.setOnClickShareListener("line");
        } else if (i == R.id.btn_share_moments) {
            ShareUtil.newInstance().share2WechatMoments(mShareTitle, mShareContent, mShareUrl,mShareImageUrl);
            if(onClickShareListener != null) onClickShareListener.setOnClickShareListener("朋友圈");
        } else if (i == R.id.btn_share_whatsapp) {
            ShareUtil.newInstance().share2WhatAPPS(mShareTitle, mShareContent, mShareUrl,mShareImageUrl);
            if(onClickShareListener != null) onClickShareListener.setOnClickShareListener("whatapp");
        } else if (i == R.id.btn_share_twitter) {
            ShareUtil.newInstance().share2Twitter(mShareTitle, mShareContent, mShareUrl,mShareImageUrl);
            if(onClickShareListener != null) onClickShareListener.setOnClickShareListener("twitter");
        } else if (i == R.id.btn_share_qq) {
            ShareUtil.newInstance().share2QQ(mShareTitle, mShareContent, mShareUrl,mShareImageUrl);
            if(onClickShareListener != null) onClickShareListener.setOnClickShareListener("qq");
        } else if (i == R.id.btn_share_copy) {
            //获取剪贴板管理器：
            ClipboardManager cm = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
            // 创建普通字符型ClipData
            String shareContent="";
            if (!mShareContent.contains("http")){
                shareContent=mShareUrl+"";
            }else{
                shareContent=mShareContent+"";
            }
            ClipData mClipData = ClipData.newPlainText("Label", shareContent);
            // 将ClipData内容放到系统剪贴板里。
            cm.setPrimaryClip(mClipData);

            Toast.makeText(mContext, ResourceUtils.getString(R.string.coupon_my_redeem_copy), Toast.LENGTH_LONG).show();
        } else if (i == R.id.btn_share_more) {
            //调用系统分享
             Intent intent = new Intent(Intent.ACTION_SEND);
             intent.setType("text/plain");
             intent.putExtra(Intent.EXTRA_TEXT, mShareContent);
            mContext.startActivity(Intent.createChooser(intent,mShareTitle));
        }
        dismiss();
    }


    public BottomShareNewDialog setShareTitle(String shareTitle) {
        mShareTitle = shareTitle;
        mPlatformBean.setTitle(shareTitle);
        return this;
    }

    public BottomShareNewDialog setShareContent(String shareContent) {
        mShareContent = shareContent;
        mPlatformBean.setContent(shareContent);
        return this;
    }

    public BottomShareNewDialog setShareUrl(String shareUrl) {
        mShareUrl = shareUrl;
        mPlatformBean.setShare_url(shareUrl);
        return this;
    }

    public BottomShareNewDialog setShareImageUrl(String shareImageUrl) {
        mShareImageUrl = shareImageUrl;
        mPlatformBean.setImage_url(shareImageUrl);
        return this;
    }
    public interface OnClickShareListener{
        void setOnClickShareListener(String share);
    }
    public void setOnClickShareListener(OnClickShareListener onClickShareListener) {
        this.onClickShareListener = onClickShareListener;
    }
}
*/
