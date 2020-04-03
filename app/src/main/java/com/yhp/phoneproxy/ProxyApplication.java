package com.yhp.phoneproxy;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.yhp.phoneproxy.proxy.BrowserMobProxy;
import com.yhp.phoneproxy.proxy.BrowserMobProxyServer;
import com.yhp.phoneproxy.proxy.bean.ResponseFilterRule;
import com.yhp.phoneproxy.proxy.proxy.CaptureType;
import com.yhp.phoneproxy.util.SharedPreferenceUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * 作者：YangHePeng
 * 时间：2020/4/1 00:59
 * 邮箱：yanghepeng@miliantech.com
 * 说明：
 */
public class ProxyApplication extends MultiDexApplication {
    public BrowserMobProxy proxy;

    public List<ResponseFilterRule> ruleList = new ArrayList<>();
    public int proxyPort = 8888;
    public Boolean isInitProxy = false;

    private static ProxyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
    }

    public static ProxyApplication getInstance() {
        return instance;
    }

    public void startProxy() {
        try {
            proxy = new BrowserMobProxyServer();
            proxy.setTrustAllServers(true);
            proxy.start(8888);
        } catch (Exception e) {
            // 防止8888已被占用
            Random rand = new Random();
            int randNum = rand.nextInt(1000) + 8000;
            proxyPort = randNum;

            proxy = new BrowserMobProxyServer();
            proxy.setTrustAllServers(true);
            proxy.start(randNum);
        }
        Log.e("~~~", proxy.getPort() + "");


        Object object = SharedPreferenceUtil.get(this.getApplicationContext(), "response_filter");
        if (object != null && object instanceof List) {
            ruleList = (List<ResponseFilterRule>) object;
        }

        SharedPreferences shp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        if (shp.getBoolean("enable_filter", false)) {
            Log.e("~~~enable_filter", "");
            initResponseFilter();
        }

        // 设置hosts
//        if (shp.getString("system_host", "").length() > 0) {
//            AdvancedHostResolver advancedHostResolver = proxy.getHostNameResolver();
//            for (String temp : shp.getString("system_host", "").split("\\n")) {
//                if (temp.split(" ").length == 2) {
//                    advancedHostResolver.remapHost(temp.split(" ")[1], temp.split(" ")[0]);
//                    Log.e("~~~~remapHost ", temp.split(" ")[1] + " " + temp.split(" ")[0]);
//                }
//            }
//            proxy.setHostNameResolver(advancedHostResolver);
//        }

        proxy.enableHarCaptureTypes(CaptureType.REQUEST_HEADERS, CaptureType.REQUEST_COOKIES,
                CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_HEADERS, CaptureType.REQUEST_COOKIES,
                CaptureType.RESPONSE_CONTENT);

        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)
                .format(new Date(System.currentTimeMillis()));
        proxy.newHar(time);

        isInitProxy = true;
    }

    public void stopProxy() {
        if (proxy != null) {
            proxy.stop();
        }
    }
}