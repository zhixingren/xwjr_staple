package com.xwjr.staple.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import cn.jpush.android.api.JPushInterface
import com.google.gson.Gson
import com.xwjr.staple.R

import com.xwjr.staple.constant.StapleConfig
import com.xwjr.staple.constant.StapleHttpUrl
import com.xwjr.staple.extension.*
import com.xwjr.staple.fragment.UpdateDialogFragment
import com.xwjr.staple.manager.StapleActivityBeanManager
import com.xwjr.staple.manager.StapleSplashBeanManager
import com.xwjr.staple.model.StapleActivityBean
import com.xwjr.staple.model.StapleSplashBean
import com.xwjr.staple.model.StapleUpdateBean
import com.xwjr.staple.presenter.StapleHttpContract
import com.xwjr.staple.presenter.StapleHttpPresenter
import java.io.File

abstract class StapleSplashActivity : AppCompatActivity(), StapleHttpContract {

    private lateinit var httpPresenter: StapleHttpPresenter
    //升级数据
    private var updateData: StapleUpdateBean.DataBean.NativeVersionBean = StapleUpdateBean.DataBean.NativeVersionBean()
    //活动数据
    private var activityData: StapleActivityBean = StapleActivityBean()
    //开屏页面延迟时间
    private var splashTime = 2000L

