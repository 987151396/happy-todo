package com.happy.todo.lib_common.utils.language;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;

import com.happy.todo.lib_common.db.DBHelper;
import com.happy.todo.lib_common.event.LanguageEvent;
import com.happy.todo.lib_common.utils.AppUtil;
import com.happy.todo.lib_common.utils.SPUtil;
import com.happy.todo.lib_common.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.util.Locale;

/**
 * @author: TwoSX
 * @description:
 * @projectName: iapp2
 * @date: 2017/11/24
 * @time: 下午5:54
 */
public class LanguageUtil {
    public static final String SP_LANG = "ebbly_lang";
    private static LanguageUtil sLanguageUtil;
    private Locale thLocale;
    private static final String ERROR_LABEL = "";

    public static LanguageUtil newInstance() {
        if (sLanguageUtil == null) {
            synchronized (LanguageUtil.class) {
                if (sLanguageUtil == null) {
                    sLanguageUtil = new LanguageUtil();
                }
            }
        }
        return sLanguageUtil;
    }


    private LanguageUtil() {
        thLocale = new Locale("th", "TH");
    }

    private int getSpLanguageType() {
        return (int) SPUtil.newInstance().get(SP_LANG, LanguageType.FOLLOW_SYSTEM);
    }

    private void setSpLanguageType(int lang) {
        SPUtil.newInstance().putAndApply(SP_LANG, lang);
    }

    /**
     * 如果不是英文、简体中文、繁体中文，默认返回英文
     * 自己定义的是跟随系统、英文、简体中文、繁体中文四种格式
     *
     * @return
     */
    private Locale getTargetLocale(Context context) {
        int langType = getSpLanguageType();
        switch (langType) {
            case LanguageType.FOLLOW_SYSTEM:
                Locale sysType = getSysLocale(context);

                if (sysType.getLanguage().equals(Locale.ENGLISH.getLanguage())) {
                    setSpLanguageType(LanguageType.ENGLISH);
                    return Locale.ENGLISH;
                } else if ((sysType.getLanguage()+sysType.getCountry()).equals(Locale.SIMPLIFIED_CHINESE.getLanguage()+""+Locale.SIMPLIFIED_CHINESE.getCountry())) {
                    setSpLanguageType(LanguageType.SIMPLIFIED_CHINESE);
                    return Locale.SIMPLIFIED_CHINESE;
                } else if ((sysType.getLanguage()+sysType.getCountry()).equals(Locale.TRADITIONAL_CHINESE.getLanguage()+""+Locale.TRADITIONAL_CHINESE.getCountry())) {
                    setSpLanguageType(LanguageType.TRADITIONAL_CHINESE);
                    return Locale.TRADITIONAL_CHINESE;
                } else if (sysType.getLanguage().equals(Locale.JAPANESE.getLanguage())) {
                    setSpLanguageType(LanguageType.JAPANESE);
                    return Locale.JAPANESE;
                } else if (sysType.getLanguage().equals(thLocale.getLanguage())) {
                    setSpLanguageType(LanguageType.THAI);
                    return thLocale;
                }
                // 默认为 英文，保存下
                setSpLanguageType(LanguageType.ENGLISH);
                return Locale.ENGLISH;
            case LanguageType.SIMPLIFIED_CHINESE:
                return Locale.SIMPLIFIED_CHINESE;
            case LanguageType.TRADITIONAL_CHINESE:
                return Locale.TRADITIONAL_CHINESE;
            case LanguageType.JAPANESE:
                return Locale.JAPANESE;

            case LanguageType.THAI:
                return thLocale;
        }
        return Locale.ENGLISH;
    }

