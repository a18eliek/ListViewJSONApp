package com.example.brom.listviewjsonapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_main);

        //Ta emot allt som blev skickat ifrån MainActivity
        Intent intent = getIntent();
        String url = intent.getStringExtra(MountainDetailsActivity.MOUNTAIN_URL);

        setTitle(url); //url används som titel för denna webview
        visaWebbSida(url);

    }

    public void visaWebbSida(String url) {
        WebView myWebView = findViewById(R.id.webview);
        myWebView.loadUrl(url);

        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        myWebView.setWebChromeClient(new WebChromeClient());
        myWebView.setWebViewClient(new WebViewClient());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if ( id == android.R.id.home ) {
            finish(); //Avsluta denna activity och återgå till förra sidan.
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
