package com.happy.todo.lib_common.event;

public class EventMessager {

    public final String POSTREVIEWSUCCESS_TO_ORDERLIST = "postReviewSuccess_to_OrderList";

    private int messagerID;

    private String messagesStr;

    private Object contentMsg;

    public int getMessagerID() {
        return messagerID;
    }

    public void setMessagerID(int messagerID) {
        this.messagerID = messagerID;
    }

    public String getMessagesStr() {
        return messagesStr;
    }

    public void setMessagesStr(String messagesStr) {
        this.messagesStr = messagesStr;
    }

    public Object getContentMsg() {
        return contentMsg;
    }

    public void setContentMsg(Object contentMsg) {
        this.contentMsg = contentMsg;
    }
}
