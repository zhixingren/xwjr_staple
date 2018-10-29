package com.xwjr.staple.model

import com.xwjr.staple.extension.logE
import com.xwjr.staple.extension.showToast

open class StapleBaseBean {
    var code = ""
    var message = ""
    var error: String? = null
    var error_description: String? = null

    fun checkCodeErrorShow(): Boolean {
        return try {
            if (code == "0000") {
                true
            } else {
                showToast(message)
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
            code == "0000"
        } catch (e: Exception) {
            false
        }
    }
}
