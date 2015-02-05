package com.yao.app.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 概述: API客户端接口：用于访问网络数据 <br>
 * <p/>
 * TIPS 暂时以json为请求体的格式数据提交
 *
 * @author yao
 * @version 1.0
 * @created 2014年11月8日
 */
public class ApiClient {

    private static Listener<JSONObject> mListener;
    private static ErrorListener mErrorListener;
    private static onReqStartListener mOnStartListener;

    /**
     * 概述: 每个activity请求网络之前必须要先设置Listeners <br>
     * 用于每个请求成功或者失败的回调 <br>
     *
     * @param listener
     * @param errorListener
     * @auther yao
     */
    public static void setListeners(onReqStartListener onStartListener, Listener<JSONObject> listener, ErrorListener errorListener) {
        mOnStartListener = onStartListener;
        mListener = listener;
        mErrorListener = errorListener;
    }

    /**
     * 概述: get token
     *
     * @return
     * @auther yao
     */
    public static String getToken() {
        return RequestUtils.getToken();
    }

    /**
     * 概述: TIPS， 若token失效，需调用此方法，重新设置token
     *
     * @param newToken
     * @auther yao
     */
    public static void setToken(String newToken) {
        RequestUtils.setToken(newToken);
    }

    /**
     * 概述: 设置appkey
     *
     * @param context
     * @auther yao
     */
    public static void initAppKey(Context context) {
        RequestUtils.setAppKey(RequestUtils.getAppKey(context));
    }

    /**
     * 概述: GET请求
     *
     * @param reqParams 请求参数
     * @auther yao
     */
    private static void _GET(String url, Map<String, String> headerParams, Map<String, String> reqParams) {
        if (mListener != null && mErrorListener != null) {
            if (mOnStartListener != null)
                mOnStartListener.onReqStart(); // 请求开始的回调

            MyVolley.addToRequestQueue(RequestUtils.getJsonObjectRequest(Method.GET, url, headerParams, reqParams, null, mListener,
                    mErrorListener));
        }
    }

    /**
     * 概述: POST请求
     *
     * @param reqParams     POST请求参数/Form参数
     * @param reqBodyParams POST请求(Body) 暂时设置为：Content-Type=application/json
     * @auther yao
     */
    private static void _POST(String url, Map<String, String> headerParams, Map<String, String> reqParams, Map<String, Object> reqBodyParams) {
        if (mListener != null && mErrorListener != null) {
            if (mOnStartListener != null)
                mOnStartListener.onReqStart(); // 请求开始的回调

            JSONObject reqJsonObject = null;
            if (reqBodyParams != null)
                reqJsonObject = new JSONObject(reqBodyParams);

            MyVolley.addToRequestQueue(RequestUtils.getJsonObjectRequest(Method.POST, url, headerParams, reqParams,
                    reqJsonObject == null ? null : reqJsonObject, mListener, mErrorListener));
        }
    }

    /**
     * 概述: POST请求
     *
     * @param reqParams     POST请求参数/Form参数
     * @param reqBodyParams POST请求(Body) 暂时设置为：Content-Type=application/json
     * @auther yao
     */
    private static void _POST_AftarOnReqStart(String url, Map<String, String> headerParams, Map<String, String> reqParams,
                                              Map<String, Object> reqBodyParams) {
        if (mListener != null && mErrorListener != null) {

            JSONObject reqJsonObject = new JSONObject(reqBodyParams);

            MyVolley.addToRequestQueue(RequestUtils.getJsonObjectRequest(Method.POST, url, headerParams, reqParams, reqJsonObject,
                    mListener, mErrorListener));
        }
    }

