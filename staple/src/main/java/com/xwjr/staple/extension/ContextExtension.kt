package com.xwjr.staple.extension

import android.content.Context
import android.view.View

/**
 * 获取App版本号
 */
fun Context.getVersionName(): String {
    return try {
        val pm = this.packageManager
        val pi = pm.getPackageInfo(packageName, 0)
        pi.versionName
    } catch (e: Exception) {
        logI("发生异常：获取app版本号失败")
        e.printStackTrace()
        ""
    }
}

//popwindow统一提示
fun Context.showPopTip(layoutId:Int, bText: String = "确定", closeable: Boolean = true, deal: () -> Any = {}) {

//    try {
//        val view = View.inflate(this,layoutId, null).apply {
//            tv_content.text = content
//            tv_sure.text = bText
//            iv_close.visibility = if (closeable) View.VISIBLE else View.GONE
//        }
//
//        val popupWindow = PopupWindow(view,
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT)
//
//        view.tv_sure.setOnClickListener {
//            deal()
//            popupWindow.dismiss()
//        }
//        view.iv_close.setOnClickListener { popupWindow.dismiss() }
//
//        popupWindow.apply {
//            isClippingEnabled = false
//            isFocusable = true// 取得焦点
//            isOutsideTouchable = true
//            isTouchable = true
//            setBackgroundDrawable(BitmapDrawable())
//            showAtLocation(view, Gravity.BOTTOM, 0, 0)
//        }
//    } catch (e: Exception) {
//        e.printStackTrace()
//        showToast("无法加载相关弹层页面")
//    }
    //进入退出的动画
//    popupWindow.setAnimationStyle(R.style.pop_show_hide)
}