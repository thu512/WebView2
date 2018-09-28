package com.gsitm.webview;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.gsitm.webview.model.Data;
import com.gsitm.webview.writeinterceptingwebview.WriteHandlingWebResourceRequest;
import com.gsitm.webview.writeinterceptingwebview.WriteHandlingWebViewClient;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Set;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class CustomWebViewClient extends WriteHandlingWebViewClient {


    public CustomWebViewClient(WebView webView) {
        super(webView);
    }


    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WriteHandlingWebResourceRequest request) {

        Uri uri = request.getUrl();
        Log.i("SCHEME2", " "+uri.getScheme());
        //Log.i("SCHEME2", " "+request.getRequestHeaders().toString());

        //gsepartner 일 경우
        if(uri.getScheme().equals("gsepartner")){
            Log.i("METHOD", " "+request.getMethod());
            if(request.getMethod().equals("GET")){
                getRequest(view, request);
            }else if(request.getMethod().equals("POST")){
                Log.i("AJAX", " "+request.getAjaxData());
            }

        }

        return super.shouldInterceptRequest(view, request);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

            Uri uri = request.getUrl();
            Log.i("SCHEME2", " "+uri.getScheme());
            //Log.i("SCHEME2", " "+request.getRequestHeaders().toString());

            //gsepartner 일 경우
            if(uri.getScheme().equals("gsepartner")){
                Log.i("METHOD", " "+request.getMethod());
                if(request.getMethod().equals("GET")){
                    getRequest(view, request);
                }else if(request.getMethod().equals("POST")){
                    Log.i("METHOD", " "+request.getMethod());
                }



            }else{
                view.loadUrl(uri.toString());
            }
            return true;
    }

    public void getRequest(WebView view, WebResourceRequest request){
        Uri uri = request.getUrl();
        //파라미터 파싱 -> 콜백 함수명 추출 -> 맵형태로
        String callBack = uri.getQueryParameter("callback");
        String data1;
        String data2;

        Set<String> keys = uri.getQueryParameterNames();
        HashMap<String, String> params = new HashMap<>();
        for (String key: keys) {
            params.put(key, uri.getQueryParameter(key));
        }
        Data data = new Data(params.get("sendData1"), params.get("sendData2"));

        saveData(data);

        Log.d("웹뷰", "URL "+uri.toString()+" 콜백 함수명: "+callBack);
        Log.d("웹뷰", "data1: "+data.getData1()+" data2: "+data.getData2());

        //웹으로 전송할 앱내에 데이터
        JSONObject json = new JSONObject();
        try {
            json.put("token","abcdefg1234");
            json.put("name","LCJ");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //웹 자바스크립트 콜백함수 호출
        view.loadUrl("javascript:"+callBack+"("+json+")");
    }

    public void saveData(final Data data){

        Realm realm = Realm.getDefaultInstance();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(data);
                RealmQuery<Data> query = realm.where(Data.class);
                RealmResults<Data> result1 = query.findAll();
                Log.d("REALM", "data1: "+result1.first().getData1()+" data2: "+result1.first().getData2());


            }
        });

    }

}
