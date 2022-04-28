package com.happy.todo.lib_common.widget.pickerselector;

import android.content.Context;
import androidx.annotation.IntDef;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.happy.todo.lib_common.R;
import com.happy.todo.lib_common.utils.ResourceUtils;
import com.happy.todo.lib_common.widget.pickerselector.adapter.PickerAdapter;
import com.happy.todo.lib_common.widget.pickerselector.mode.StringItemView;
import com.happy.todo.lib_common.widget.pickerselector.picker.PicketOptions;
import com.happy.todo.lib_common.widget.pickerselector.picker.WheelPicker;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by iTimeTraveler on 2019/3/20.
 */
public class TimeWheelPicker extends DateWheelPicker {

    private static final int DEFAULT_MIX_DATE_COUNT = 15;
    public static final NumberFormat DEFAULT_MINUTE_FORMAT = new DecimalFormat("###00");
    public static final DateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final DateFormat DEFAULT_TIME_FORMAT = new SimpleDateFormat("HH:mm");
    public static final Format dateFormat = new SimpleDateFormat("MM月dd日");
    public static final Format weekFormat = new SimpleDateFormat("E");
    public static final Format week_date_Format = new SimpleDateFormat(ResourceUtils.getString(R.string.date_type_plane2));
    public static final Format week_date_time_Format= new SimpleDateFormat(ResourceUtils.getString(R.string.date_type_hotel3));

    private static final int TYPE_YEAR  = 1 << 0;
    private static final int TYPE_MONTH = 1 << 1;
    private static final int TYPE_DAY   = 1 << 2;

    private static final int TYPE_HOUR   = 1 << 5;
    private static final int TYPE_MINUTE = 1 << 6;
    private static final int TYPE_AM_PM  = 1 << 7;

    /** 日期聚合 */
    public static final int TYPE_MIXED_DATE = 1 << 3;
    /** 时间聚合 */
    private static final int TYPE_MIXED_TIME = 1 << 4;

    // 时间：小时(12制)、分钟、AM/PM
    public static final int TYPE_12_TIME = TYPE_HOUR | TYPE_MINUTE | TYPE_AM_PM;
    // 时间：小时(24制)、分钟
    public static final int TYPE_24_TIME = TYPE_HOUR | TYPE_MINUTE;

    public static final int TYPE_DEFAULT_TIME = TYPE_MIXED_DATE | TYPE_24_TIME;

    /** 可选项 */
    @IntDef({TYPE_12_TIME, TYPE_24_TIME, TYPE_MIXED_DATE, TYPE_DEFAULT_TIME})
    @Retention(RetentionPolicy.SOURCE)
    private @interface SelectorType {}

    public static final AmPm[] AM_PM_DESC = {AmPm.AM, AmPm.PM};

    @SelectorType private int mType =  TYPE_DEFAULT_TIME;
    private int mCount;
    private List<Integer> mTypeMap = new ArrayList<Integer>();
    private List<Calendar> mMixedDateList = new ArrayList<Calendar>();
    private OnTimeChangedListener mOnTimeChangedListener;

    public TimeWheelPicker(Context context) {
        this(context, null);
    }

    /**
     * 可设置显示项
     * @param type
     */
    public TimeWheelPicker(Context context, @SelectorType int type) {
        super(context, null);
        mType = type;
        init();
    }

    public TimeWheelPicker(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimeWheelPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        parseType();
        PickerAdapter adapter = new PickerAdapter() {

            @Override
            public int numberOfComponentsInWheelPicker(WheelPicker wheelPicker) {
                return mCount;
            }

            @Override
            public int numberOfRowsInComponent(int component) {
                int type = mTypeMap.get(component);
                switch (type) {
                    case TYPE_YEAR:
                        return mMaxDate.get(Calendar.YEAR) - mMinDate.get(Calendar.YEAR);
                    case TYPE_MONTH:
                        return 12;
                    case TYPE_DAY:
                        return 30;
                    case TYPE_HOUR:
                        return is24_Hour() ? 24 : 12;
                    case TYPE_MINUTE:
                        return 60;
                    case TYPE_AM_PM:
                        return AM_PM_DESC.length;
                    case TYPE_MIXED_DATE:
                        return DEFAULT_MIX_DATE_COUNT * 2;
                    case TYPE_MIXED_TIME:
                        break;
                }
                return 0;
            }

            @Override
            public View onCreateView(ViewGroup parent, int row, int component) {
                int type = mTypeMap.get(component);
                return new StringItemView(getDesc(type, row)).onCreateView(parent);
            }

            @Override
            public void onBindView(ViewGroup parent, View convertView, int row, int component) {
                int type = mTypeMap.get(component);
                new StringItemView(getDesc(type, row)).onBindView(parent, convertView, row);
            }

            @Override
            public String labelOfComponent(int component) {
                int type = mTypeMap.get(component);
                switch (type) {
                    case TYPE_YEAR:
                        //return "年";
                    case TYPE_MONTH:
                        //return "月";
                    case TYPE_DAY:
                        //return "日";
                    case TYPE_HOUR:
                        return ":";
                        //return "时";
                    case TYPE_MINUTE:
                        return "";
                        //return "分";
                    case TYPE_MIXED_DATE:
                        break;
                    case TYPE_MIXED_TIME:
                        break;
                }
                return "";
            }
        };

        setOptions(new PicketOptions.Builder()
                .dividedEqually(false)
                .build());
        setAdapter(adapter);

        // 默认选中项
        setDefaultSelecteTime(mCurrentDate);
        }

