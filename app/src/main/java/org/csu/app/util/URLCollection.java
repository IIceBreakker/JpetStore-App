package org.csu.app.util;


import java.net.HttpURLConnection;

/**
 * This class is used to manage all http urls;
 * @author IIceBreakker 575987220@qq.com
 */

public class URLCollection {

    public static final String LOGIN_URL = "http://10.0.2.2:3000/login";
    public static final String REGISTER_URL = "http://10.0.2.2:3000/register";
    public static final String CATEGORY_URL = "http://10.0.2.2:3000/category";
    public static final String PRODUCTS_URL = "http://10.0.2.2:3000/products?catId=";
    public static final String ITEMS_URL = "http://10.0.2.2:3000/items?productId=";
    public static final String CART_URL = "http://10.0.2.2:3000/order?userId=";
    public static final String ADD_ORDER_URL = "http://10.0.2.2:3000/addOrder";

}
