package com.happy.todo.lib_common.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.AnimRes;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import android.view.View;

import java.util.List;

/**
 * @作者: TwoSX
 * @时间: 2017/12/3 下午2:10
 * @描述: Activity 相关工具类
 */
public class ActivityUtil {

    private ActivityUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }


    /**
     * 判断 Activity 是否存在
     *
     * @param packageName 包名
     * @param className   activity 全路径类名
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isActivityExists(@NonNull final String packageName,
                                           @NonNull final String className) {
        Intent intent = new Intent();
        intent.setClassName(packageName, className);
        return !(Utils.getApp().getPackageManager().resolveActivity(intent, 0) == null ||
                intent.resolveActivity(Utils.getApp().getPackageManager()) == null ||
                Utils.getApp().getPackageManager().queryIntentActivities(intent, 0).size() == 0);
    }


    /**
     * 获取 Activity 栈链表
     *
     * @return Activity 栈链表
     */
    public static List<Activity> getActivityList() {
        return Utils.sActivityList;
    }

    /**
     * 获取启动项 Activity
     *
     * @return 启动项 Activity
     */
    public static String getLauncherActivity() {
        return getLauncherActivity(Utils.getApp().getPackageName());
    }

    /**
     * 获取启动项 Activity
     *
     * @param packageName 包名
     * @return 启动项 Activity
     */
    public static String getLauncherActivity(@NonNull final String packageName) {
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PackageManager pm = Utils.getApp().getPackageManager();
        List<ResolveInfo> info = pm.queryIntentActivities(intent, 0);
        for (ResolveInfo aInfo : info) {
            if (aInfo.activityInfo.packageName.equals(packageName)) {
                return aInfo.activityInfo.name;
            }
        }
        return "no " + packageName;
    }

    /**
     * 获取栈顶 Activity
     *
     * @return 栈顶 Activity
     */
    public static Activity getTopActivity() {
        if (Utils.sTopActivityWeakRef != null) {
            Activity activity = Utils.sTopActivityWeakRef.get();
            if (activity != null) {
                return activity;
            }
        }
        List<Activity> activities = Utils.sActivityList;
        int size = activities.size();
        return size > 0 ? activities.get(size - 1) : null;
    }

    /**
     * 判断 Activity 是否存在栈中
     *
     * @param activity activity
     * @return {@code true}: 存在<br>{@code false}: 不存在
     */
    public static boolean isActivityExistsInStack(@NonNull final Activity activity) {
        List<Activity> activities = Utils.sActivityList;
        for (Activity aActivity : activities) {
            if (aActivity.equals(activity)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断 Activity 是否存在栈中
     *
     * @param clz Activity 类
     * @return {@code true}: 存在<br>{@code false}: 不存在
     */
    public static boolean isActivityExistsInStack(@NonNull final Class<?> clz) {
        List<Activity> activities = Utils.sActivityList;
        for (Activity aActivity : activities) {
            if (aActivity.getClass().equals(clz)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 结束 Activity
     *
     * @param activity activity
     */
    public static void finishActivity(@NonNull final Activity activity) {
        finishActivity(activity, false);
    }

    /**
     * 结束 Activity
     *
     * @param activity   activity
     * @param isLoadAnim 是否启动动画
     */
    public static void finishActivity(@NonNull final Activity activity, final boolean isLoadAnim) {
        activity.finish();
        if (!isLoadAnim) {
            activity.overridePendingTransition(0, 0);
        }
    }

    /**
     * 结束 Activity
     *
     * @param activity  activity
     * @param enterAnim 入场动画
     * @param exitAnim  出场动画
     */
    public static void finishActivity(@NonNull final Activity activity,
                                      @AnimRes final int enterAnim,
                                      @AnimRes final int exitAnim) {
        activity.finish();
        activity.overridePendingTransition(enterAnim, exitAnim);
    }

    /**
     * 结束 Activity
     *
     * @param clz Activity 类
     */
    public static void finishActivity(@NonNull final Class<?> clz) {
        finishActivity(clz, false);
    }

    /**
     * 结束 Activity
     *
     * @param clz        Activity 类
     * @param isLoadAnim 是否启动动画
     */
    public static void finishActivity(@NonNull final Class<?> clz, final boolean isLoadAnim) {
        List<Activity> activities = Utils.sActivityList;
        for (Activity activity : activities) {
            if (activity.getClass().equals(clz)) {
                activity.finish();
                if (!isLoadAnim) {
                    activity.overridePendingTransition(0, 0);
                }
            }
        }
    }

    /**
     * 结束 Activity
     *
     * @param clz       Activity 类
     * @param enterAnim 入场动画
     * @param exitAnim  出场动画
     */
    public static void finishActivity(@NonNull final Class<?> clz,
                                      @AnimRes final int enterAnim,
                                      @AnimRes final int exitAnim) {
        List<Activity> activities = Utils.sActivityList;
        for (Activity activity : activities) {
            if (activity.getClass().equals(clz)) {
                activity.finish();
                activity.overridePendingTransition(enterAnim, exitAnim);
            }
        }
    }

    /**
     * 结束到指定 Activity
     *
     * @param activity      activity
     * @param isIncludeSelf 是否结束该 activity 自己
     */
    public static boolean finishToActivity(@NonNull final Activity activity,
                                           final boolean isIncludeSelf) {
        return finishToActivity(activity, isIncludeSelf, false);
    }

    /**
     * 结束到指定 Activity
     *
     * @param activity      activity
     * @param isIncludeSelf 是否结束该 activity 自己
     * @param isLoadAnim    是否启动动画
     */
    public static boolean finishToActivity(@NonNull final Activity activity,
                                           final boolean isIncludeSelf,
                                           final boolean isLoadAnim) {
        List<Activity> activities = Utils.sActivityList;
        for (int i = activities.size() - 1; i >= 0; --i) {
            Activity aActivity = activities.get(i);
            if (aActivity.equals(activity)) {
                if (isIncludeSelf) {
                    finishActivity(aActivity, isLoadAnim);
                }
                return true;
            }
            finishActivity(aActivity, isLoadAnim);
        }
        return false;
    }

    /**
     * 结束到指定 Activity
     *
     * @param activity      activity
     * @param isIncludeSelf 是否结束该 activity 自己
     * @param enterAnim     入场动画
     * @param exitAnim      出场动画
     */
    public static boolean finishToActivity(@NonNull final Activity activity,
                                           final boolean isIncludeSelf,
                                           @AnimRes final int enterAnim,
                                           @AnimRes final int exitAnim) {
        List<Activity> activities = Utils.sActivityList;
        for (int i = activities.size() - 1; i >= 0; --i) {
            Activity aActivity = activities.get(i);
            if (aActivity.equals(activity)) {
                if (isIncludeSelf) {
                    finishActivity(aActivity, enterAnim, exitAnim);
                }
                return true;
            }
            finishActivity(aActivity, enterAnim, exitAnim);
        }
        return false;
    }

    /**
     * 结束到指定 Activity
     *
     * @param clz           Activity 类
     * @param isIncludeSelf 是否结束该 activity 自己
     */
    public static boolean finishToActivity(@NonNull final Class<?> clz,
                                           final boolean isIncludeSelf) {
        return finishToActivity(clz, isIncludeSelf, false);
    }

    /**
     * 结束到指定 Activity
     *
     * @param clz           Activity 类
     * @param isIncludeSelf 是否结束该 activity 自己
     * @param isLoadAnim    是否启动动画
     */
    public static boolean finishToActivity(@NonNull final Class<?> clz,
                                           final boolean isIncludeSelf,
                                           final boolean isLoadAnim) {
        List<Activity> activities = Utils.sActivityList;
        for (int i = activities.size() - 1; i >= 0; --i) {
            Activity aActivity = activities.get(i);
            if (aActivity.getClass().equals(clz)) {
                if (isIncludeSelf) {
                    finishActivity(aActivity, isLoadAnim);
                }
                return true;
            }
            finishActivity(aActivity, isLoadAnim);
        }
        return false;
    }

    /**
     * 结束到指定 Activity
     *
     * @param clz           Activity 类
     * @param isIncludeSelf 是否结束该 activity 自己
     * @param enterAnim     入场动画
     * @param exitAnim      出场动画
     */
    public static boolean finishToActivity(@NonNull final Class<?> clz,
                                           final boolean isIncludeSelf,
                                           @AnimRes final int enterAnim,
                                           @AnimRes final int exitAnim) {
        List<Activity> activities = Utils.sActivityList;
        for (int i = activities.size() - 1; i >= 0; --i) {
            Activity aActivity = activities.get(i);
            if (aActivity.getClass().equals(clz)) {
                if (isIncludeSelf) {
                    finishActivity(aActivity, enterAnim, exitAnim);
                }
                return true;
            }
            finishActivity(aActivity, enterAnim, exitAnim);
        }
        return false;
    }

    /**
     * 结束除最新之外的同类型 Activity
     * <p>也就是让栈中最多只剩下一种类型的 Activity</p>
     *
     * @param clz Activity 类
     */
    public static void finishOtherActivitiesExceptNewest(@NonNull final Class<?> clz) {
        finishOtherActivitiesExceptNewest(clz, false);
    }

    /**
     * 结束除最新之外的同类型 Activity
     * <p>也就是让栈中最多只剩下一种类型的 Activity</p>
     *
     * @param clz        Activity 类
     * @param isLoadAnim 是否启动动画
     */
    public static void finishOtherActivitiesExceptNewest(@NonNull final Class<?> clz,
                                                         final boolean isLoadAnim) {
        List<Activity> activities = Utils.sActivityList;
        boolean flag = false;
        for (int i = activities.size() - 1; i >= 0; i--) {
            Activity activity = activities.get(i);
            if (activity.getClass().equals(clz)) {
                if (flag) {
                    finishActivity(activity, isLoadAnim);
                } else {
                    flag = true;
                }
            }
        }
    }

    /**
     * 结束除最新之外的同类型 Activity
     * <p>也就是让栈中最多只剩下一种类型的 Activity</p>
     *
     * @param clz       Activity 类
     * @param enterAnim 入场动画
     * @param exitAnim  出场动画
     */
    public static void finishOtherActivitiesExceptNewest(@NonNull final Class<?> clz,
                                                         @AnimRes final int enterAnim,
                                                         @AnimRes final int exitAnim) {
        List<Activity> activities = Utils.sActivityList;
        boolean flag = false;
        for (int i = activities.size() - 1; i >= 0; i--) {
            Activity activity = activities.get(i);
            if (activity.getClass().equals(clz)) {
                if (flag) {
                    finishActivity(activity, enterAnim, exitAnim);
                } else {
                    flag = true;
                }
            }
        }
    }

    /**
     * 结束所有 Activity
     */
    public static void finishAllActivities() {
        finishAllActivities(false);
    }

    /**
     * 结束所有 Activity
     *
     * @param isLoadAnim 是否启动动画
     */
    public static void finishAllActivities(final boolean isLoadAnim) {
        List<Activity> activityList = Utils.sActivityList;
        for (int i = activityList.size() - 1; i >= 0; --i) {// 从栈顶开始移除
            Activity activity = activityList.get(i);
            activity.finish();// 在 onActivityDestroyed 发生 remove
            if (!isLoadAnim) {
                activity.overridePendingTransition(0, 0);
            }
        }
    }

    /**
     * 结束所有 Activity
     *
     * @param enterAnim 入场动画
     * @param exitAnim  出场动画
     */
    public static void finishAllActivities(@AnimRes final int enterAnim, @AnimRes final int exitAnim) {
        List<Activity> activityList = Utils.sActivityList;
        for (int i = activityList.size() - 1; i >= 0; --i) {// 从栈顶开始移除
            Activity activity = activityList.get(i);
            activity.finish();// 在 onActivityDestroyed 发生 remove
            activity.overridePendingTransition(enterAnim, exitAnim);
        }
    }

    private static Context getActivityOrApp() {
        Activity topActivity = getTopActivity();
        return topActivity == null ? Utils.getApp() : topActivity;
    }

    private static void startActivity(final Context context,
                                      final Bundle extras,
                                      final String pkg,
                                      final String cls,
                                      final Bundle options) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (extras != null) intent.putExtras(extras);
        intent.setComponent(new ComponentName(pkg, cls));
        startActivity(intent, context, options);
    }

    private static void startActivity(final Intent intent,
                                      final Context context,
                                      final Bundle options) {
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        if (options != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            context.startActivity(intent, options);
        } else {
            context.startActivity(intent);
        }
    }

    private static void startActivities(final Intent[] intents,
                                        final Context context,
                                        final Bundle options) {
        if (!(context instanceof Activity)) {
            for (Intent intent : intents) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
        }
        if (options != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            context.startActivities(intents, options);
        } else {
            context.startActivities(intents);
        }
    }

    private static Bundle getOptionsBundle(final Context context,
                                           final int enterAnim,
                                           final int exitAnim) {
        return ActivityOptionsCompat.makeCustomAnimation(context, enterAnim, exitAnim).toBundle();
    }

    private static Bundle getOptionsBundle(final Activity activity,
                                           final View[] sharedElements) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int len = sharedElements.length;
            @SuppressWarnings("unchecked")
            Pair<View, String>[] pairs = new Pair[len];
            for (int i = 0; i < len; i++) {
                pairs[i] = Pair.create(sharedElements[i], sharedElements[i].getTransitionName());
            }
            return ActivityOptionsCompat.makeSceneTransitionAnimation(activity, pairs).toBundle();
        }
        return ActivityOptionsCompat.makeSceneTransitionAnimation(activity, null, null).toBundle();
    }
}
