package com.happy.todo.lib_common.bean;

import android.text.TextUtils;

import java.util.Locale;

/**
 * 登录/注册/修改个人信息，最终都会返回这个实体对应的json字符串
 * Created by Jaminchanks on 2018/1/23.
 */

public class  UserInfoResult {
    /**
     * user_id : 20
     * account : 12121@qq.com
     * token : 9a2eb00e19ae1fad7ba130ca80ec6168
     * user_info : {"user_id":20,"email":"12121@qq.com","mobile":null,"area_code":null,"reg_time":"1517820046","nickname":null,"first_name":"张","last_name":"大炮","user_token":null,  "unionid": "ebbly_8975fb980a0484561b99e8e625a7d37d", "extend":{"sex":1,"birthday":null,"country":null,"avatar":null,"avatar_thumb":null},"percent":"42%","open":[{"open_name":"微信","open_type_id":2,"openid":null},{"open_name":"facebook","open_type_id":3,"openid":null},{"open_name":"ifree","open_type_id":4,"openid":null}]}
     */

    private int user_id;
    private String account;
    private String password;
    private String token;
    private UserInfoBean user_info;
    private EmergencyBean emergency;

    public EmergencyBean getEmergency() {
        return emergency;
    }

    public void setEmergency(EmergencyBean emergency) {
        this.emergency = emergency;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserInfoBean getUser_info() {
        return user_info;
    }

    public void setUser_info(UserInfoBean user_info) {
        this.user_info = user_info;
    }

    public static class EmergencyBean {

        /**
         * id : 3
         * account_id : 4
         * sex : 1
         * first_name : first
         * last_name : last
         * area_code : 86
         * mobile : 1111111111
         * email : testemail@qq.com
         * emergency_id : 3
         */

        private int id; //不会用到
        private int account_id; //不会用到
        private int sex;
        private String first_name;
        private String last_name;
        private String area_code;
        private String mobile;
        private String email;
        private String emergency_id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getAccount_id() {
            return account_id;
        }

        public void setAccount_id(int account_id) {
            this.account_id = account_id;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
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

        public String getEmergency_id() {
            return emergency_id;
        }

        public void setEmergency_id(String emergency_id) {
            this.emergency_id = emergency_id;
        }
    }
    public static class UserInfoBean {
        /**
         * user_id : 20
         * email : 12121@qq.com
         * mobile : null
         * area_code : null
         * reg_time : 1517820046
         * nickname : null
         * first_name : 张
         * last_name : 大炮
         * user_token : null
         * "unionid": "ebbly_8975fb980a0484561b99e8e625a7d37d",
         * extend : {"sex":1,"birthday":null,"country":null,"avatar":null,"avatar_thumb":null}
         * percent : 42%
         * open : [{"open_name":"微信","open_type_id":2,"openid":null},{"open_name":"facebook","open_type_id":3,"openid":null},{"open_name":"ifree","open_type_id":4,"openid":null}]
         */

        private int user_id;
        private String email;
        private String mobile;
        private String area_code;
        private String reg_time;
        private String nickname;
        private String first_name;
        private String last_name;
        private String user_token;
        private String unionid;
        private ExtendBean extend;
        private String percent;
        private String invitation_code;

        public String getInvitation_code() {
            return invitation_code;
        }

        public void setInvitation_code(String invitation_code) {
            this.invitation_code = invitation_code;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getArea_code() {
            return area_code;
        }

        public void setArea_code(String area_code) {
            this.area_code = area_code;
        }

        public String getReg_time() {
            return reg_time;
        }

        public void setReg_time(String reg_time) {
            this.reg_time = reg_time;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
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

        public String getUser_token() {
            return user_token;
        }

        public void setUser_token(String user_token) {
            this.user_token = user_token;
        }

        public String getUnionid() {
            return unionid;
        }

        public void setUnionid(String unionid) {
            this.unionid = unionid;
        }

        public ExtendBean getExtend() {
            return extend;
        }

        public void setExtend(ExtendBean extend) {
            this.extend = extend;
        }

        public String getPercent() {
            return percent;
        }

        public void setPercent(String percent) {
            this.percent = percent;
        }

        public static class ExtendBean {
            /**
             * sex : 1
             * birthday : null
             * country : null
             * avatar : null
             * avatar_thumb : null
             */

            private int sex;
            private String birthday;
            private String country;
            private String country_id;
            private String avatar;
            private String avatar_thumb;
            private String country_short_name;

            public String getCountry_short_name() {
                if(!TextUtils.isEmpty(country_short_name)) country_short_name.toUpperCase(Locale.US);
                return country_short_name;
            }

            public void setCountry_short_name(String country_short_name) {
                this.country_short_name = country_short_name;
            }

            public String getCountry_id() {
                return country_id;
            }

            public void setCountry_id(String country_id) {
                this.country_id = country_id;
            }

            public int getSex() {
                return sex;
            }

            public void setSex(int sex) {
                this.sex = sex;
            }

            public String getBirthday() {
                return birthday;
            }

            public void setBirthday(String birthday) {
                this.birthday = birthday;
            }

            public String getCountry() {
                return country;
            }

            public void setCountry(String country) {
                this.country = country;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getAvatar_thumb() {
                return avatar_thumb;
            }

            public void setAvatar_thumb(String avatar_thumb) {
                this.avatar_thumb = avatar_thumb;
            }
        }
    }

}
