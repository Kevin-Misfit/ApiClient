package com.yao.app.api;

import java.io.Serializable;

/**
 * 概述: 接口URL实体类
 * 
 * @author yao
 * @version 1.0
 * @created 2014年11月8日
 */
public class URLs implements Serializable {

	private static final long serialVersionUID = -3371739479972951686L;

	// 公网/生产服务器
	private final static String SERVER_PRODUCT = "www.Servier.com";

	// 局域网服务器
	private final static String LOCAL_TEST_SERVER = "192.168.1.50";

	// 内测服务器
	private final static String INTERNEL_TEST_SERVER = "www.InternelServier.com";

	// 默认局域网服务器
	private static String HOST = LOCAL_TEST_SERVER;

	private static boolean isInternelTest = true;
	private static boolean isLocalServer = true;

	static {
		// 更改app当前状态所用的服务器， 分为本地调试、内测、公测， 默认为内测
		if (isInternelTest) {

			if (isLocalServer)
				URLs.HOST = URLs.LOCAL_TEST_SERVER; // 本地测试服务器
			else
				URLs.HOST = URLs.INTERNEL_TEST_SERVER; // 内测域名代理

		} else {
			URLs.HOST = URLs.SERVER_PRODUCT;
		}
	}

	private final static String HTTP = "http://";
	private final static String HTTPS = "https://";

	private final static String URL_SPLITTER = "/";

	public final static String PRODUCT_NAME = "myProduct";
	public final static String API = "api";
	public final static String PRODUCT_API_PATH = PRODUCT_NAME + URL_SPLITTER + API;

	private final static String URL_API_HTTP_HOST = HTTP + HOST + URL_SPLITTER + PRODUCT_API_PATH + URL_SPLITTER;
//	private final static String URL_API_HTTPS_HOST = HTTPS + HOST + URL_SPLITTER + PRODUCT_API_PATH + URL_SPLITTER;

	// 获取短信验证码
	public final static String SMS_VERIFY = URL_API_HTTP_HOST + "smsVerify";

	// 用户注册
	public final static String USER_REGISTER = URL_API_HTTP_HOST + "userRegister";

	// 登录
	public final static String USER_LOGIN = URL_API_HTTP_HOST + "userLogin";

	// 获取帐号下设备列表
	public final static String UserObjList = URL_API_HTTP_HOST + "userObjList";

	// 查询设备是否已绑定以及是否设置SIM号
	public final static String isIMEIExists = URL_API_HTTP_HOST + "IMEIExists";


}
