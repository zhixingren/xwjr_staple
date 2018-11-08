package com.xwjr.xwjrstaple.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.xwjr.staple.activity.StapleWebView
import com.xwjr.staple.extension.logI
import com.xwjr.xwjrstaple.R
import kotlinx.android.synthetic.main.activity_webview.*

class WebViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)

//        webView.loadUrl("http://debugx5.qq.com")
//        webView.loadUrl("http://p2psp.kfxfd.cn:9080/mobile/more")
//        webView.loadUrl("http://www.baidu.com")
//        webView.loadUrl("http://activity.m.duiba.com.cn/chome/index?from=login&spm=17959.1.1.1")
//        webView.loadUrl("https://wxpay.wxutil.com/mch/pay/h5.v2.php")
        webView.loadUrl("http://10.99.97.29:18080/returnResults?message=%25E5%25BC%2580%25E6%2588%25B7%25E6%2588%2590%25E5%258A%259F&status=true&optype=%25E5%25BC%2580%25E6%2588%25B7")
        webView.addTitleChangeListener(object : StapleWebView.TitleChangeListener {
            override fun changedTitle(title: String) {
                tv_title.text = title
            }
        })

        webView.registerHandler("BidListPage") { _, _ ->
            logI("webViewHandler BidListPage ")
        }
        webView.registerHandler("UserStatusFlag") { _, _ ->
            logI("webViewHandler UserStatusFlagUserStatusFlagUserStatusFlagUserStatusFlag")
        }
        webView.registerHandler("NoBack") { _, _ ->
            logI("webViewHandler NoBackNoBackNoBackNoBackNoBack")
        }
        webView.registerHandler("Phone") { _, _ ->
            logI("webViewHandler NoBackNoBackNoBackNoBackNoBack")
        }

        webView.setProgressBarColors(0xffff0000.toInt(),0xff00ff00.toInt())
    }


    override fun onBackPressed() {
        if (webView.onBackListener()) {
            return
        }
        super.onBackPressed()
    }
}
