package com.xwjr.xwjrstaple.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.xwjr.xwjrstaple.R
import kotlinx.android.synthetic.main.activity_webview.*

class WebViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)

        webView.loadUrl("http://www.xwjr.com")
    }
}
