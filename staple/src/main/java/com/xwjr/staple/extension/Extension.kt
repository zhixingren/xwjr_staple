package com.xwjr.staple.extension

import android.util.Log
import com.xwjr.staple.constant.StapleConfig
import com.xwjr.staple.util.ToastUtils
import com.xwjr.staple.util.StapleUtils
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
 * 日志打印扩展
 */
fun Any?.logE(content: String) {
    try {
        if (StapleConfig.isDebug) {
            Log.e(TAG, content)
        }
    } catch (e: Exception) {
        Log.e(TAG, "日志打印错误")
        e.printStackTrace()
    }
}


fun Any.showToast(content: String?) {
    try {
        if (content != null) {
            ToastUtils.showShortToast(content)
        } else {
            logI("toast内容为空")
        }
    } catch (e: Exception) {
        logE("toast失败")
        e.printStackTrace()
    }
}

fun Any.showToast(resID: Int?) {
    try {
        if (resID != null) {
            ToastUtils.showShortToast(StapleUtils.getContext().getString(resID))
        } else {
            logI("toast资源id为空")
        }
    } catch (e: Exception) {
        logE("toast失败")
        e.printStackTrace()
    }
}

/**
 *延迟处理
 */
fun Any?.laterDeal(time: Long = 3000, deal: (() -> Unit)? = null) {
    try {
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
    } catch (e: Exception) {
        if (deal != null) {
            deal()
        }
        e.printStackTrace()
        logE("倒计时发生异常")
    }

}
