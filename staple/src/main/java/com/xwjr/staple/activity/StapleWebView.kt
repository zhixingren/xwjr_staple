package com.xwjr.staple.activity

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.ClipDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.github.lzyzsd.jsbridge.BridgeWebView
import com.tencent.smtt.sdk.*
import com.xwjr.staple.constant.StapleConfig
import com.xwjr.staple.constant.StapleHttpUrl
import com.xwjr.staple.extension.getVersionName
import com.xwjr.staple.extension.isNotNullOrEmpty
import com.xwjr.staple.extension.logI
import com.xwjr.staple.manager.StapleUserTokenManager
import com.xwjr.staple.util.StapleUtils




class StapleWebView : BridgeWebView {

    private var mContext: Context? = null
    private var progressBar: ProgressBar? = null

    constructor(context: Context) : super(context) {
        mContext = context
        init()
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        mContext = context
        init()
    }

    constructor(context: Context, attributeSet: AttributeSet, i: Int) : super(context, attributeSet, i) {
        mContext = context
        init()
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Suppress("DEPRECATION")
    private fun init() {
        progressBar = ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal)
        progressBar?.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 4)//设置宽高属性
        setProgressBarColors(0xffeeeeee.toInt(),0xff659cff.toInt())
        addView(progressBar)


        requestFocusFromTouch()//支持获取手势焦点，输入用户名、密码或其他
        settings.apply {
            setRenderPriority(WebSettings.RenderPriority.HIGH)//提高渲染的优先级
            javaScriptEnabled = true //支持js
//            pluginsEnabled = true  //支持插件
            useWideViewPort = true  //将图片调整到适合webview的大小
            loadWithOverviewMode = true // 缩放至屏幕的大小
            defaultZoom = WebSettings.ZoomDensity.FAR //屏幕自适应网页,如果没有这个，在低分辨率的手机上显示可能会异常
            setSupportZoom(true)    //支持缩放，默认为true。是下面那个的前提。
            builtInZoomControls = true  //设置可以缩放
            displayZoomControls = false  //隐藏原生的缩放控件
//            textZoom = 100 //设置文本的缩放倍数，默认为 100
            layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN //支持内容重新布局
            supportMultipleWindows()  //多窗口
            allowFileAccess = true //设置可以访问文件
            databaseEnabled = true
            cacheMode = WebSettings.LOAD_NO_CACHE  //关闭webview中缓存
//            setNeedInitialFocus(true) //当webview调用requestFocus时为webview设置节点
            javaScriptCanOpenWindowsAutomatically = true //支持通过JS打开新窗口
            loadsImagesAutomatically = true //支持自动加载图片
            defaultTextEncodingName = "utf-8" //设置编码格式
//            standardFontFamily = "" //设置 WebView 的字体，默认字体为 "sans-serif"
//            defaultFontSize = 20 //设置 WebView 字体的大小，默认大小为 16
//            minimumFontSize = 12 //设置 WebView 支持的最小字体大小，默认为 8

            //当app来源为xwjr时，增加修改userAgent
            when (StapleConfig.appSource) {
                StapleConfig.XWJR -> {
                    val userAgent = userAgentString + " XwjrAppVersion/" + StapleUtils.getContext().getVersionName() + " DevelopPlatform/Android"
                    userAgentString = userAgent
                }
                StapleConfig.WWXJK->{
                    val userAgent = userAgentString + " XwjrAppVersion/" + StapleUtils.getContext().getVersionName() + " DevelopPlatform/ReactNative"
                    userAgentString = userAgent
                }
            }
        }

        //设置cookie
        CookieSyncManager.createInstance(this.context)
        setCookie(CookieManager.getInstance().apply {
            setAcceptCookie(true)
            removeAllCookie()
        })


        setWebChromeClient(object : WebChromeClient() {
            override fun onReceivedTitle(p0: WebView?, p1: String?) {
                super.onReceivedTitle(p0, p1)
                if (p1.isNotNullOrEmpty() && !p1!!.contains("希望金融")) {
                    titleChangeListener?.changedTitle(p1)
                    logI("onReceivedTitle 当前webview标题: $p1")
                }
            }

            override fun onProgressChanged(p0: WebView?, p1: Int) {
                super.onProgressChanged(p0, p1)
                progressBar?.apply {
                    progress = p1
                    visibility = if (p1 == 100) {
                        View.GONE
                    } else {
                        View.VISIBLE
                    }
                }

            }
        })

    }

    /**
     * 配置域名cookie
     */
    private fun setCookie(cookieManager: CookieManager?) {
        try {
            for (item in StapleHttpUrl.getDomainUrl()) {
                cookieManager?.setCookie(item, "device=android")
                cookieManager?.setCookie(item, "ccat=" + StapleUserTokenManager.getUserToken())
            }
            CookieSyncManager.getInstance().sync()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    //返回键处理操作
    fun onBackListener(): Boolean {
        if (canGoBack()) {
            goBack()
            return true
        }
        return false
    }


    private var titleChangeListener: TitleChangeListener? = null

    interface TitleChangeListener {
        fun changedTitle(title: String)
    }

    fun addTitleChangeListener(titleChangeListener: TitleChangeListener) {
        this.titleChangeListener = titleChangeListener
    }

    //设置progressBar颜色
    fun setProgressBarColors(backgroundColor: Int, progressColor: Int) {
        //Background
        val bgClipDrawable = ClipDrawable(ColorDrawable(backgroundColor), Gravity.START, ClipDrawable.HORIZONTAL)
        bgClipDrawable.level = 10000
        //Progress
        val progressClip = ClipDrawable(ColorDrawable(progressColor), Gravity.START, ClipDrawable.HORIZONTAL)
        //Setup LayerDrawable and assign to progressBar
        val progressDrawables = arrayOf<Drawable>(bgClipDrawable, progressClip/*second*/, progressClip)
        val progressLayerDrawable = LayerDrawable(progressDrawables)
        progressLayerDrawable.setId(0, android.R.id.background)
        progressLayerDrawable.setId(1, android.R.id.secondaryProgress)
        progressLayerDrawable.setId(2, android.R.id.progress)
        progressBar?.progressDrawable = progressLayerDrawable
    }


}
