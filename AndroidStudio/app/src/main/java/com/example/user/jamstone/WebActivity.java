package com.example.user.jamstone;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;

public class WebActivity extends AppCompatActivity {

    private WebView webView;
    private EditText editText;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        webView = (WebView)findViewById(R.id.webView);
        editText = (EditText)findViewById(R.id.editWeb);
        button = (Button)findViewById(R.id.btWeb);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.loadUrl("http://"+ editText.getText().toString()+":5000");
            }
        });


        /*WebSettings settings = webView.getSettings(); //웹뷰 폰에 맞게 크기조절
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);*/

    }
}
