package com.happy.todo.calendarview;

import java.util.Date;

/**
 * Created by cxk on 2018/1/9.
 */

public class CustomCalendarCellDecorator implements CalendarCellDecorator {
    @Override
    public void decorate(CalendarCellView cellView, Date date) {
        RangeState rangeState = cellView.getRangeState();

        switch (rangeState) {
//            case NONE:
//                if (cellView.isSelected()) {
//                    cellView.setBackgroundResource(R.drawable.custom_calendar_bg_selector);
//                } else {
//                    cellView.setBackgroundResource(0);
//                }
//                break;
//
//            case FIRST:
//                cellView.setBackgroundResource(R.drawable.calendar_bg_circle_left);
//                break;
//
//            case MIDDLE:
//                cellView.setBackgroundResource(R.color.blue);
//                break;
//
//            case LAST:
//                cellView.setBackgroundResource(R.drawable.calendar_bg_circle_right);
//                break;
//
//            default:
//                cellView.setBackgroundResource(0);
//                break;
        }
    }
}
