package com.example.bsproperty.net;

/**
 * Created by yezi on 2018/1/27.
 */

public class ApiManager {

    private static final String HTTP = "http://";
    private static final String IP = "192.168.55.103";
    private static final String PROT = ":8080";
    private static final String HOST = HTTP + IP + PROT;
    private static final String API = "/api";
    private static final String NOTICE = "/notice";
    private static final String USER = "/user";


    public static final String HOME_LIST = HOST + API + NOTICE + "/";
    public static final String REGISTER = HOST + API + USER + "/";
    public static final String LOGIN = HOST + API + USER + "/login";

}
