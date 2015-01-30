package com.mapgoo.snowleopard.engineer.api;

import android.annotation.SuppressLint;
import android.content.Context;

import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 概述: API客户端接口：用于访问网络数据 <br>
 * <p/>
 * TIPS 暂时以JSON为请求体的格式数据提交
 *
 * @author yao
 * @version 1.0
 * @created 2014年11月8日
 */
@SuppressLint("SimpleDateFormat")
public class ApiClient {

    private static Listener<JSONObject> mListener;
    private static ErrorListener mErrorListener;
    private static onReqStartListener mOnStartListener; // 网络请求开始的回调

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
     * 概述: TIPS， 若token失效，需调用此方法，重新设置token
     *
     * @param newToken
     * @auther yao
     */
    public static void setToken(String newToken) {
        RequestUtils.setToken(newToken);
    }

    public static void initAppKey(Context context) {
        RequestUtils.setAppKey(RequestUtils.getAppKey(context));
    }

    /**
     * 概述：将键值对转换为XML
     *
     * @param params
     * @return
     * @author yqw
     * @since 2014年4月17日
     */
    private static String map2XML(HashMap<String, Object> params) {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");

        sb.append("<xml>");
        Set<String> keys = params.keySet();
        for (String key : keys) {
            sb.append("<" + key + ">" + params.get(key) + "</" + key + ">");
        }
        sb.append("</xml>");

        return sb.toString();
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

    private static void _GET_WITH_LISTENERS(String url, Map<String, String> headerParams, Map<String, String> reqParams,
                                            onReqStartListener reqStartListener, Listener<JSONObject> responseListener, ErrorListener errorListener) {

        if (responseListener != null && errorListener != null) {
            if (reqStartListener != null) // 这个包容性太棒了，不用监听的话，我可以直接传null了
                reqStartListener.onReqStart(); // 请求开始的回调

            MyVolley.addToRequestQueue(RequestUtils.getJsonObjectRequest(Method.GET, url, headerParams, reqParams, null, responseListener,
                    errorListener));
        }
    }

    /**
     * 概述: 字符串GET请求
     *
     * @param url
     * @param headerParams
     * @param reqParams
     * @param reqStartListener
     * @param responseListener
     * @param errorListener
     * @auther yao
     */
    private static void _GET_WITH_LISTENERS_REQ_STRING(String url, Map<String, String> headerParams, Map<String, String> reqParams,
                                                       onReqStartListener reqStartListener, Listener<String> responseListener, ErrorListener errorListener) {

        if (responseListener != null && errorListener != null) {
            if (reqStartListener != null) // 这个包容性太棒了，不用监听的话，我可以直接传null了
                reqStartListener.onReqStart(); // 请求开始的回调

            MyVolley.addToRequestQueue(RequestUtils.getStringRequest(Method.GET, url, headerParams, reqParams, responseListener,
                    errorListener));
        }
    }

    /**
     * 概述: POST请求
     *
     * @param reqParams     POST请求参数/Form参数
     * @param reqJsonObject POST请求(Body) 暂时设置为：Content-Type=application/json
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
     * @param reqJsonObject POST请求(Body) 暂时设置为：Content-Type=application/json
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
     * @param reqJsonObject POST请求(Body) 暂时设置为：Content-Type=application/json
     * @auther yao
     */
    private static void _POST_WITH_LISTENERS(String url, Map<String, String> headerParams, Map<String, String> reqParams,
                                             Map<String, Object> reqBodyParams, onReqStartListener reqStartListener, Listener<JSONObject> responseListener,
                                             ErrorListener errorListener) {
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
     * 概述：打断当前的网络请求队列
     */
    public static void cancelCurReqQueue(Context context) {
        MyVolley.cancelCurReqQueue(context);
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
     * 概述: 登录
     *
     * @param telNum     电话号码
     * @param encodedPwd 密码/密文
     * @auther yao
     */
    public static void login(String telNum, String encodedPwd, String devicetoken, String packname, String version, String platform) {
        Map<String, Object> reqBodyParams = new HashMap<String, Object>();
        reqBodyParams.put("mobile", telNum);
        reqBodyParams.put("pwd", encodedPwd);
        reqBodyParams.put("devicetoken", devicetoken);
        reqBodyParams.put("packname", packname);
        reqBodyParams.put("version", version);
        reqBodyParams.put("platform", platform);

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
    public static void loginInternel(String telNum, String encodedPwd, onReqStartListener reqStartListener,
                                     Listener<JSONObject> responseListener, ErrorListener errorListener) {
        Map<String, Object> reqBodyParams = new HashMap<String, Object>();
        reqBodyParams.put("mobile", telNum);
        reqBodyParams.put("pwd", encodedPwd);

        _POST_WITH_LISTENERS(URLs.USER_LOGIN, null, null, reqBodyParams, reqStartListener, responseListener, errorListener);
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
    public static void loginInternel(String telNum, String encodedPwd, String devicetoken, String packname, String version,
                                     String platform, onReqStartListener reqStartListener, Listener<JSONObject> responseListener, ErrorListener errorListener) {
        Map<String, Object> reqBodyParams = new HashMap<String, Object>();
        reqBodyParams.put("mobile", telNum);
        reqBodyParams.put("pwd", encodedPwd);
        reqBodyParams.put("devicetoken", devicetoken);
        reqBodyParams.put("packname", packname);
        reqBodyParams.put("version", version);
        reqBodyParams.put("platform", platform);

        _POST_WITH_LISTENERS(URLs.USER_LOGIN, null, null, reqBodyParams, reqStartListener, responseListener, errorListener);
    }

    /**
     *  TIPS: 查询设备状态 - 工程版 多一个识别参数：model 1-工程模式(工程APP专用)
     * @param imei
     */
    public static void validateDevice(String imei) {

        Map<String, String> reqParams = new HashMap<String, String>();
        reqParams.put("imei", imei);
        reqParams.put("model", 1 + ""); // TIPS: 查询设备状态 - 工程版 多一个识别参数：model 1-工程模式(工程APP专用)

        _GET(URLs.validateDevice, null, reqParams);
    }

    /**
     * 概述: 根据经纬度获取天气
     *
     * @param longitude
     * @param latitude
     * @param future    未来几天
     * @auther yao
     */
    public static void getWeatherInfo(double longitude, double latitude, int future, onReqStartListener reqStartListener,
                                      Listener<JSONObject> responseListener, ErrorListener errorListener) {

        Map<String, String> reqParams = new HashMap<String, String>();
        reqParams.put("lon", longitude + "");
        reqParams.put("lat", latitude + "");
        reqParams.put("future", future + "");

        _GET_WITH_LISTENERS(URLs.WEATHER, null, reqParams, reqStartListener, responseListener, errorListener);
    }

    /**
     * 概述: 获取用户资料
     *
     * @param uid
     * @param reqStartListener
     * @param responseListener
     * @param errorListener
     * @auther yao
     */
    public static void getUserInfo(int uid, onReqStartListener reqStartListener, Listener<JSONObject> responseListener,
                                   ErrorListener errorListener) {
        Map<String, String> reqParams = new HashMap<String, String>();
        reqParams.put("uid", uid + "");

        _GET_WITH_LISTENERS(URLs.USERINFO, null, reqParams, reqStartListener, responseListener, errorListener);
    }

    /**
     * 概述: 获取单台车资料
     *
     * @param uid
     * @param objectId
     * @param imei
     * @param reqStartListener
     * @param responseListener
     * @param errorListener
     * @auther yao
     */
    public static void getCarInfo(int uid, int objectId, String imei, onReqStartListener reqStartListener,
                                  Listener<JSONObject> responseListener, ErrorListener errorListener) {
        Map<String, String> reqParams = new HashMap<String, String>();
        reqParams.put("uid", uid + "");
        reqParams.put("objectid", objectId + "");
        reqParams.put("imei", imei);

        _GET_WITH_LISTENERS(URLs.VEHICLEINFO, null, reqParams, reqStartListener, responseListener, errorListener);
    }

    /**
     * 概述: 地址解析
     *
     * @param lat
     * @param lng
     * @param reqStartListener
     * @param responseListener
     * @param errorListener
     * @auther yao
     */
    public static void getGetGeoAdrress(double lat, double lng, onReqStartListener reqStartListener, Listener<String> responseListener,
                                        ErrorListener errorListener) {
        Map<String, String> reqParams = new HashMap<String, String>();
        reqParams.put("pos", lat + "," + lng);

        _GET_WITH_LISTENERS_REQ_STRING(URLs.ReverseGeo, null, reqParams, reqStartListener, responseListener, errorListener);
    }

    /**
     * 概述: 激活设备
     *
     * @param uid
     * @param imei
     * @param reqStartListener
     * @param responseListener
     * @param errorListener
     * @auther yao
     */
    public static void activateDevice(int uid, String imei, double lat, double lng, onReqStartListener reqStartListener, Listener<JSONObject> responseListener,
                                      ErrorListener errorListener) {
        Map<String, String> reqParams = new HashMap<String, String>();
        reqParams.put("uid", uid + "");
        reqParams.put("imei", imei);
        reqParams.put("lat", lat + "");
        reqParams.put("lon", lng + "");

        _GET_WITH_LISTENERS(URLs.DEVICE_ACTIVE, null, reqParams, reqStartListener, responseListener, errorListener);
    }

    /**
     * 概述: 激活设备
     *
     * @param uid
     * @param imei
     * @param reqStartListener
     * @param responseListener
     * @param errorListener
     * @auther yao
     */
    public static void getCarUserList(int objectId, onReqStartListener reqStartListener, Listener<JSONObject> responseListener,
                                      ErrorListener errorListener) {
        Map<String, String> reqParams = new HashMap<String, String>();
        reqParams.put("objectid", objectId + "");

        _GET_WITH_LISTENERS(URLs.USEROBJECT, null, reqParams, reqStartListener, responseListener, errorListener);
    }

    /**
     * 概述: 开始测试
     *
     * @param mobile
     * @param objectId
     * @param optype
     * @param reqStartListener
     * @param responseListener
     * @param errorListener
     * @auther yao
     */
    public static void startDeviceTest(int uid, String userMobile, int objectId, String imei, onReqStartListener reqStartListener,
                                       Listener<JSONObject> responseListener, ErrorListener errorListener) {

        Map<String, Object> reqBodyParams = new HashMap<String, Object>();
        reqBodyParams.put("uid", uid);
        reqBodyParams.put("mobile", userMobile);
        reqBodyParams.put("objectid", objectId);
        reqBodyParams.put("imei", imei);

        _POST_WITH_LISTENERS(URLs.DeviceTest, null, null, reqBodyParams, reqStartListener, responseListener, errorListener);
    }

    /**
     * 概述: 设置新密码
     *
     * @param userId
     * @param mobile
     * @param verifyCode
     * @param newEncodedPwd
     * @param oldEncodedPwd
     * @param reqStartListener
     * @param responseListener
     * @param errorListener
     * @auther yao
     */
    public static void setNewPwd(int userId, String mobile, String verifyCode, String oldEncodedPwd, String newEncodedPwd, onReqStartListener reqStartListener,
                                 Listener<JSONObject> responseListener, ErrorListener errorListener) {

        HashMap<String, Object> reqBodyParams = new HashMap<String, Object>();
        reqBodyParams.put("uid", userId);
        reqBodyParams.put("mobile", mobile);
        reqBodyParams.put("verifycode", verifyCode);
        reqBodyParams.put("password", newEncodedPwd);
        reqBodyParams.put("oldpassword", oldEncodedPwd);

        _POST_WITH_LISTENERS(URLs.User_Set_Password, null, null, reqBodyParams, reqStartListener, responseListener, errorListener);
    }


    /**
     * 概述: 获取工程测试记录
     *
     * @param userId
     * @param reqStartListener
     * @param responseListener
     * @param errorListener
     */
    public static void getTestRecords(int userId, onReqStartListener reqStartListener, Listener<JSONObject> responseListener,
                                      ErrorListener errorListener) {
        Map<String, String> reqParams = new HashMap<String, String>();
        reqParams.put("uid", userId + "");

        _GET_WITH_LISTENERS(URLs.DeviceTest, null, reqParams, reqStartListener, responseListener, errorListener);
    }


    /**
     * 概述: 下发指令
     *
     * @auther yao
     * @param userId
     * @param objectId
     *            设备id
     * @param mNetCmdVal
     * @param transtype
     *            0-GPRS 、1-语音 、2-SMS
     */
    public static void sendNetCMD(int userId, int objectId, String mNetCmdVal, String cmdSeq, int transtype, onReqStartListener reqStartListener,
                                  Listener<JSONObject> responseListener, ErrorListener errorListener) {

        Map<String, Object> reqBodyParams = new HashMap<String, Object>();
        reqBodyParams.put("uid", userId);
        reqBodyParams.put("objectid", objectId);
        reqBodyParams.put("cmd", mNetCmdVal);

        reqBodyParams.put("cmdseq", cmdSeq);

        reqBodyParams.put("transtype", transtype);

        _POST_WITH_LISTENERS(URLs.SENDCMD, null, null, reqBodyParams, reqStartListener, responseListener, errorListener);
    }

    /**
     * 概述：查询命令下发的状态
     *
     * @param userId
     * @param objectId
     * @param cmdSeq
     * @param reqStartListener
     * @param responseListener
     * @param errorListener
     */
    public static void queryCMDStatus(int userId, int objectId, String cmdSeq, onReqStartListener reqStartListener, Listener<JSONObject> responseListener,
                                      ErrorListener errorListener) {
        Map<String, String> reqParams = new HashMap<String, String>();
        reqParams.put("uid", userId + "");
        reqParams.put("objectid", objectId + "");
        reqParams.put("cmdseq", cmdSeq);

        _GET_WITH_LISTENERS(URLs.SENDCMD, null, reqParams, reqStartListener, responseListener, errorListener);
    }


    /**
     * 概述: 车况查询 / 获取车况
     *
     * @auther yao
     * @param userId
     * @param objectId
     *            // 设备对象id / car id
     */
    public static void getCarCondition(int userId, int objectId, onReqStartListener reqStartListener, Listener<JSONObject> responseListener,
                                       ErrorListener errorListener) {
        Map<String, String> reqParams = new HashMap<String, String>();
        reqParams.put("uid", userId + "");
        reqParams.put("objectid", objectId + "");

        _GET_WITH_LISTENERS(URLs.STATE, null, reqParams, reqStartListener, responseListener, errorListener);
    }

    /**
     * 概述: 激活设备
     *
     * @param uid
     * @param imei
     * @param reqStartListener
     * @param responseListener
     * @param errorListener
     * @auther yao
     */
    public static void activateDevice(int uid, String imei, onReqStartListener reqStartListener, Listener<JSONObject> responseListener,
                                      ErrorListener errorListener) {
        Map<String, String> reqParams = new HashMap<String, String>();
        reqParams.put("uid", uid + "");
        reqParams.put("imei", imei);

        _GET_WITH_LISTENERS(URLs.DEVICE_ACTIVE, null, reqParams, reqStartListener, responseListener, errorListener);
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
         * @param reqCode 请求的reqCode
         * @auther yao
         */
        public void onReqStart();
    }
}
