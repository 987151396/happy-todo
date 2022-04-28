package com.happy.todo.lib_common.http.api;

import com.xiaomai.environmentswitcher.annotation.Environment;
import com.xiaomai.environmentswitcher.annotation.Module;

public class EnvironmentConfig {
    /**
     * The entire app environment
     */
    @Module
    private class App {
        @Environment(url = "https://v1.ebbly-api.cn/", isRelease = true)
        private String online;

        @Environment(url = "http://apiebbly.ifreegroup.net:8000/")
        private String test;

        @Environment(url = "http://ebbly-v1.uat.ifreegroup.net:8888/")
        private String uatTest;

        @Environment(url = "https://pre.ebbly-api.cn")
        private String releaseTest;
    }


    @Module
    private class RecommendedHotel {
        @Environment(url = "https://v2.ebbly-api.cn/", alias = "推荐酒店正式", isRelease = true)
        private String online;

        @Environment(url = "http://v2-ebbly.ifreegroup.net:8013", alias = "推荐酒店测试")
        private String test;
    }

    @Module
    private class JS_URL {
        @Environment(url = "https://m.ebbly.com/", alias = "正式", isRelease = true)
        private String online;

        @Environment(url = "http://webapp.ebbly.ifreegroup.net:8000/", alias = "测试")
        private String test;
    }
}
