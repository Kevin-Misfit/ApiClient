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
	private final static String SERVER_PRODUCT = "app001.u12580.com";

	// 局域网服务器
	private final static String LOCAL_TEST_SERVER = "192.168.1.50";

	// 内测服务器
	private final static String INTERNEL_TEST_SERVER = "mapgoo1307.eicp.net:6789";

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

	public final static String PRODUCT_NAME = "life365";
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
	
	// 设置佩带者资料
	public final static String ObjectBasic = URL_API_HTTP_HOST + "ObjectBasic";
	
	// 新增/修改 静音时段 
	public final static String UpdateObjectMuteTime = URL_API_HTTP_HOST + "UpdateObjectMuteTime";
	
	// 删除-静音时段条目
	public final static String DelObjectMuteTime = URL_API_HTTP_HOST + "DelObjectMuteTime";
	
	// 设置佩带者关系
	public final static String UserObjRelation = URL_API_HTTP_HOST + "UserObjRelation";
	
	// 获取设备家庭成员信息-GET / 移除家庭成员-POST
	public final static String ObjectMember = URL_API_HTTP_HOST + "ObjectMember";
	
	// 设置用户SOS等级
	public final static String UpdateUserObjSOS = URL_API_HTTP_HOST + "UpdateUserObjSOS";
	
	// 设置用户白名单权限
	public final static String UpdateUserObjWhiteList = URL_API_HTTP_HOST + "UpdateUserObjWhiteList";
	
	// 管理员邀请他人关注设备
	public final static String InviteUserRelation = URL_API_HTTP_HOST + "InviteUserRelation";
	
}
