package com.xwjr.staple.extension

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64

fun base64ToBitmap(string: String): Bitmap? {
    //将字符串转换成Bitmap类型
    var bitmap: Bitmap? = null
    try {
        val bitmapArray = Base64.decode(string.split(',')[1], Base64.DEFAULT)
        bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.size)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return bitmap
}