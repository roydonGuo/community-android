package com.roydon.community.api;

public class ApiConfig {

    public static final int PAGE_SIZE = 10;
        public static final String BASE_URl = "http://106.14.105.101:8088";
//    public static final String BASE_URl = "http://192.168.1.25:8088";

    /**
     * app前缀接口
     */
    public static final String LOGIN = "/app/login"; //登录
    public static final String REGISTER = "/app/register"; //注册

    /**
     * 新闻模块接口
     */
    public static final String NEWS_CATEGORY_LIST = "/app/news/category"; //新闻分类
    public static final String NEWS_LIST = "/app/news/list"; //新闻list

}