package com.xwjr.staple.extension

import android.app.Activity
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.WindowManager
import java.io.File

/**
 * 开屏页状态栏导航栏适配
 */
fun Activity.setStatusBar() {
    try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
    } catch (e: Exception) {
        logI("发生异常：开屏页状态栏，导航栏适配失败")
        e.printStackTrace()
    }
}

/**
 * 设置Window背景
 * resId: 资源id ,
 * res:drawble
 * file:File
 * filePath:图片本地路径地址
 */
fun Activity.setWindowBG(resId: Int? = null, res: Drawable? = null, file: File? = null, filePath: String? = null) {
    try {
        if (resId != null) {
            window.setBackgroundDrawable(resources.getDrawable(resId))
            return
        }
        if (res != null) {
            window.setBackgroundDrawable(res)
            return
        }
        if (file != null) {
            window.setBackgroundDrawable(BitmapDrawable(BitmapFactory.decodeFile(file.path)))
            return
        }
        if (filePath != null) {
            window.setBackgroundDrawable(BitmapDrawable.createFromPath(filePath))
            return
        }
    } catch (e: Exception) {
        logI("发生异常：设置window背景失败")
        e.printStackTrace()
    }
}
