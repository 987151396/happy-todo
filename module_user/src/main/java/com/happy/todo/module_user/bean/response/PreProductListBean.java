package com.happy.todo.module_user.bean.response;

import java.util.List;

/**
 * Author by Ouyangle, Date on 2020-07-10.
 * PS: Not easy to write code, please indicate.
 */
public class PreProductListBean {

    /**
     * goods_name : 【Pre-sale goods】Luxury hotel + buffet
     * hotel_name : Anantara Bophut Koh Samui Resort
     * img : https://ac-r.static.booking.cn/images/hotel/max1024x768/658/65848362.jpg
     * label : ["Guests' favorite","Love Hotel"]
     */

    private String goods_name;
    private String goods_code;
    private String hotel_name;
    private String img;
    private String product_url;
    private List<String> label;

    public String getGoods_code() {
        return goods_code;
    }

    public void setGoods_code(String goods_code) {
        this.goods_code = goods_code;
    }

    public String getProduct_url() {
        return product_url;
    }

    public void setProduct_url(String product_url) {
        this.product_url = product_url;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getHotel_name() {
        return hotel_name;
    }

    public void setHotel_name(String hotel_name) {
        this.hotel_name = hotel_name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public List<String> getLabel() {
        return label;
    }

    public void setLabel(List<String> label) {
        this.label = label;
    }
}
