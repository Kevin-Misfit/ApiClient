package com.yao.app;

import android.app.Application;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.yao.app.api.ApiClient;
import com.yao.app.api.MyVolley;
import com.yao.app.utils.DatabaseHelper;

public class App extends Application {

    public static App pThis;

    /**
     * @return ApplicationController singleton instance
     */
    public static synchronized App getInstance() {
        return pThis;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        init();

    }

    private void init() {
        pThis = this;

        MyVolley.init(this); // 单例初始化

        // 初始化请求头的appkey
        ApiClient.initAppKey(this);
    }

    /**
     * You'll need this in your class to cache the helper in the class.
     */
    protected static DatabaseHelper mDatabaseHelper = null;

    /**
     * You'll need this in your class to get the helper from the manager once
     * per class.
     */
    public static DatabaseHelper getHelper() {
        if (mDatabaseHelper == null) {
            mDatabaseHelper = OpenHelperManager.getHelper(pThis, DatabaseHelper.class);
        }

        return mDatabaseHelper;
    }

    // FIXME 再整个App生命周期中， 维持数据的链接，是不是不太好？
    @Override
    public void onTerminate() {
        super.onTerminate();

		/*
         * You'll need this in your class to release the helper when done.
		 */
        if (mDatabaseHelper != null) {
            OpenHelperManager.releaseHelper();
            mDatabaseHelper = null;
        }
    }
}
