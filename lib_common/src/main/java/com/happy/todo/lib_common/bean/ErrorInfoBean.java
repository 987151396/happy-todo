package com.happy.todo.lib_common.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @Describe：
 * @Date： 2019/03/04
 * @Author： dengkewu
 * @Contact：
 */
public class ErrorInfoBean {

    /**
     * msgtype : text
     * text : {"content":"我就是我, 是不一样的烟火@156xxxx8827"}
     * at : {"atMobiles":["156xxxx8827","189xxxx8325"],"isAtAll":false}
     */

    private String msgtype;
    private TextBean text;
    private AtBean at;
    /**
     * link : {"text":"这个即将发布的新版本，创始人陈航（花名\u201c无招\u201d）称它为\u201c红树林\u201d。 而在此之前，每当面临重大升级，产品经理们都会取一个应景的代号，这一次，为什么是\u201c红树林\u201d？","title":"时代的火车向前开","picUrl":"","messageUrl":"https://mp.weixin.qq.com/s?__biz=MzA4NjMwMTA2Ng==&mid=2650316842&idx=1&sn=60da3ea2b29f1dcc43a7c8e4a7c97a16&scene=2&srcid=09189AnRJEdIiWVaKltFzNTw&from=timeline&isappinstalled=0&key=&ascene=2&uin=&devicetype=android-23&version=26031933&nettype=WIFI"}
     */

    private LinkBean link;

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }

    public TextBean getText() {
        return text;
    }

    public void setText(TextBean text) {
        this.text = text;
    }

    public AtBean getAt() {
        return at;
    }

    public void setAt(AtBean at) {
        this.at = at;
    }

    public LinkBean getLink() {
        return link;
    }

    public void setLink(LinkBean link) {
        this.link = link;
    }

    public static class TextBean {
        /**
         * content : 我就是我, 是不一样的烟火@156xxxx8827
         */

        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public static class AtBean {
        /**
         * atMobiles : ["156xxxx8827","189xxxx8325"]
         * isAtAll : false
         */

        private boolean isAtAll;
        private List<String> atMobiles;

        public boolean isIsAtAll() {
            return isAtAll;
        }

        public void setIsAtAll(boolean isAtAll) {
            this.isAtAll = isAtAll;
        }

        public List<String> getAtMobiles() {
            return atMobiles;
        }

        public void setAtMobiles(List<String> atMobiles) {
            this.atMobiles = atMobiles;
        }
    }


    public static class LinkBean {
        /**
         * text : 这个即将发布的新版本，创始人陈航（花名“无招”）称它为“红树林”。 而在此之前，每当面临重大升级，产品经理们都会取一个应景的代号，这一次，为什么是“红树林”？
         * title : 时代的火车向前开
         * picUrl :
         * messageUrl : https://mp.weixin.qq.com/s?__biz=MzA4NjMwMTA2Ng==&mid=2650316842&idx=1&sn=60da3ea2b29f1dcc43a7c8e4a7c97a16&scene=2&srcid=09189AnRJEdIiWVaKltFzNTw&from=timeline&isappinstalled=0&key=&ascene=2&uin=&devicetype=android-23&version=26031933&nettype=WIFI
         */

        @SerializedName("text")
        private String textX;
        private String title;
        private String picUrl;
        private String messageUrl;

        public String getTextX() {
            return textX;
        }

        public void setTextX(String textX) {
            this.textX = textX;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getMessageUrl() {
            return messageUrl;
        }

        public void setMessageUrl(String messageUrl) {
            this.messageUrl = messageUrl;
        }
    }
}
