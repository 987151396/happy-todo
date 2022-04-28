package com.happy.todo.lib_common.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.webkit.WebSettings;

import com.happy.todo.lib_common.BuildConfig;
import com.meituan.android.walle.WalleChannelReader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Description: APP 管理类
 * Author: 贰师兄
 * Date: 2017/10/12 下午2:58
 */

public class AppUtil {
    /**
     * 私有的构造方法.
     */
    private AppUtil() {

    }

    /**
     * 判断是否为 debug
     *
     * @return
     */
    public static boolean isDebug() {
        return BuildConfig.IS_DEBUG;
    }

    /**
     * 获取userAgent，用于网络请求
     *
     * @return 系统相关的信息
     */
    public static String getUserAgent() {
        // ==============================================================
        //		User-Agent
        //		格式：
        //		应用名称;应用版本;平台;OS版本;OS版本名称;厂商;机型;分辨率(宽*高);安装渠道;网络;
        //		示例：
        //		HET;2.2.0;Android;4.2.2;N7100XXUEMI6BYTuifei;samsung;GT-I9300;480*800;360;WIFI;
        return getAppName() + ";" + getAppVersionName() + ";" + "Android/" + DeviceUtil
                .getSDKVersion() + ";" + DeviceUtil.getManufacturer() + "/" + DeviceUtil.getModel
                () + "/" + DeviceUtil.getAndroidID() + ";" + getChannel() + ";" + NetworkUtil
                .getNetworkType().toString();
    }

