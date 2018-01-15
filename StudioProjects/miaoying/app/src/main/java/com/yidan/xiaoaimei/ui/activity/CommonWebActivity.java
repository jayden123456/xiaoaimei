package com.yidan.xiaoaimei.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextPaint;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.ConsoleMessage;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yidan.xiaoaimei.R;
import com.yidan.xiaoaimei.base.BaseActivity;
import com.yidan.xiaoaimei.ui.activity.user.LoginActivity;

import butterknife.OnClick;


public class CommonWebActivity
        extends
        BaseActivity {

    private TextView tvTitle;
    private ImageView ivBack;
    private WebView mWebView;
    private TextView tvShare;

    private String mTitle = "";
    private String mUrl = "";
    private WebChromeClient chromeClient = null;


    private int toWhere;

    private int fromType;

//    private CommonDialog commonDialog;

    private PopupWindow mSharePopWindow;

//    private OnekeyShare oks = new OnekeyShare();

    @OnClick(R.id.iv_back)
    public void OnClick() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();// 返回前一个页面
        } else {
            if (fromType == 2) {
                if (toWhere == 1) {
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(this, LoginActivity.class));
                    finish();
                }
            } else {
                finish();
            }

        }
    }

    @Override
    public int getLayoutResId() {
        return R.layout.webview_lay;
    }


    @Override
    public void initView() {
        super.initView();
        mTitle = getIntent().getStringExtra("title");
        mUrl = getIntent().getStringExtra("url");
        if (getIntent().hasExtra("fromType")) {
            fromType = getIntent().getIntExtra("fromType", 0);
        }
        if (getIntent().hasExtra("toWhere")) {
            toWhere = getIntent().getIntExtra("toWhere", 0);
        }

        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setText(mTitle);
        TextPaint tp = tvTitle.getPaint();
        tp.setFakeBoldText(true);

//        tvShare = (TextView) findViewById(R.id.tv_button_right);
//        if (fromType == 3) {
//            tvShare.setVisibility(View.VISIBLE);
//        } else {
//            tvShare.setVisibility(View.GONE);
//        }

//        tvShare.setText("分享");
//        tvShare.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                initSharePopWindow();
//                mSharePopWindow.showAtLocation(tvShare, Gravity.BOTTOM, 0, 0);
//            }
//        });

        mWebView = (WebView) findViewById(R.id.webview_web);
        setWebView();
    }

    private void setWebView() {
        WebSettings webSeting = mWebView.getSettings();
        webSeting.setJavaScriptEnabled(true);
        webSeting.setJavaScriptCanOpenWindowsAutomatically(true);
        webSeting.setBlockNetworkImage(false);

        mWebView.setWebViewClient(new MyWebviewCient());

        chromeClient = new MyChromeClient();

        mWebView.setWebChromeClient(chromeClient);
        webSeting.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSeting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);

        mWebView.setHorizontalScrollBarEnabled(false);
        mWebView.setVerticalScrollBarEnabled(true);
        webSeting.setSupportZoom(false);
        webSeting.setPluginState(WebSettings.PluginState.ON);
        webSeting.setLoadWithOverviewMode(true);
        webSeting.setUseWideViewPort(true); //适应屏幕

        webSeting.setJavaScriptEnabled(true);
//        mWebView.addJavascriptInterface(new CalledByJs(this), "android");

        mWebView.loadUrl(mUrl);
    }



    public class MyChromeClient
            extends
            WebChromeClient {

        @Override
        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            Log.d("ZR", consoleMessage.message() + " at " + consoleMessage.sourceId() + ":" + consoleMessage.lineNumber());
            return super.onConsoleMessage(consoleMessage);
        }

        /*
         * 此处覆盖的是javascript中的alert方法。
         * 当网页需要弹出alert窗口时，会执行onJsAlert中的方法
         * 网页自身的alert方法不会被调用。
         */
        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            /*
             * 此处代码非常重要，若没有，android就不能与js继续进行交互了，
			 * 且第一次交互后，webview不再展示出来。
			 * result：A JsResult to confirm that the user hit enter.
			 * 我的理解是，confirm代表着此次交互执行完毕。只有执行完毕了，才可以进行下一次交互。
			 */
            result.confirm();
            return true;
        }

        /*
         * 此处覆盖的是javascript中的confirm方法。
         * 当网页需要弹出confirm窗口时，会执行onJsConfirm中的方法
         * 网页自身的confirm方法不会被调用。
         */
        @Override
        public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
            return true;
        }

        /*
         * 此处覆盖的是javascript中的confirm方法。
         * 当网页需要弹出confirm窗口时，会执行onJsConfirm中的方法
         * 网页自身的confirm方法不会被调用。
         */
        @Override
        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
            return true;
        }

        /*
         * 如果页面被强制关闭,弹窗提示：是否确定离开？
         * 点击确定 保存数据离开，点击取消，停留在当前页面
         */
        @Override
        public boolean onJsBeforeUnload(WebView view, String url, String message, JsResult result) {
            return true;
        }
    }


    public class MyWebviewCient
            extends
            WebViewClient {
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
            WebResourceResponse response = null;
            response = super.shouldInterceptRequest(view, url);
            return response;
        }

        /*
         * 点击页面的某条链接进行跳转的话，会启动系统的默认浏览器进行加载，调出了我们本身的应用
         * 因此，要在shouldOverrideUrlLoading方法中
         */
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            //使用当前的WebView加载页面
            if (url.contains("tel:")) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(url));
                startActivity(intent);
                return true;
            }
            view.loadUrl(url);
            return true;
        }

        /*
         * 网页加载完毕(仅指主页，不包括图片)
         */
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        /*
         * 网页加载完毕(仅指主页，不包括图片)
         */
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        /*
         * 加载页面资源
         */
        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
        }

        /*
         * 错误提示
         */
        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (fromType == 2) {
            if (toWhere == 1) {
                startActivity(new Intent(this, MainActivity.class));
                finish();
            } else {
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            }
        } else {
            if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
                mWebView.goBack();// 返回前一个页面
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }



}
