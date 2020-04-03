package com.yhp.phoneproxy.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.yhp.phoneproxy.R;

/**
 * 作者：YangHePeng
 * 时间：2020/3/22 00:54
 * 邮箱：yanghepeng@miliantech.com
 * 说明：
 */
public class WebViewActivity extends Activity {
    public static final String KEY_WEB_VIEW_URL = "web_view_url";

    public void startActivity(Context context, String url) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(KEY_WEB_VIEW_URL, url);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_web_view);


    }
}