    //6.0以上获取方式需要特殊处理一下
    public Locale getSysLocale(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            return context.getResources().getConfiguration().locale;
        } else {
            return context.getResources().getConfiguration().getLocales().get(0);
        }
    }

    // 修改了语言之后，修改SharedPreferences里面的配置信息，通过 EventBus 进行通知页面进行重新加载。
    public void updateLanguage(int languageType) {
        if (languageType != getSpLanguageType()) {
            setSpLanguageType(languageType);
            changeAppLanguage(Utils.getApp());

            Locale targetLocale = getTargetLocale(Utils.getApp().getApplicationContext());
            DBHelper.getInstance().getEbblyDatabase().getOpenHelper().getWritableDatabase().setLocale(targetLocale);

            EventBus.getDefault().post(new LanguageEvent());
            //ARouter.getInstance().build(RouterPath.MAIN).navigation();
        }
    }

    public void updateLanguageForWeb(int languageType) {
        if (languageType != getSpLanguageType()) {
            setSpLanguageType(languageType);
            LanguageUtil.newInstance().changeAppLanguage(Utils.getApp());

            Locale targetLocale = getTargetLocale(Utils.getApp().getApplicationContext());
            DBHelper.getInstance().getEbblyDatabase().getOpenHelper().getWritableDatabase().setLocale(targetLocale);
        }
    }

    /**
     * 这里是获取对应的配置信息代表的缩写
     */
    public String getLanguageCode() {
        Locale targetLocale = getTargetLocale(Utils.getApp().getApplicationContext());
        if (targetLocale.equals(Locale.SIMPLIFIED_CHINESE)) {
            return "zh-cn";
        } else if (targetLocale.equals(Locale.TRADITIONAL_CHINESE)) {
            return "zh-tw";
        } else if (targetLocale.equals(Locale.JAPANESE)) {
            return "ja-JP";
        } else if (targetLocale.equals(Locale.ENGLISH)) {
            return "en-us";
        } else if (targetLocale.equals(thLocale)) {
            return "th-TH";
        }
        return "en-us";
    }


    /**
     * 语言文字描述
     *
     * @return
     */
    public String getLanguageDesc() {
        String languageCode = LanguageUtil.newInstance().getLanguageCode();
        switch (languageCode) {
            case "zh-cn":
                return "简体中文";
            case "zh-tw":
                return "繁体中文";
            case "ja-JP":
                return "日本語";
            case "en-us":
                return "English";
            default:
                return "English";
        }
    }


    public Context changeAppLanguage(Context context) {
        // app locale
        Locale locale = getTargetLocale(context);
        Locale.setDefault(locale);
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            configuration.setLocale(locale);
        } else {
            configuration.locale = locale;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            context = context.createConfigurationContext(configuration);
        } else {
            context.getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());
        }

        return context;
    }


    /**
     * 获取application Resources
     *
     * @param pm
     * @param pkgName
     * @param l
     * @return
     */
    private static Resources getApplicationResource(PackageManager pm, String pkgName, Locale l) {
        Resources resourceForApplication = null;
        try {
            resourceForApplication = pm.getResourcesForApplication(pkgName);
            Configuration config = resourceForApplication.getConfiguration();
            config.locale = l;
            resourceForApplication.updateConfiguration(config, null);
        } catch (PackageManager.NameNotFoundException ignored) {

        }
        return resourceForApplication;
    }

    /**
     * 根据当前语言设置读取指定的语言包字符串
     *
     * @param stringId
     * @return
     */
    public static String getStringByLocale(int stringId, Object... args) {

        Context context = Utils.getApp();
        String packageName = AppUtil.getAppPackageName(); //获取程序包名

        Resources resources = getApplicationResource(context.getApplicationContext().getPackageManager(),
                packageName, newInstance().getTargetLocale(context));
        if (resources == null) {
            return ERROR_LABEL;
        } else {
            try {
                return resources.getString(stringId, args);
            } catch (Exception e) {
                return ERROR_LABEL;
            }
        }

    }


    public Context attachBaseContext(Context context) {
        return changeAppLanguage(context);
    }



    public boolean isZhCn() {
        return getLanguageCode().equals("zh-cn");
    }
    public boolean isZhTw() {
        return getLanguageCode().equals("zh-tw");
    }
    public boolean isEnUs() {
        return getLanguageCode().equals("en-us");
    }

}
