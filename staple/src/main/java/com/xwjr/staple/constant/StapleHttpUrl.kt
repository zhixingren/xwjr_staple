package com.xwjr.staple.constant

import android.content.Context
import com.xwjr.staple.extension.getVersionName


object StapleHttpUrl {

    /**
     * 域名配置
     */
    private fun getBaseUrl(): String {
        return if (StapleConfig.isDebug) {
            "http://p2psp.kfxfd.cn:9080"
        } else {
            "https://www.xwjr.com"
        }
    }

    /**
     * 版本升级
     */
    fun updateInfoUrl(context: Context): String {
        return getBaseUrl() + "/apphub/app/checkUpdate?" +
                "appkey=" + StapleConfig.getAppKey() +
                "&bundleVersion=" + context.getVersionName() +
                "&nativeVersion=" + context.getVersionName() +
                "&token=" + StapleConfig.getAppToken() +
                "&platform=android"
    }

    /**
     * app活动
     */
    fun activityInfoUrl(): String {
        return getBaseUrl() + "/apphub/activity/latest/" + StapleConfig.getAppKey()
    }

    /**
     * app开屏页
     */
    fun splashImgInfoUrl(): String {
        return getBaseUrl() + "/apphub/splash/latest/" + StapleConfig.getAppKey()
    }

    /**
     * 上传身份证识别数据
     */
    fun upLoadIDCardInfo(): String {
        return getBaseUrl() + "/riskshield/verify/ocrIdCard/MYSELF"
    }

    /**
     * 上传活体识别数据
     */
    fun upLoadLiveInfo():String{
        return getBaseUrl()+"/riskshield/verify/faceId/MYSELF"
    }
}
