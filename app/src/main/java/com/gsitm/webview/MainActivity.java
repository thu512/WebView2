package com.gsitm.webview;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.util.Set;

public class MainActivity extends AppCompatActivity {


    private WebView mWebView;
    private WebSettings mWebSetting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWebView = findViewById(R.id.web_view);

        mWebView.setWebViewClient(new WebViewClient());
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.setWebViewClient(new WebViewClient() {


            //url 가로채는 부분
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {


                //gsepartner 일 경우
                if(url.startsWith("gsepartner:")){
                    Uri uri = Uri.parse(url);

                    //파라미터 파싱 -> 콜백 함수명 추출
                    String callBack = uri.getQueryParameter("callback");


                    Log.d("웹뷰", "gsepartner호출 "+url.toString()+" 콜백 함수명: "+callBack);

                    //웹으로 전송할 앱내에 데이터
                    JSONObject json = new JSONObject();
                    try {
                        json.put("token","abcdefg1234");
                        json.put("name","LCJ");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    //웹 자바스크립트 콜백함수 호출
                    mWebView.loadUrl("javascript:"+callBack+"("+json+")");


                }else{
                    view.loadUrl(url);
                }
                return true;
            }

            /**
             * 웹페이지 로딩이 시작할 때 처리
             */
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

            }


            /**
             * 웹페이지 로딩중 에러가 발생했을때 처리
             */
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(getApplicationContext(),"로딩에 실패하였습니다.",Toast.LENGTH_LONG).show();

            }

            /**
             * 웹페이지 로딩이 끝났을 때 처리
             */
            @Override
            public void onPageFinished(WebView view, String url) {

            }

        });

        mWebSetting = mWebView.getSettings();
        mWebSetting.setJavaScriptEnabled(true);
        mWebSetting.setLoadWithOverviewMode(true);
        mWebSetting.setBuiltInZoomControls(false);
        mWebSetting.setSupportZoom(true);
        mWebSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        mWebSetting.setDefaultZoom(WebSettings.ZoomDensity.FAR);


        mWebView.loadUrl("file:///android_asset/www/sample.html");

    }
}
