package com.happy.todo.module_user.bean.response;

/**
 * @Describe：
 * @Date： 2018/12/15
 * @Author： dengkewu
 * @Contact：
 */
public class RedeemBean {

    /**
     * Number : 0
     * FlowNumber : 0
     * IsBound : 1
     */

    private String Number;
    private String FlowNumber;
    private String IsBound;

    public String getNumber() {
        return Number;
    }

    public void setNumber(String Number) {
        this.Number = Number;
    }

    public String getFlowNumber() {
        return FlowNumber;
    }

    public void setFlowNumber(String FlowNumber) {
        this.FlowNumber = FlowNumber;
    }

    public String getIsBound() {
        return IsBound;
    }

    public void setIsBound(String IsBound) {
        this.IsBound = IsBound;
    }
}
