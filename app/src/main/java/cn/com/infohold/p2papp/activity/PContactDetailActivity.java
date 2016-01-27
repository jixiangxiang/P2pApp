package cn.com.infohold.p2papp.activity;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import cn.com.infohold.p2papp.R;

public class PContactDetailActivity extends BaseActivity {

    private WebView detailView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pcontact_detail);
    }

    @Override
    protected void initView() {
        initialize();
        getToolbar().setBackgroundResource(R.mipmap.p_invest_list_title_bg);
        getToolbar().setNavigationIcon(R.mipmap.p_back);
        initTitleText(getIntent().getStringExtra("title"), BaseActivity.TITLE_CENTER);
    }

    private void initialize() {
        detailView = (WebView) findViewById(R.id.detailView);
        detailView.setFocusable(false);
        WebSettings webSettings = detailView.getSettings();
        webSettings.setJavaScriptEnabled(false);//设置使用够执行JS脚本
        webSettings.setBuiltInZoomControls(false);//设置使支持缩放
        webSettings.setDefaultTextEncodingName("UTF-8");
        detailView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if (progress == 100) {
                    PContactDetailActivity.this.getProgressDialog().dismiss();
                }
            }
        });
        detailView.setWebViewClient(new WebViewClient() {

            @Override   //转向错误时的处理
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                // TODO Auto-generated method stub
                if (PContactDetailActivity.this.getProgressDialog().isShowing()) {
                    PContactDetailActivity.this.getProgressDialog().dismiss();
                }
                showToastShort("加载错误" + description);
            }
        });
        detailView.loadUrl(getIntent().getStringExtra("url"));
        this.getProgressDialog().show();
    }
}
