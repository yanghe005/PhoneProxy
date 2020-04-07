package com.yhp.phoneproxy.util;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Base64;

import com.yhp.phoneproxy.ProxyApplication;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by xuzhou on 2016/9/12.
 * SharedPreferenceUtil
 */
public class SharedPreferenceUtil {

    public static void putInt(String key, int value) {
        try {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(ProxyApplication.getInstance());
            sp.edit().putInt(key, value).apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getInt(String key, int defaultValue) {
        try {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(ProxyApplication.getInstance());
            return sp.getInt(key, defaultValue);
        } catch (Exception e) {
            e.printStackTrace();
            return defaultValue;
        }
    }

    public static void putLong(String key, long value) {
        try {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(ProxyApplication.getInstance());
            sp.edit().putLong(key, value).apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static long getLong(String key, long defaultValue) {
        try {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(ProxyApplication.getInstance());
            return sp.getLong(key, defaultValue);
        } catch (Exception e) {
            e.printStackTrace();
            return defaultValue;
        }
    }

    public static void putString(String key, String value) {
        try {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(ProxyApplication.getInstance());
            sp.edit().putString(key, value).apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getString(String key, String defaultValue) {
        try {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(ProxyApplication.getInstance());
            return sp.getString(key, defaultValue);
        } catch (Exception e) {
            e.printStackTrace();
            return defaultValue;
        }
    }

    public static void putBoolean(String key, boolean value) {
        try {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(ProxyApplication.getInstance());
            sp.edit().putBoolean(key, value).apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        try {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(ProxyApplication.getInstance());
            return sp.getBoolean(key, defaultValue);
        } catch (Exception e) {
            e.printStackTrace();
            return defaultValue;
        }
    }

    public static void remove(String key) {
        try {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(ProxyApplication.getInstance());
            sp.edit().remove(key).apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * writeObject 方法负责写入特定类的对象的状态，以便相应的 readObject 方法可以还原它
     * 最后，用Base64.encode将字节文件转换成Base64编码保存在String中
     *
     * @param object 待加密的转换为String的对象
     * @return String   加密后的String
     */
    private static String Object2String(Object object) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(object);
            String string = new String(Base64.encode(byteArrayOutputStream.toByteArray(), Base64.DEFAULT));
            objectOutputStream.close();
            return string;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 使用Base64解密String，返回Object对象
     *
     * @param objectString 待解密的String
     * @return object      解密后的object
     */
    private static Object String2Object(String objectString) {
        byte[] mobileBytes = Base64.decode(objectString.getBytes(), Base64.DEFAULT);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(mobileBytes);
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(byteArrayInputStream);
            Object object = objectInputStream.readObject();
            objectInputStream.close();
            return object;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 使用SharedPreference保存对象
     *
     * @param key        储存对象的key
     * @param saveObject 储存的对象
     */
    public static void save(String key, Object saveObject) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ProxyApplication.getInstance());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String string = Object2String(saveObject);
        editor.putString(key, string);
        editor.commit();
    }

    /**
     * 获取SharedPreference保存的对象
     *
     * @param key     储存对象的key
     * @return object 返回根据key得到的对象
     */
    public static Object get(String key) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ProxyApplication.getInstance());
        String string = sharedPreferences.getString(key, null);
        if (string != null) {
            Object object = String2Object(string);
            return object;
        } else {
            return null;
        }
    }
}
