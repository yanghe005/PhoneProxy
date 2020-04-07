package com.yhp.phoneproxy.view;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.yhp.phoneproxy.ProxyApplication;
import com.yhp.phoneproxy.R;
import com.yhp.phoneproxy.proxy.BrowserMobProxy;
import com.yhp.phoneproxy.proxy.core.har.HarEntry;
import com.yhp.phoneproxy.proxy.core.har.HarPage;

import java.util.List;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showPort((TextView) findViewById(R.id.proxyPort));

        findViewById(R.id.temp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ProxyApplication.getInstance().isInitProxy) {
                    return;
                }
                Log.d("yanghepeng", "ok");

                BrowserMobProxy proxy = ProxyApplication.getInstance().proxy;
                List<HarPage> harPageList = proxy.getHar().getLog().getPages();
                for (HarPage harPage: harPageList) {
                    List<HarEntry> entryList = proxy.getHar(harPage.getId()).getLog().getEntries();
                    for (HarEntry entry: entryList) {
                        Log.d("yanghepeng", "content:   " + entry.getResponse().getContent().getText());
                    }
                }
            }
        });
    }

    private void showPort(final TextView view) {
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (ProxyApplication.getInstance().isInitProxy) {
                    view.setText("端口号: " + ProxyApplication.getInstance().proxyPort);
                } else {
                    showPort(view);
                }
            }
        }, 300);
    }
}