    /**
     * 获取userAgent，用于webview
     *
     * @return 系统相关的信息
     */
    public static String getUserAgentByWeb() {
        String userAgent = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            try {
                userAgent = WebSettings.getDefaultUserAgent(Utils.getApp());
            } catch (Exception e) {
                userAgent = System.getProperty("http.agent");
            }
        } else {
            userAgent = System.getProperty("http.agent");
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0, length = userAgent.length(); i < length; i++) {
            char c = userAgent.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                sb.append(String.format("\\u%04x", (int) c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 获取当前渠道
     */
    public static String getChannel() {
        return WalleChannelReader.getChannel(Utils.getApp());
    }

    /**
     * 获取当前国家的语言
     */
    public static String getCurCountryLan(Context context) {
        Configuration config = context.getResources().getConfiguration();
        Locale sysLocale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            sysLocale = config.getLocales().get(0);
        } else {
            //noinspection deprecation
            sysLocale = config.locale;
        }
        return sysLocale.getLanguage()
                + "-"
                + sysLocale.getCountry();
    }

    /**
     * 判断是否为中文环境
     */
    public static boolean isZhCN() {
        Configuration config = Utils.getApp().getResources().getConfiguration();
        Locale sysLocale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            sysLocale = config.getLocales().get(0);
        } else {
            //noinspection deprecation
            sysLocale = config.locale;
        }
        String lang = sysLocale.getCountry();
        return lang.equalsIgnoreCase("CN");
    }

    /**
     * 判断 App 是否安装
     *
     * @param action   action
     * @param category category
     * @return {@code true}: 已安装<br>{@code false}: 未安装
     */
    public static boolean isInstallApp(final String action, final String category) {
        Intent intent = new Intent(action);
        intent.addCategory(category);
        PackageManager pm = Utils.getApp().getPackageManager();
        ResolveInfo info = pm.resolveActivity(intent, 0);
        return info != null;
    }

    /**
     * 判断 App 是否安装
     *
     * @param packageName 包名
     * @return {@code true}: 已安装<br>{@code false}: 未安装
     */
    public static boolean isInstallApp(final String packageName) {
        return !isSpace(packageName) && IntentUtil.getLaunchAppIntent(packageName) != null;
    }

    /**
     * 安装 App(支持 8.0)
     * <p>8.0 需添加权限 {@code <uses-permission android:name="android.permission
     * .REQUEST_INSTALL_PACKAGES" />}</p>
     *
     * @param filePath  文件路径
     * @param authority 7.0 及以上安装需要传入清单文件中的{@code <provider>}的 authorities 属性
     *                  <br>参看 https://developer.android
     *                  .com/reference/android/support/v4/content/FileProvider.html
     */
    public static void installApp(final String filePath, final String authority) {
        installApp(FileUtil.getFileByPath(filePath), authority);
    }

    /**
     * 安装 App（支持 8.0）
     * <p>8.0 需添加权限 {@code <uses-permission android:name="android.permission
     * .REQUEST_INSTALL_PACKAGES" />}</p>
     *
     * @param file      文件
     * @param authority 7.0 及以上安装需要传入清单文件中的{@code <provider>}的 authorities 属性
     *                  <br>参看 https://developer.android
     *                  .com/reference/android/support/v4/content/FileProvider.html
     */
    public static void installApp(final File file, final String authority) {
        if (!FileUtil.isFileExists(file))
            return;
        Utils.getApp().startActivity(IntentUtil.getInstallAppIntent(file, authority));
    }

    /**
     * 安装 App（支持 8.0）
     * <p>8.0 需添加权限 {@code <uses-permission android:name="android.permission
     * .REQUEST_INSTALL_PACKAGES" />}</p>
     *
     * @param activity    activity
     * @param filePath    文件路径
     * @param authority   7.0 及以上安装需要传入清单文件中的{@code <provider>}的 authorities 属性
     *                    <br>参看 https://developer.android
     *                    .com/reference/android/support/v4/content/FileProvider.html
     * @param requestCode 请求值
     */
    public static void installApp(final Activity activity, final String filePath, final String
            authority, final int requestCode) {
        installApp(activity, FileUtil.getFileByPath(filePath), authority, requestCode);
    }

    /**
     * 安装 App（支持 8.0）
     * <p>8.0 需添加权限 {@code <uses-permission android:name="android.permission
     * .REQUEST_INSTALL_PACKAGES" />}</p>
     *
     * @param activity    activity
     * @param file        文件
     * @param authority   7.0 及以上安装需要传入清单文件中的{@code <provider>}的 authorities 属性
     *                    <br>参看 https://developer.android
     *                    .com/reference/android/support/v4/content/FileProvider.html
     * @param requestCode 请求值
     */
    public static void installApp(final Activity activity, final File file, final String
            authority, final int requestCode) {
        if (!FileUtil.isFileExists(file))
            return;
        activity.startActivityForResult(IntentUtil.getInstallAppIntent(file, authority),
                requestCode);
    }

    /**
     * 静默安装 App
     * <p>非 root 需添加权限 {@code <uses-permission android:name="android.permission.INSTALL_PACKAGES"
     * />}</p>
     *
     * @param filePath 文件路径
     * @return {@code true}: 安装成功<br>{@code false}: 安装失败
     */
    public static boolean installAppSilent(final String filePath) {
        File file = FileUtil.getFileByPath(filePath);
        if (!FileUtil.isFileExists(file))
            return false;
        String command = "LD_LIBRARY_PATH=/vendor/lib:/system/lib pm install " + filePath;
        ShellUtil.CommandResult commandResult = ShellUtil.execCmd(command, !isSystemApp(), true);
        return commandResult.successMsg != null && commandResult.successMsg.toLowerCase()
                .contains("success");
    }

    /**
     * 卸载 App
     *
     * @param packageName 包名
     */
    public static void uninstallApp(final String packageName) {
        if (isSpace(packageName))
            return;
        Utils.getApp().startActivity(IntentUtil.getUninstallAppIntent(packageName));
    }

    /**
     * 卸载 App
     *
     * @param activity    activity
     * @param packageName 包名
     * @param requestCode 请求值
     */
    public static void uninstallApp(final Activity activity, final String packageName, final int
            requestCode) {
        if (isSpace(packageName))
            return;
        activity.startActivityForResult(IntentUtil.getUninstallAppIntent(packageName), requestCode);
    }

    /**
     * 静默卸载 App
     * <p>非 root 需添加权限 {@code <uses-permission android:name="android.permission.DELETE_PACKAGES"
     * />}</p>
     *
     * @param packageName 包名
     * @param isKeepData  是否保留数据
     * @return {@code true}: 卸载成功<br>{@code false}: 卸载失败
     */
    public static boolean uninstallAppSilent(final String packageName, final boolean isKeepData) {
        if (isSpace(packageName))
            return false;
        String command = "LD_LIBRARY_PATH=/vendor/lib:/system/lib pm uninstall " + (isKeepData ?
                "-k " : "") + packageName;
        ShellUtil.CommandResult commandResult = ShellUtil.execCmd(command, !isSystemApp(), true);
        return commandResult.successMsg != null && commandResult.successMsg.toLowerCase()
                .contains("success");
    }


    /**
     * 判断 App 是否有 root 权限
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isAppRoot() {
        ShellUtil.CommandResult result = ShellUtil.execCmd("echo root", true);
        if (result.result == 0) {
            return true;
        }
        if (result.errorMsg != null) {
            Log.d("AppUtils", "isAppRoot() called" + result.errorMsg);
        }
        return false;
    }

    /**
     * 打开 App
     *
     * @param packageName 包名
     */
    public static void launchApp(final String packageName) {
        if (isSpace(packageName))
            return;
        Utils.getApp().startActivity(IntentUtil.getLaunchAppIntent(packageName));
    }

    /**
     * 打开 App
     *
     * @param activity    activity
     * @param packageName 包名
     * @param requestCode 请求值
     */
    public static void launchApp(final Activity activity, final String packageName, final int
            requestCode) {
        if (isSpace(packageName))
            return;
        activity.startActivityForResult(IntentUtil.getLaunchAppIntent(packageName), requestCode);
    }

    /**
     * 关闭 App
     */
    public static void exitApp() {
        List<Activity> activityList = Utils.sActivityList;
        for (int i = activityList.size() - 1; i >= 0; --i) {
            activityList.get(i).finish();
            activityList.remove(i);
        }
        System.exit(0);
    }

    /**
     * 获取 App 包名
     *
     * @return App 包名
     */
    public static String getAppPackageName() {
        return Utils.getApp().getPackageName();
    }

    /**
     * 获取 App 具体设置
     */
    public static void getAppDetailsSettings() {
        getAppDetailsSettings(Utils.getApp().getPackageName());
    }

    /**
     * 获取 App 具体设置
     *
     * @param packageName 包名
     */
    public static void getAppDetailsSettings(final String packageName) {
        if (isSpace(packageName))
            return;
        Utils.getApp().startActivity(IntentUtil.getAppDetailsSettingsIntent(packageName));
    }

    /**
     * 获取 App 名称
     *
     * @return App 名称
     */
    public static String getAppName() {
        return getAppName(Utils.getApp().getPackageName());
    }

    /**
     * 获取 App 名称
     *
     * @param packageName 包名
     * @return App 名称
     */
    public static String getAppName(final String packageName) {
        if (isSpace(packageName))
            return null;
        try {
            PackageManager pm = Utils.getApp().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.applicationInfo.loadLabel(pm).toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取 App 图标
     *
     * @return App 图标
     */
    public static Drawable getAppIcon() {
        return getAppIcon(Utils.getApp().getPackageName());
    }

    /**
     * 获取 App 图标
     *
     * @param packageName 包名
     * @return App 图标
     */
    public static Drawable getAppIcon(final String packageName) {
        if (isSpace(packageName))
            return null;
        try {
            PackageManager pm = Utils.getApp().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.applicationInfo.loadIcon(pm);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取 App 路径
     *
     * @return App 路径
     */
    public static String getAppPath() {
        return getAppPath(Utils.getApp().getPackageName());
    }

    /**
     * 获取 App 路径
     *
     * @param packageName 包名
     * @return App 路径
     */
    public static String getAppPath(final String packageName) {
        if (isSpace(packageName))
            return null;
        try {
            PackageManager pm = Utils.getApp().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.applicationInfo.sourceDir;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取 App 版本号
     *
     * @return App 版本号
     */
    public static String getAppVersionName() {
        return getAppVersionName(Utils.getApp().getPackageName());
    }

    /**
     * 获取 App 版本号
     *
     * @param packageName 包名
     * @return App 版本号
     */
    public static String getAppVersionName(final String packageName) {
        if (isSpace(packageName))
            return null;
        try {
            PackageManager pm = Utils.getApp().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取 App 版本码
     *
     * @return App 版本码
     */
    public static int getAppVersionCode() {
        return getAppVersionCode(Utils.getApp().getPackageName());
    }

    /**
     * 获取 App 版本码
     *
     * @param packageName 包名
     * @return App 版本码
     */
    public static int getAppVersionCode(final String packageName) {
        if (isSpace(packageName))
            return -1;
        try {
            PackageManager pm = Utils.getApp().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? -1 : pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 判断 App 是否是系统应用
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isSystemApp() {
        return isSystemApp(Utils.getApp().getPackageName());
    }

    /**
     * 判断 App 是否是系统应用
     *
     * @param packageName 包名
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isSystemApp(final String packageName) {
        if (isSpace(packageName))
            return false;
        try {
            PackageManager pm = Utils.getApp().getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo(packageName, 0);
            return ai != null && (ai.flags & ApplicationInfo.FLAG_SYSTEM) != 0;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断 App 是否是 Debug 版本
     *
     * @param packageName 包名
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isAppDebug(final String packageName) {
        if (isSpace(packageName))
            return false;
        try {
            PackageManager pm = Utils.getApp().getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo(packageName, 0);
            return ai != null && (ai.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断 App 是否处于前台
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isAppForeground() {
        ActivityManager manager = (ActivityManager) Utils.getApp().getSystemService(Context
                .ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> info = manager.getRunningAppProcesses();
        if (info == null || info.size() == 0)
            return false;
        for (ActivityManager.RunningAppProcessInfo aInfo : info) {
            if (aInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return aInfo.processName.equals(Utils.getApp().getPackageName());
            }
        }
        return false;
    }

    /**
     * 判断 App 是否处于前台
     * <p>当不是查看当前 App，且 SDK 大于 21 时，
     * 需添加权限 {@code <uses-permission android:name="android.permission.PACKAGE_USAGE_STATS"/>}</p>
     *
     * @param packageName 包名
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isAppForeground(final String packageName) {
        return !isSpace(packageName) && packageName.equals(ProcessUtil.getForegroundProcessName());
    }

    /**
     * 封装 App 信息的 Bean 类
     */
    public static class AppInfo {

        private String name;
        private Drawable icon;
        private String packageName;
        private String packagePath;
        private String versionName;
        private int versionCode;
        private boolean isSystem;

        public Drawable getIcon() {
            return icon;
        }

        public void setIcon(final Drawable icon) {
            this.icon = icon;
        }

        public boolean isSystem() {
            return isSystem;
        }

        public void setSystem(final boolean isSystem) {
            this.isSystem = isSystem;
        }

        public String getName() {
            return name;
        }

        public void setName(final String name) {
            this.name = name;
        }

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(final String packageName) {
            this.packageName = packageName;
        }

        public String getPackagePath() {
            return packagePath;
        }

        public void setPackagePath(final String packagePath) {
            this.packagePath = packagePath;
        }

        public int getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(final int versionCode) {
            this.versionCode = versionCode;
        }

        public String getVersionName() {
            return versionName;
        }

        public void setVersionName(final String versionName) {
            this.versionName = versionName;
        }

        /**
         * @param name        名称
         * @param icon        图标
         * @param packageName 包名
         * @param packagePath 包路径
         * @param versionName 版本号
         * @param versionCode 版本码
         * @param isSystem    是否系统应用
         */
        public AppInfo(String packageName, String name, Drawable icon, String packagePath,
                       String versionName, int versionCode, boolean isSystem) {
            this.setName(name);
            this.setIcon(icon);
            this.setPackageName(packageName);
            this.setPackagePath(packagePath);
            this.setVersionName(versionName);
            this.setVersionCode(versionCode);
            this.setSystem(isSystem);
        }

        @Override
        public String toString() {
            return "pkg name: " + getPackageName() +
                    "\napp name: " + getName() +
                    "\napp path: " + getPackagePath() +
                    "\napp v name: " + getVersionName() +
                    "\napp v code: " + getVersionCode() +
                    "\nis system: " + isSystem();
        }
    }

    /**
     * 获取 App 信息
     * <p>AppInfo（名称，图标，包名，版本号，版本 Code，是否系统应用）</p>
     *
     * @return 当前应用的 AppInfo
     */
    public static AppInfo getAppInfo() {
        return getAppInfo(Utils.getApp().getPackageName());
    }

    /**
     * 获取 App 信息
     * <p>AppInfo（名称，图标，包名，版本号，版本 Code，是否系统应用）</p>
     *
     * @param packageName 包名
     * @return 当前应用的 AppInfo
     */
    public static AppInfo getAppInfo(final String packageName) {
        try {
            PackageManager pm = Utils.getApp().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return getBean(pm, pi);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 得到 AppInfo 的 Bean
     *
     * @param pm 包的管理
     * @param pi 包的信息
     * @return AppInfo 类
     */
    private static AppInfo getBean(final PackageManager pm, final PackageInfo pi) {
        if (pm == null || pi == null)
            return null;
        ApplicationInfo ai = pi.applicationInfo;
        String packageName = pi.packageName;
        String name = ai.loadLabel(pm).toString();
        Drawable icon = ai.loadIcon(pm);
        String packagePath = ai.sourceDir;
        String versionName = pi.versionName;
        int versionCode = pi.versionCode;
        boolean isSystem = (ApplicationInfo.FLAG_SYSTEM & ai.flags) != 0;
        return new AppInfo(packageName, name, icon, packagePath, versionName, versionCode,
                isSystem);
    }

    /**
     * 获取所有已安装 App 信息
     * <p>{@link #getBean(PackageManager, PackageInfo)}（名称，图标，包名，包路径，版本号，版本 Code，是否系统应用）</p>
     * <p>依赖上面的 getBean 方法</p>
     *
     * @return 所有已安装的 AppInfo 列表
     */
    public static List<AppInfo> getAppsInfo() {
        List<AppInfo> list = new ArrayList<>();
        PackageManager pm = Utils.getApp().getPackageManager();
        // 获取系统中安装的所有软件信息
        List<PackageInfo> installedPackages = pm.getInstalledPackages(0);
        for (PackageInfo pi : installedPackages) {
            AppInfo ai = getBean(pm, pi);
            if (ai == null)
                continue;
            list.add(ai);
        }
        return list;
    }

    private static boolean isSpace(final String s) {
        if (s == null)
            return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 版本号比较
     * compareTo()方法返回值为int类型，就是比较两个值，如：x.compareTo(y)。如果前者大于后者，返回1，前者等于后者则返回0，前者小于后者则返回-1
     * @param s1
     * @param s2
     * @return 范围可以是"2.20.", "2.10.0"  ,".20","2.10.0",2.1.3 ，3.7.5，10.2.0
     */
    public static int compareVersion(String s1, String s2) {
        String[] s1Split = s1.split("\\.", -1);
        String[] s2Split = s2.split("\\.", -1);
        int len1 = s1Split.length;
        int len2 = s2Split.length;
        int lim = Math.min(len1, len2);
        int i = 0;
        while (i < lim) {
            int c1 = "".equals(s1Split[i]) ? 0 : Integer.parseInt(s1Split[i]);
            int c2 = "".equals(s2Split[i]) ? 0 : Integer.parseInt(s2Split[i]);
            if (c1 != c2) {
                return c1 - c2;
            }
            i++;
        }
        return len1 - len2;
    }

}
