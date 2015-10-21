package k.webview;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{

    static String url = null;

    long time = 0;

    EditText url_et;
    WebView mWebView;
    Button search_bt;
    ProgressBar mProgressBar;
    WebSettings mWebSetting;
    Toolbar toolbar;

    ActionBar mActionbar;

    float oldY;
    float Y;

    SharedPreferences_Manager pref = new SharedPreferences_Manager(this);
    String key = "Bookmark";
    protected String Homepage = null;

    protected void onCreate(Bundle SavedInstanceState){
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.webview_activity);

        url_et = (EditText)findViewById(R.id.url_et);
        mWebView = (WebView)findViewById(R.id.webView);

        toolbar = (Toolbar)findViewById(R.id.toolbar);

        url_et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchURL();
                    return false;
                }
                return true;
            }
        });

        mWebView.setWebViewClient(new WebClient());
        mWebView.setWebChromeClient(new webViewChrome());

        search_bt = (Button)findViewById(R.id.search_bt);
        mProgressBar = (ProgressBar)findViewById(R.id.progressBar);

        mWebSetting = mWebView.getSettings();
        mWebSetting.setSaveFormData(false);
        mWebSetting.setJavaScriptEnabled(true);
        mWebSetting.setSupportZoom(true);
        mWebSetting.setBuiltInZoomControls(true);
        mWebSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);

        search_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchURL();
            }
        });
        search_bt.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                pref.removeValue(key);
                pref.put(key, url_et.getText().toString());
                Homepage = pref.getValue(key, null);
                ToatShow("this URL<" + pref.getValue(key, null) + ">is set homepage");
                return true;
            }
        });

        if(Homepage == null){
            Homepage = pref.getValue(key,null);
            if(Homepage == null){
                ToatShow("Plz set Homepage");
            }else{
                mWebView.loadUrl(Homepage);
            }
        }else {
            mWebView.loadUrl(Homepage);
        }
        setSupportActionBar(toolbar);
        mWebView.setHorizontalScrollBarEnabled(false);
        mWebView.setVerticalScrollBarEnabled(true);

        keypadDown(url_et);
    }

    public void searchURL(){
        url = url_et.getText().toString();
        if(url.contains("http://")||url.contains("http//")){
            mWebView.loadUrl(url);
        }else {
            mWebView.loadUrl("http://" + url);
        }
        mActionbar = getActionBar();
        toolbar.setVisibility(View.VISIBLE);
    }

    public void keypadDown(EditText ed){
        InputMethodManager mInputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        mInputMethodManager.hideSoftInputFromWindow(ed.getWindowToken(),0);
    }

    @Override
    public void onBackPressed() {
        toolbar.setVisibility(View.VISIBLE);
        url_et.setFocusable(false);
        if (System.currentTimeMillis() > time + 500) {
            time = System.currentTimeMillis();
            if (mWebView.canGoBack()) {
                mWebView.goBack();
                if(Homepage.equals(null)){
                    super.onBackPressed();
                }
            }else{
                super.onBackPressed();
            }
        } else if(System.currentTimeMillis() <= time + 500){
            super.onBackPressed();
        }
    }

    class WebClient extends WebViewClient{

        public boolean shouldOverrideUrlLoading(WebView view, String url){
            mProgressBar.setVisibility(View.VISIBLE);
            view.loadUrl(url);

            return super.shouldOverrideUrlLoading(view, url);
        }
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon){
            super.onPageStarted(view, url, favicon);
            url_et.setText(url);
        }

        @Override
        public void onPageFinished(WebView view, String url){
            super.onPageFinished(view, url);
        }
    }

    class webViewChrome extends WebChromeClient{
        @Override
        public void onProgressChanged(WebView view, int newProgress){
            super.onProgressChanged(view, newProgress);

            if(newProgress < 100){
                mProgressBar.setVisibility(View.VISIBLE);
                mProgressBar.setProgress(newProgress);
            }else{
                mProgressBar.setVisibility(View.INVISIBLE);
            }
        }
    }
    private void ToatShow(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
