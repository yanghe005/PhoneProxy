package com.yhp.phoneproxy.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Proxy;
import android.os.Build;
import android.util.ArrayMap;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ProxyUtil {

    private static final String LOG_TAG = "ProxyWebUtil";


    public static boolean setProxy(Context context, String host, int port) {
        return setProxyLollipop(context, host, port);
    }

    public static boolean clearProxy(Context context) {
        return setProxyLollipop(context, null, 0);
    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    private static boolean setProxyLollipop(final Context context, String host, int port) {
        System.setProperty("http.proxyHost", host);
        System.setProperty("http.proxyPort", port + "");
        System.setProperty("https.proxyHost", host);
        System.setProperty("https.proxyPort", port + "");
        try {
            Context appContext = context.getApplicationContext();
            Class applictionClass = Class.forName("android.app.Application");
            Field mLoadedApkField = applictionClass.getDeclaredField("mLoadedApk");
            mLoadedApkField.setAccessible(true);
            Object mloadedApk = mLoadedApkField.get(appContext);
            Class loadedApkClass = Class.forName("android.app.LoadedApk");
            Field mReceiversField = loadedApkClass.getDeclaredField("mReceivers");
            mReceiversField.setAccessible(true);
            ArrayMap receivers = (ArrayMap) mReceiversField.get(mloadedApk);
            for (Object receiverMap : receivers.values()) {
                for (Object receiver : ((ArrayMap) receiverMap).keySet()) {
                    Class clazz = receiver.getClass();
                    if (clazz.getName().contains("ProxyChangeListener")) {
                        Method onReceiveMethod = clazz.getDeclaredMethod("onReceive", Context.class, Intent.class);
                        Intent intent = new Intent(Proxy.PROXY_CHANGE_ACTION);
                        onReceiveMethod.invoke(receiver, appContext, intent);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private static Object getFieldValueSafely(Field field, Object classInstance) throws IllegalArgumentException, IllegalAccessException {
        boolean oldAccessibleValue = field.isAccessible();
        field.setAccessible(true);
        Object result = field.get(classInstance);
        field.setAccessible(oldAccessibleValue);
        return result;
    }
}
