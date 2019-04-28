package com.xwjr.staple.manager

import android.Manifest
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import cn.jpush.android.api.CustomPushNotificationBuilder
import cn.jpush.android.api.JPushInterface
import com.megvii.idcardlib.IDCardScanActivity
import com.megvii.idcardlib.util.Util
import com.megvii.idcardquality.IDCardQualityLicenseManager
import com.megvii.licensemanager.Manager
import com.megvii.livenessdetection.LivenessLicenseManager
import com.megvii.livenesslib.LivenessActivity
import com.megvii.livenesslib.util.ConUtil
import com.xwjr.staple.R
import com.xwjr.staple.constant.StapleConfig
import com.xwjr.staple.extension.laterDeal
import com.xwjr.staple.extension.logE
import com.xwjr.staple.extension.logI
import com.xwjr.staple.extension.showToast
import com.xwjr.staple.permission.PermissionUtils
import com.xwjr.staple.util.FileUtil
import org.json.JSONException
import org.json.JSONObject
import java.io.*

/**
 * 身份证识别，活体检测功能
 */
object JpushManager {
    fun defaultJpushView(context: Context) {
        val customPushNotificationBuilder = CustomPushNotificationBuilder(
                context, R.layout.staple_jpush_notification_view,
                R.id.icon,
                R.id.title,
                R.id.text
        )
        // 指定定制的 Notification Layout
        customPushNotificationBuilder.statusBarDrawable = R.mipmap.ic_launcher
        // 指定最顶层状态栏小图标
        customPushNotificationBuilder.layoutIconDrawable = R.mipmap.ic_launcher
        // 指定下拉状态栏时显示的通知图标
        JPushInterface.setPushNotificationBuilder(1, customPushNotificationBuilder)
        JPushInterface.setDefaultPushNotificationBuilder(customPushNotificationBuilder)
    }
}
