package com.happy.todo.module_user.bean.response;

/**
 * @功能描述：
 * @创建日期： 2018/07/17
 * @作者： dengkewu
 */

public class TestBean {

    /**
     * city_code : 3CHA
     * hotel_code : BAA1
     * room_type : 豪华别墅
     * star : 4
     * dur : 1
     * start_time : 1532275200
     * end_time : 1532361600
     * currency : USD
     * order_sn : HOT201807161741782101
     * itself : {"coupon_price":"0.00","currency":"USD","all_price":"9.00","pay_all_price":"9.00"}
     * conversion : {"coupon_price":"0.00","currency":"USD","tranlate_rate":"1.000","pay_tran_all_price":"9.00","all_tran_price":"9.00"}
     * hotel_name : Baan Haad Ngam Boutique Resort
     * cancel_condtion : {"Condition":[{"@value":"","@attributes":{"Charge":"true","ChargeAmount":"7.50","Currency":"USD","FromDate":"2018-07-23","ToDate":"2018-07-08"}},{"@value":"","@attributes":{"Charge":"false","FromDate":"2018-07-07"}}],"@attributes":{"Type":"cancellation"}}
     * cancel_time : null
     */

    private String city_code;
    private String hotel_code;
    private String room_type;
    private String star;
    private int dur;
    private String start_time;
    private String end_time;
    private String currency;
    private String order_sn;
    private ItselfBean itself;
    private ConversionBean conversion;
    private String hotel_name;
//    private CancelCondtionBean cancel_condtion;
    private Object cancel_time;

    public static class ItselfBean {
        /**
         * coupon_price : 0.00
         * currency : USD
         * all_price : 9.00
         * pay_all_price : 9.00
         */

        private String coupon_price;
        private String currency;
        private String all_price;
        private String pay_all_price;

        public String getCoupon_price() {
            return coupon_price;
        }

        public void setCoupon_price(String coupon_price) {
            this.coupon_price = coupon_price;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getAll_price() {
            return all_price;
        }

        public void setAll_price(String all_price) {
            this.all_price = all_price;
        }

        public String getPay_all_price() {
            return pay_all_price;
        }

        public void setPay_all_price(String pay_all_price) {
            this.pay_all_price = pay_all_price;
        }
    }

    public static class ConversionBean {
        /**
         * coupon_price : 0.00
         * currency : USD
         * tranlate_rate : 1.000
         * pay_tran_all_price : 9.00
         * all_tran_price : 9.00
         */

        private String coupon_price;
        private String currency;
        private String tranlate_rate;
        private String pay_tran_all_price;
        private String all_tran_price;

        public String getCoupon_price() {
            return coupon_price;
        }

        public void setCoupon_price(String coupon_price) {
            this.coupon_price = coupon_price;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getTranlate_rate() {
            return tranlate_rate;
        }

        public void setTranlate_rate(String tranlate_rate) {
            this.tranlate_rate = tranlate_rate;
        }

        public String getPay_tran_all_price() {
            return pay_tran_all_price;
        }

        public void setPay_tran_all_price(String pay_tran_all_price) {
            this.pay_tran_all_price = pay_tran_all_price;
        }

        public String getAll_tran_price() {
            return all_tran_price;
        }

        public void setAll_tran_price(String all_tran_price) {
            this.all_tran_price = all_tran_price;
        }
    }
}
