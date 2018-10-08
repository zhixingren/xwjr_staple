package com.xwjr.staple.extension

import android.util.Log
import com.xwjr.staple.constant.StapleConfig

private const val TAG = "xwjrStaple"
/**
 * 日志打印扩展
 */
fun Any?.logI(content: String) {
    try {
        if (StapleConfig.isDebug) {
            Log.i(TAG, content)
        }
    } catch (e: Exception) {
        Log.i(TAG, "日志打印错误")
        e.printStackTrace()
    }
}