    companion object {
        const val REQUEST_PERMISSION_UPDATE = 600
        const val REQUEST_PERMISSION_WINDOW_BG = 601
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.staple_activity_splash)
        httpPresenter = StapleHttpPresenter(this, this)
        init()
    }

    /**
     * 初始化数据
     */
    @Suppress("DEPRECATION")
    private fun init() {
        setStatusBar()
        queryUpdateInfo()
        querySplashInfo()


        checkPermission(REQUEST_PERMISSION_WINDOW_BG) {
            setWindowBackground()
        }
    }

    /**
     * 设置app开屏页面背景
     */
    private fun setWindowBackground() {
        try {
            val filePath = StapleConfig.getImgFilePath() + "/" + StapleConfig.getSplashFileName()
            if (File(filePath).exists()) {
                setWindowBG(filePath = filePath)
            } else {
                logI("未获取到本地开屏页图片，使用默认图片")
                setDefaultWindowBackground()
            }
        } catch (e: Exception) {
            setDefaultWindowBackground()
            logI("设置apphub配置的开屏页失败")
        }

    }

    private fun setDefaultWindowBackground() {
        try {
            StapleConfig.dealAppSource(
                    wwxhb = {
                        setWindowBG(resId = R.drawable.staple_wwxhb_window_bg)
                    },
                    wwxhc = {
                        setWindowBG(resId = R.drawable.staple_wwxhc_null_window_bg)
                    },
                    xwjr = {
                        setWindowBG(resId = R.drawable.staple_xwjr_window_bg)
                    },
                    xwb = {
                        setWindowBG(res = resources.getDrawable(R.drawable.staple_xwb_window_bg))
                    },
                    wwxjk = {
                        setWindowBG(res = resources.getDrawable(R.drawable.staple_wwxjk_window_bg))
                    },
                    apphub = {
                        setWindowBG(resId = R.drawable.staple_apphub_window_bg)
                    }
            )
        } catch (e: Exception) {
            e.printStackTrace()
            logI("设置默认开屏页失败")
        }
    }

    /**
     * 查询升级数据
     */
    private fun queryUpdateInfo() {
        httpPresenter.queryUpdateInfo()
    }

    /**
     * 查询活动数据
     */
    private fun queryActivityInfo() {
        httpPresenter.queryActivityInfo()
    }

    /**
     * 查询开屏页数据
     */
    private fun querySplashInfo() {
        httpPresenter.querySplashInfo()
    }

    /**
     * 下载开屏图
     */
    private fun downloadSplashImg(url: String?) {
        if (url != null)
            httpPresenter.downLoadSplashImg(url)
    }

    override fun statusBack(i: String, data: Any) {
        when (i) {
            StapleHttpUrl.updateInfoUrl(this) -> {
                try {
                    logI("获取升级数据成功，开始解析")
                    data as String
                    val updateBean = (Gson().fromJson(data, StapleUpdateBean::class.java))
                    if (updateBean.success) {
                        updateData = updateBean.data?.nativeVersion!!
                        if (updateData.hasNew && updateData.downloadUrl.isNotNullOrEmpty()) {
                            checkPermission(REQUEST_PERMISSION_UPDATE) {
                                dealUpdateData()
                            }
                        } else {
                            //无升级
                            logI("无升级：")
                            queryActivityInfo()
                        }
                    } else {
                        logI("未查询到升级版本：")
                        queryActivityInfo()
                    }
                } catch (e: Exception) {
                    logI("发生异常：" + "升级数据解析失败")
                    //无升级
                    logI("无升级：")
                    queryActivityInfo()
                    e.printStackTrace()
                }
            }
            StapleHttpUrl.updateInfoUrl(this).err() -> {
                logI("发生异常：获取升级数据失败")
                queryActivityInfo()
            }
            StapleHttpUrl.activityInfoUrl() -> {
                try {
                    logI("获取活动数据成功，开始解析")
                    data as String
                    activityData = (Gson().fromJson(data, StapleActivityBean::class.java))
                    if (activityData.success && activityData.data != null) {
                        if (StapleActivityBeanManager.isNeedShowActivity(this, activityData.data!!)) {
                            dealActivityData(activityData.data)
                        } else {
                            dealActivityData()
                        }
                    } else {
                        dealActivityData()
                    }
                } catch (e: Exception) {
                    logI("发生异常：" + "活动数据解析失败")
                    e.printStackTrace()
                    dealActivityData()
                }
            }
            StapleHttpUrl.activityInfoUrl().err() -> {
                logI("发生异常：获取活动数据失败")
                dealActivityData()
            }
            StapleHttpUrl.splashImgInfoUrl() -> {
                try {
                    data as String
                    val splashBean = (Gson().fromJson(data, StapleSplashBean::class.java))
                    if (splashBean.success) {
                        if (splashBean.data?.imgUrl != null) {
                            if (StapleSplashBeanManager.isNeedDownloadSplashImg(this, splashBean.data?.imgUrl!!)) {
                                downloadSplashImg(splashBean.data?.imgUrl)
                            }
                        } else {
                            deleteSplashImg()
                        }
                    } else {
                        deleteSplashImg()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
            StapleHttpUrl.splashImgInfoUrl().err() -> {
                logI("发生异常：获取开屏图失败")
                deleteSplashImg()
            }
        }
    }

    /**
     * 移除本地开屏图图片
     */
    private fun deleteSplashImg() {
        try {
            val file = File(StapleConfig.getImgFilePath(), StapleConfig.getSplashFileName())
            if (file.exists()) {
                file.delete()
                logI("已删除本地开屏图")
            } else {
                logI("本地不存在开屏图")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 处理升级相关数据
     */
    private fun dealUpdateData() {
        if (updateData.forceUpdate) {
            //强制升级
            logI("强制升级")
            UpdateDialogFragment
                    .newInstance(false, updateData.downloadUrl!!, "V" + updateData.version!!, updateData.changeLog!!)
                    .show(supportFragmentManager)
        } else {
            //非强制升级
            logI("非强制升级")
            UpdateDialogFragment
                    .newInstance(true, updateData.downloadUrl!!, "V" + updateData.version!!, updateData.changeLog!!).apply {
                        show(supportFragmentManager)
                        setCancelUpdateListener(object : UpdateDialogFragment.CancelUpdate {
                            override fun cancel() {
                                logI("稍后升级")
                                queryActivityInfo()
                            }
                        })
                    }
        }
    }

    /**校验读写权限并执行相应方法*/
    private fun checkPermission(requestCode: Int, deal: (() -> Any)? = null) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            if (deal != null)
                deal()
        } else {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                if (deal != null)
                    deal()
            } else {
                //系统会显示一个请求权限的提示对话框，当前应用不能配置和修改这个对话框
                requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), requestCode)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_PERMISSION_UPDATE -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    dealUpdateData()
                } else {
                    logI("未授予读写权限，无法下载app")
                    Toast.makeText(this, "未授予读写权限，无法下载app", Toast.LENGTH_SHORT).show()
                }
            }
            REQUEST_PERMISSION_WINDOW_BG -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    setWindowBackground()
                } else {
                    logI("未授予读写权限，无法获取windowBg数据")
                    setDefaultWindowBackground()
                }
            }
        }
    }


    //自定义处理数据
    abstract fun customDealActivityData(latestData: StapleActivityBean.DataBean? = null)

    private fun dealActivityData(latestData: StapleActivityBean.DataBean? = null) {
        laterDeal(splashTime) {
            customDealActivityData(latestData)
        }
    }
}
