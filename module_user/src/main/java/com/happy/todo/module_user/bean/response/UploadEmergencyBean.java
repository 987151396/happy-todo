package com.happy.todo.module_user.bean.response;

/**
 * @Describe：
 * @Date： 2019/01/02
 * @Author： dengkewu
 * @Contact：
 */
public class UploadEmergencyBean {
    private String emergency_id;
    private String sex;
    private String first_name;
    private String last_name;
    private String area_code;
    private String mobile;
    private String email;

    public String getEmergency_id() {
        return emergency_id;
    }

    public void setEmergency_id(String emergency_id) {
        this.emergency_id = emergency_id;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getArea_code() {
        return area_code;
    }

    public void setArea_code(String area_code) {
        this.area_code = area_code;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
