package com.gsitm.webview;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.gsitm.webview.writeinterceptingwebview.WriteHandlingWebResourceRequest;
import com.gsitm.webview.writeinterceptingwebview.WriteHandlingWebViewClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.util.Set;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {


    private WebView mWebView;
    private WebSettings mWebSetting;
    private CustomWebViewClient webViewClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Realm.init(this);
        mWebView = findViewById(R.id.web_view);

        mWebView.setWebChromeClient(new WebChromeClient());
        webViewClient = new CustomWebViewClient(mWebView);

        //분리
        mWebView.setWebViewClient(webViewClient);

        mWebSetting = mWebView.getSettings();
        mWebSetting.setAllowFileAccessFromFileURLs(true);
        mWebSetting.setAllowUniversalAccessFromFileURLs(true);
        mWebSetting.setJavaScriptEnabled(true);
        mWebSetting.setLoadWithOverviewMode(true);
        mWebSetting.setBuiltInZoomControls(false);
        mWebSetting.setSupportZoom(true);
        mWebSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        mWebSetting.setDefaultZoom(WebSettings.ZoomDensity.FAR);


        mWebView.loadUrl("file:///android_asset/www/sample.html");

    }
}
