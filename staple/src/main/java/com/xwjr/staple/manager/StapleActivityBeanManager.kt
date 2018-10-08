package com.xwjr.staple.manager

import android.annotation.SuppressLint
import android.content.Context
import com.xwjr.staple.extension.logI
import com.xwjr.staple.model.StapleActivityBean
import java.text.SimpleDateFormat
import java.util.*

object StapleActivityBeanManager {
    private const val STAPLE_TABLE = "STAPLE_TABLE"
    private const val TITLE = "ACTIVITY_TITLE"
    private const val URL = "ACTIVITY_URL"
    private const val IMG_URL = "ACTIVITY_IMG_URL"
    private const val SERIAL = "ACTIVITY_SERIAL"
    private const val DESCRIPTION = "ACTIVITY_DESCRIPTION"
    private const val SERVER_TIME = "ACTIVITY_SERVER_TIME"

    /**
     * 保存最新的活动信息
     */
    @SuppressLint("ApplySharedPref")
    @Suppress("DEPRECATION")
    fun saveActivityInfo(context: Context, title: String, url: String, imgUrl: String, serial: String, description: String, serverTime: String) {
        try {
            val sharedPreferences = context.getSharedPreferences(STAPLE_TABLE, Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString(TITLE, title)
            editor.putString(URL, url)
            editor.putString(IMG_URL, imgUrl)
            editor.putString(SERIAL, serial)
            editor.putString(DESCRIPTION, description)
            val time = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date(serverTime.toLong()))
            editor.putString(SERVER_TIME, time)
            editor.commit()
        } catch (e: Exception) {
            e.printStackTrace()
            logI("本地存储活动信息异常")
        }
    }

    /**
     * 保存最新的活动信息
     */
    fun saveActivityInfo(context: Context, stapleActivityData: StapleActivityBean.DataBean) {
        try {
            saveActivityInfo(context,
                    stapleActivityData.title!!,
                    stapleActivityData.url!!,
                    stapleActivityData.imgUrl!!,
                    stapleActivityData.serial!!,
                    stapleActivityData.description!!,
                    stapleActivityData.serverTime!!)
        } catch (e: Exception) {
            e.printStackTrace()
            logI("本地存储活动信息异常")
        }
    }

    /**
     * 获取本地存储的活动信息
     */
    fun getActivityInfo(context: Context): StapleActivityBean.DataBean {
        return try {
            val sharedPreferences = context.getSharedPreferences(STAPLE_TABLE, Context.MODE_PRIVATE)
            StapleActivityBean.DataBean().apply {
                title = sharedPreferences.getString(TITLE, "")
                url = sharedPreferences.getString(URL, "")
                imgUrl = sharedPreferences.getString(IMG_URL, "")
                serial = sharedPreferences.getString(SERIAL, "")
                description = sharedPreferences.getString(DESCRIPTION, "")
                serverTime = sharedPreferences.getString(SERVER_TIME, "")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            logI("本地获取活动信息异常")
            StapleActivityBean.DataBean().apply {
                title = ""
                url = ""
                imgUrl = ""
                serial = ""
                description = ""
                serverTime = ""
            }
        }
    }

    /**
     * 校验是否需要提示活动
     */
    fun isNeedShowActivity(context: Context, stapleActivityData: StapleActivityBean.DataBean): Boolean {
        try {
            val localData = getActivityInfo(context)
            if (localData.url == stapleActivityData.url
                    && localData.title == stapleActivityData.title
                    && localData.imgUrl == stapleActivityData.imgUrl
                    && localData.serial == stapleActivityData.serial
                    && localData.description == stapleActivityData.description) {
                val time = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date(stapleActivityData.serverTime?.toLong()!!))
                return if (localData.serverTime?.substring(0, 10) == time.substring(0, 10)) {
                    logI("活动内容未更新，距离上次弹窗未超过一天")
                    false
                } else {
                    logI("活动内容未更新，距离上次弹窗超过一天")
                    saveActivityInfo(context, stapleActivityData)
                    true
                }
            } else {
                logI("活动内容更新，需要弹窗显示")
                saveActivityInfo(context, stapleActivityData)
                return true
            }
        }catch (e:Exception){
            logI("校验是否需要提示活动异常")
            e.printStackTrace()
            return false
        }

    }
}