package com.xwjr.staple.manager

import android.annotation.SuppressLint
import android.content.Context
import com.xwjr.staple.extension.logI

object StapleSplashBeanManager {
    private const val STAPLE_TABLE = "STAPLE_TABLE"
    private const val IMG_URL = "SPLASH_IMG_URL"

    /**
     * 保存最新的开屏信息
     */
    @SuppressLint("ApplySharedPref")
    @Suppress("DEPRECATION")
    fun saveSplashInfo(context: Context, imgUrl: String) {
        try {
            val sharedPreferences = context.getSharedPreferences(STAPLE_TABLE, Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString(IMG_URL, imgUrl)
            editor.commit()
        } catch (e: Exception) {
            e.printStackTrace()
            logI("本地存储开屏图信息异常")
        }
    }

    /**
     * 保存最新的开屏信息
     */
    fun clearSplashInfo(context: Context) {
        saveSplashInfo(context, "")
    }

    /**
     * 获取本地存储的开屏信息
     */
    private fun getSplashInfo(context: Context): String? {
        return try {
            val sharedPreferences = context.getSharedPreferences(STAPLE_TABLE, Context.MODE_PRIVATE)
            sharedPreferences.getString(IMG_URL, "")
        } catch (e: Exception) {
            e.printStackTrace()
            logI("本地获取开屏图信息异常")
            ""
        }
    }

    /**
     * 校验是否需要提示活动
     */
    fun isNeedDownloadSplashImg(context: Context, imgUrl: String): Boolean {
        return try {
            val localData = getSplashInfo(context)
            if (localData == imgUrl) {
                logI("不需要更新开屏图")
                false
            } else {
                logI("需要更新开屏图")
                saveSplashInfo(context, imgUrl)
                true
            }
        } catch (e: Exception) {
            logI("校验是否需要提示活动异常")
            e.printStackTrace()
            true
        }

    }
}