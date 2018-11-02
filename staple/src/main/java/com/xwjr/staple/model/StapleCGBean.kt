package com.xwjr.staple.model

import com.xwjr.staple.extension.isNotNullOrEmpty
import com.xwjr.staple.extension.logE
import com.xwjr.staple.extension.showToast
import com.xwjr.staple.manager.ErrorCodeManager

open class StapleCGBean {
    var success: Boolean = true
    var message = ""
    var error: List<ErrorBean>? = null
    var error_description: String? = null

    fun checkCodeErrorShow(): Boolean {
        return try {
            if (success) {
                true
            } else {
                when {
                    message.isNotNullOrEmpty() -> showToast(message)
                    error_description.isNotNullOrEmpty() -> showToast(error_description)
                    else -> {
                        if (error != null && error?.size!! > 0) {
                            showToast(ErrorCodeManager.getMessage(error!![0].message, "发生未知错误"))
                        } else {
                            showToast("发生未知错误")
                        }
                    }
                }
                false
            }
        } catch (e: Exception) {
            e.printStackTrace()
            logE("发生异常，解析网络请求code/message数据异常")
            false
        }
    }

    fun checkCode(): Boolean {
        return try {
            success
        } catch (e: Exception) {
            false
        }
    }

    class ErrorBean {
        var message: String? = null
        var type: String? = null
        var value: String? = null
        var code: String? = null
    }
}
