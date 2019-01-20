package com.rohan.admin.loginapp1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class pdfview extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        String pdfURL = getIntent().getStringExtra("url");

        WebView webView=new WebView(pdfview.this);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);

        //---you need this to prevent the webview from
        // launching another browser when a url
        // redirection occurs---

        webView.setWebViewClient(new Callback());
   //    Toast.makeText(pdfview.this, pdfURL , Toast.LENGTH_SHORT).show();
        if(pdfURL.equals("http://dragon321.esy.es/") ) {
          //  Toast.makeText(pdfview.this, "No PDF available" , Toast.LENGTH_SHORT).show();
            finish();
        }

        //  String pdfURL = "http://dragon321.esy.es/documents/2114919Dec-2016.pdf";
        webView.loadUrl(
                "http://docs.google.com/gview?embedded=true&url=" + pdfURL);

        setContentView(webView);
    }

    private class Callback extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(
                WebView view, String url) {

            return(false);
        }
    }
}
