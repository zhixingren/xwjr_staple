package com.xwjr.staple.extension

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.annotation.DrawableRes
import android.util.Base64
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget

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

/**
 * 加载资源文件gif
 */
fun ImageView.loadGif(@DrawableRes res: Int) {
    Glide.with(this.context)
            .load(res)
            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
            .into(GlideDrawableImageViewTarget(this))
}