package com.roydon.community.api;

public class ApiConfig {

    public static final int PAGE_SIZE = 10;
    public static final int PAGE_SIZE_20 = 20;
    public static final String BASE_URl = "http://106.14.105.101:8088";
//    public static final String BASE_URl = "http://192.168.0.101:8088";

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
    public static final String NEWS_DETAIL = "/app/news/detail"; //新闻detail

    /**
     * 轮播公告
     */
    public static final String BANNER_NOTICE_LIST = "/app/notice/list"; //新闻分类

    /**
     * mall-goods
     */
    public static final String MALL_GOODS_LIST = "/app/mallGoods/list"; //商品集合
    public static final String MALL_GOODS_DETAIL = "/app/mallGoods"; //商品集合

    /**
     * mall-cart
     */
    public static final String MALL_CART_LIST = "/app/mallUserCart/list"; //购物车集合
    public static final String MALL_ADD_CART = "/app/mallUserCart"; //添加购物车
    public static final String MALL_DEL_CART = "/app/mallUserCart/"; //添加购物车
    public static final String MALL_ALL_CART = "/app/mallUserCart/all"; //添加购物车

    /**
     * mall-address
     */
    public static final String MALL_ADDRESS_LIST = "/app/mallUserAddress/list"; //购物车集合
    public static final String MALL_ADD_ADDRESS = "/app/mallUserAddress"; //添加购物车
    public static final String MALL_DEFAULT_ADDRESS = "/app/mallUserAddress/default"; //默认收货地址

    /**
     * mall-order
     */
    public static final String MALL_ORDER_CREATE = "/app/mallOrder/create";
}