    /**
     * 概述: POST请求
     *
     * @param reqParams     POST请求参数/Form参数
     * @param reqBodyParams POST请求(Body) 暂时设置为：Content-Type=application/json
     * @auther yao
     */
    private static void _POST_WITH_LISTENERS(String url, Map<String, String> headerParams, Map<String, String> reqParams,
                                             Map<String, Object> reqBodyParams, onReqStartListener reqStartListener, Listener<JSONObject> responseListener, ErrorListener errorListener) {
        if (responseListener != null && errorListener != null) {
            if (reqStartListener != null)
                reqStartListener.onReqStart(); // 请求开始的回调

            JSONObject reqJsonObject = null;

            if (reqBodyParams != null)
                reqJsonObject = new JSONObject(reqBodyParams);

            MyVolley.addToRequestQueue(RequestUtils.getJsonObjectRequest(Method.POST, url, headerParams, reqParams,
                    reqJsonObject == null ? null : reqJsonObject, responseListener, errorListener));
        }
    }

    /**
     * 概述: 请求短信验证码
     *
     * @param appKey
     * @param phoneNum 手机号
     * @auther yao
     */
    public static void reqVerifyCode(String appKey, String phoneNum) {
        Map<String, String> reqParams = new HashMap<String, String>();
        reqParams.put("appkey", appKey);
        reqParams.put("sim", phoneNum);

        _GET(URLs.SMS_VERIFY, null, reqParams);
    }

    /**
     * 概述: 用户注册
     *
     * @param phoneNum   手机号
     * @param verifyCode 验证码
     * @param encodedPwd 密码/密文
     * @auther yao
     */
    public static void userRegister(String phoneNum, String verifyCode, String encodedPwd) {
        Map<String, Object> reqBodyParams = new HashMap<String, Object>();
        reqBodyParams.put("mobile", phoneNum);
        reqBodyParams.put("verifyCode", verifyCode);
        reqBodyParams.put("pwd", encodedPwd);

        _POST(URLs.USER_REGISTER, null, null, reqBodyParams);
    }

    /**
     * 概述: 登录
     *
     * @param telNum     电话号码
     * @param encodedPwd 密码/密文
     * @auther yao
     */
    public static void login(String telNum, String encodedPwd) {
        Map<String, Object> reqBodyParams = new HashMap<String, Object>();
        reqBodyParams.put("mobile", telNum);
        reqBodyParams.put("pwd", encodedPwd);

        _POST(URLs.USER_LOGIN, null, null, reqBodyParams);
    }

    /**
     * 概述: 登录接口，内部使用，当token失效是重新用来重新获取token
     *
     * @param telNum
     * @param encodedPwd
     * @param responseListener
     * @param errorListener
     * @auther yao
     */
    public static void loginInternel(String telNum, String encodedPwd, onReqStartListener reqStartListener, Listener<JSONObject> responseListener, ErrorListener errorListener) {
        Map<String, Object> reqBodyParams = new HashMap<String, Object>();
        reqBodyParams.put("mobile", telNum);
        reqBodyParams.put("pwd", encodedPwd);

        _POST_WITH_LISTENERS(URLs.USER_LOGIN, null, null, reqBodyParams, reqStartListener, responseListener, errorListener);
    }

    /**
     * 概述: 获取帐号下设备列表
     *
     * @param userId uid
     * @auther yao
     */
    public static void getUserObjList(int userId) {

        Map<String, String> reqParams = new HashMap<String, String>();
        reqParams.put("uid", String.valueOf(userId));

        Log.d("uid", String.valueOf(userId));

        _GET(URLs.UserObjList, null, reqParams);
    }

    public static void isIMEIExists(String imei) {
        Map<String, String> reqParams = new HashMap<String, String>();
        reqParams.put("imei", imei);

        _GET(URLs.isIMEIExists, null, reqParams);

    }

    /**
     * 概述: 请求开始的回调监听器
     *
     * @author yao
     * @version 1.0
     * @created 2014年11月8日
     */
    public interface onReqStartListener {
        /**
         * 概述: 请求开始的回调方法
         *
         * @auther yao
         */
        public void onReqStart();
    }

}