        public void setDefaultSelecteTime(Calendar currentDate){
            for (int i = 0; i < mCount; i++) {
                int type = mTypeMap.get(i);
                switch (type) {
                    case TYPE_YEAR:
                        setSelection(i,  currentDate.get(Calendar.YEAR) - mMinDate.get(Calendar.YEAR));
                        break;
                    case TYPE_MONTH:
                        setSelection(i, currentDate.get(Calendar.MONTH));
                        break;
                    case TYPE_DAY:
                        setSelection(i, currentDate.get(Calendar.DAY_OF_MONTH));
                        break;
                    case TYPE_HOUR:
                        setSelection(i, is24_Hour() ? currentDate.get(Calendar.HOUR_OF_DAY) : currentDate.get(Calendar.HOUR));
                        break;
                    case TYPE_MINUTE:
                        setSelection(i, currentDate.get(Calendar.MINUTE));
                        break;
                    case TYPE_AM_PM:
                        if ( ! is24_Hour()) {
                            setSelection(i, currentDate.get(Calendar.AM_PM));
                        }
                        break;
                    case TYPE_MIXED_DATE:
                        setSelection(i, DEFAULT_MIX_DATE_COUNT);
                        break;
                    case TYPE_MIXED_TIME:
                        break;
                }
        }

        setOnItemSelectedListener(new WheelPicker.OnItemSelectedListener() {
            @Override
            public void onItemSelected(WheelPicker parentView, int[] position) {
                Calendar result = Calendar.getInstance();
                int count = position.length;
                for (int i = 0; i < count; i++) {
                    int v = position[i];
                    int type = mTypeMap.get(i);
                    switch (type) {
                        case TYPE_YEAR:
                            result.set(Calendar.YEAR, mMinDate.get(Calendar.YEAR) + v);
                            break;
                        case TYPE_MONTH:
                            result.set(Calendar.MONTH, v);
                            break;
                        case TYPE_DAY:
                            result.set(Calendar.DAY_OF_MONTH, v);
                            break;
                        case TYPE_HOUR:
                            // 处理12小时制或24小时制
                            if ( ! is24_Hour()) {
                                int am_pm = mTypeMap.indexOf(TYPE_AM_PM);
                                int index = position[am_pm];
                                v = AM_PM_DESC[index].getId() == AmPm.PM.getId() ? v : v % 12;
                            }
                            result.set(Calendar.HOUR_OF_DAY, v);
                            break;
                        case TYPE_MINUTE:
                            result.set(Calendar.MINUTE, v);
                            break;
                        case TYPE_AM_PM:
                            break;
                        case TYPE_MIXED_DATE:
                            result = mMixedDateList.get(v);
                            break;
                        case TYPE_MIXED_TIME:
                            // TODO
                            break;
                    }
                }
                //当前当地时间
                Calendar  Calendar_now = getCalendarForLocale(Calendar.getInstance(), Locale.getDefault());

                if (mOnTimeChangedListener != null) {
                    Calendar.getInstance().getTimeInMillis();
                    mOnTimeChangedListener.onTimeChanged(TimeWheelPicker.this, result);
                }
            }
        });
    }

    private void parseType() {
        mCount = Integer.bitCount(mType);

        for (int i = 0; i < 32; i++) {
            int t = ((mType << (31 - i)) >>> 31) << i;
            if ((t & (1 << i)) > 0) {
                mTypeMap.add(t);
            }
        }

        if ((mType & TYPE_MIXED_DATE) > 0) {
            mMixedDateList = generateDateList(DEFAULT_MIX_DATE_COUNT);
        }
    }

    /**
     * 是否是24小时制
     */
    private boolean is24_Hour() {
        return !mTypeMap.contains(TYPE_AM_PM);
    }

    private List<Calendar> generateDateList(int daysCount){
        final int todayIdx = daysCount;
        Calendar[] arr = new Calendar[daysCount * 2 + 1];
        for(int i = 1; i <= daysCount; i++){
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            c.add(Calendar.DAY_OF_MONTH, -i);   // 今天-1天
            arr[todayIdx - i] = c;

            Calendar c1 = Calendar.getInstance();
            c1.setTime(new Date());
            c1.add(Calendar.DAY_OF_MONTH, i);   // 今天+1天
            arr[todayIdx + i] = c1;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        arr[todayIdx] = c;

        return Arrays.asList(arr);
    }

    private String getDesc(int type, int row) {
        switch (type) {
            case TYPE_YEAR:
                return String.valueOf(mMinDate.get(Calendar.YEAR) + row);
            case TYPE_MONTH:
            case TYPE_DAY:
            case TYPE_HOUR:
                return String.valueOf(row);
            case TYPE_MINUTE:
                return DEFAULT_MINUTE_FORMAT.format(row);
            case TYPE_AM_PM:
                return AM_PM_DESC[row].getDesc();
            case TYPE_MIXED_DATE:
                Calendar c = mMixedDateList.get(row);
                return week_date_Format.format(c.getTime());
            case TYPE_MIXED_TIME:
                break;
        }
        return null;
    }

    public void setOnTimeChangedListener(OnTimeChangedListener onTimeChangedListener) {
        mOnTimeChangedListener = onTimeChangedListener;
    }

    private enum AmPm {
        AM(0, "AM"),
        PM(1, "PM");

        private int id;
        private String desc;

        private AmPm(int id, String desc) {
            this.id = id;
            this.desc = desc;
        }

        int getId() { return id; }
        String getDesc() { return desc; }
    }

    /**
     * The callback interface used to indicate the time has been adjusted.
     */
    public interface OnTimeChangedListener {

        /**
         * @param view The view associated with this listener.
         * @param date The select date.
         */
        void onTimeChanged(TimeWheelPicker view, Calendar date);
    }
}
