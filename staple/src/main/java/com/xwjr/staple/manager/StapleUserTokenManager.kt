package com.xwjr.staple.manager

import android.annotation.SuppressLint
import android.content.Context
import com.xwjr.staple.extension.logI
import com.xwjr.staple.util.StapleUtils

object StapleUserTokenManager {
    private const val STAPLE_TABLE = "STAPLE_TABLE"
    private const val USER_TOKEN = "USER_TOKEN"

    /**
     * 保存最新的活动信息
     */
    @SuppressLint("ApplySharedPref")
    @Suppress("DEPRECATION")
    fun saveUserToken(token: String) {
        try {
            val sharedPreferences = StapleUtils.getContext().getSharedPreferences(STAPLE_TABLE, Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString(USER_TOKEN, token)
            editor.commit()
        } catch (e: Exception) {
            e.printStackTrace()
            logI("本地存储token失败")
        }
    }

    fun clearUserToken() {
        saveUserToken("")
    }

    /**
     * 获取本地token信息
     */
    fun getUserToken(): String? {
        return try {
            val sharedPreferences = StapleUtils.getContext().getSharedPreferences(STAPLE_TABLE, Context.MODE_PRIVATE)
            sharedPreferences.getString(USER_TOKEN, "")
        } catch (e: Exception) {
            e.printStackTrace()
            logI("本地获取token异常")
            ""
        }
    }
}