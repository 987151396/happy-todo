package com.happy.todo.lib_common.widget.dialog;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.widget.DatePicker;

import com.happy.todo.lib_common.R;
import com.happy.todo.lib_common.base.BaseAppPopWindow;
import com.happy.todo.lib_common.utils.AppLogUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @作者: TwoSX
 * @时间: 2018/1/13 下午1:29
 * @描述: 底部时间选择弹窗
 */
public class AppBottomDateDialog extends BaseAppPopWindow {

    private DatePicker mDatePicker;
    private Date mDefaultDate;


    public AppBottomDateDialog(Context context) {
        super(context);
        setOnBeforeShowCallback((popupRootView, anchorView, hasShowAnima) -> {
            backgroundAlpha(activity,0.7f);
            return true;
        });

        setOnBeforeShowCallback((popupRootView, anchorView, hasShowAnima) -> false);

        mDatePicker = (DatePicker) findViewById(R.id.date_picker);

        mDatePicker.setMaxDate(System.currentTimeMillis());
        findViewById(R.id.tv_ok).setOnClickListener(view -> dismiss());
    }

    public AppBottomDateDialog(Context context, Date defaultData) {
        super(context);
        setOnBeforeShowCallback((popupRootView, anchorView, hasShowAnima) -> {
            backgroundAlpha(activity,0.7f);
            return true;
        });
        mDefaultDate = defaultData;
        mDatePicker = (DatePicker) findViewById(R.id.date_picker);
        try {
            if (defaultData!=null) {
                Calendar calendar = Calendar.getInstance();//日历对象
                calendar.setTime(defaultData);//设置当前日期

                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DATE);
                AppLogUtil.e("-----当前日期--》" + year + "---" + month + "---" + day);
                setCurrentData(year, month, day);
            }else{
                //如果默认为空时，选中当前时间
                mDatePicker.setMaxDate(System.currentTimeMillis());
            }

        } catch (Exception e) {
            AppLogUtil.e("数据出错");
        }

//        mDatePicker.setMaxDate(System.currentTimeMillis());
        findViewById(R.id.tv_ok).setOnClickListener(view -> dismiss());
    }

    public AppBottomDateDialog setOkClickListener(OnOkClickListener okClickListener) {
        if (okClickListener != null) {
            findViewById(R.id.tv_ok).setOnClickListener(view -> {
                String dateStr = mDatePicker.getYear() + "-" + (mDatePicker.getMonth() + 1) + "-" +
                        mDatePicker.getDayOfMonth();
                try {
                    Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
                    okClickListener.onClick(this, date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                dismiss();
            });
        }
        return this;
    }


    public AppBottomDateDialog setOkClickListener(OnOkClickListener okClickListener, long maxDate, long minDate) {
        if (okClickListener != null) {
            try {
                //当前时间不在该区间∏
                mDatePicker.setMaxDate(maxDate);
                mDatePicker.setMinDate(minDate);
            } catch (Exception e) {
                if (isBirthDay) {
                    //选择出生日期，最大选到默认时间
                    mDatePicker.setMaxDate(mDefaultDate.getTime());
                    mDatePicker.setMinDate(minDate);
                } else {
                    //护照有效期，最小选到默认时间
                    mDatePicker.setMaxDate(maxDate);
                    mDatePicker.setMinDate(mDefaultDate.getTime());
                }
            }

            findViewById(R.id.tv_cancel).setOnClickListener(view -> {
                dismiss();
            });

            findViewById(R.id.tv_ok).setOnClickListener(view -> {
                String dateStr = mDatePicker.getYear() + "-" + (mDatePicker.getMonth() + 1) + "-" +
                        mDatePicker.getDayOfMonth();
                try {
                    Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
                    okClickListener.onClick(this, date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                dismiss();
            });
        }
        return this;
    }

    @Override
    public void dismiss() {
        super.dismiss();
        backgroundAlpha(activity,1f);
    }

    @Override
    protected Animation initShowAnimation() {
        return getTranslateVerticalAnimation(1f, 0, 200);
    }

    @Override
    protected Animation initExitAnimation() {
        return getTranslateVerticalAnimation(0, 1f, 200);
    }

    @Override
    public View getClickToDismissView() {
        return getPopupWindowView();
    }

    @Override
    public View onCreatePopupView() {
        return createPopupById(R.layout.dialog_bottom_date);
    }

    @Override
    public View initAnimaView() {
        return findViewById(R.id.popup_content);
    }

    public interface OnOkClickListener {
        void onClick(AppBottomDateDialog dialog, Date date);
    }

    public void setCurrentData(int year, int month, int day) {
        mDatePicker.init(year, month, day, null);
    }

    private boolean isBirthDay = true;

    public void setTpye(boolean isBirthday) {
        this.isBirthDay = isBirthday;
    }
}
