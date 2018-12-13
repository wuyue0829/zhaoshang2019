package pdkj.zhaoshang.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import pdkj.zhaoshang.R;

public class BaiDuYunActivity extends Activity {
    private WebView view_web;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baiduyun);
        initView();
        initData();

    }


    private void initView(){
        view_web =findViewById(R.id.view_web);
    }


    private void initData(){
       /* WebSettings webSettings = this.view_web.getSettings();
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setJavaScriptEnabled(true);
        this.view_web.loadDataWithBaseURL("about:blank", "https://pan.baidu.com", "text/html", "utf-8", null);
        this.view_web.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);*/
        view_web.loadUrl("https://pan.baidu.com");
    }
}
