package com.xwjr.staple.extension

import android.util.Log
import com.xwjr.staple.constant.StapleConfig
import java.util.*

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

/**
 *延迟处理
 */
fun Any?.laterDeal( time: Long = 3000,deal: (() -> Unit)? = null) {
    val timer = Timer()
    val task = object : TimerTask() {
        override fun run() {
            logI("倒计时结束")
            if (deal != null) {
                logI("倒计时结束后，开始执行任务")
                deal()
            }
        }
    }
    logI("开始倒计时")
    timer.schedule(task, time)
}
