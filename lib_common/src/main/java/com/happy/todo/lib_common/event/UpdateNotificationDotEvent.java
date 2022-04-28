package com.happy.todo.lib_common.event;

/**
 * @author Vegen
 * @creation_time 2018/10/25
 * @description
 */

public class UpdateNotificationDotEvent {
   private int dotNum;

    public UpdateNotificationDotEvent(int dotNum) {
        this.dotNum = dotNum;
    }

    public int getDotNum() {
        return dotNum;
    }

    public void setDotNum(int dotNum) {
        this.dotNum = dotNum;
    }
}
