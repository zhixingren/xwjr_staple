package com.xwjr.staple.extension

import android.app.AppOpsManager
import android.content.Context
import android.os.Build
import android.view.View
import java.lang.reflect.InvocationTargetException

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

/**
 * 查看通知权限是否开启
 */
fun Context.isNotificationEnabled(): Boolean {
    try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val mAppOps = this.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager

            val appInfo = this.applicationInfo
            val pkg = this.applicationContext.packageName
            val uid = appInfo.uid

            var appOpsClass: Class<*>? = null
            /* Context.APP_OPS_MANAGER */
            appOpsClass = Class.forName(AppOpsManager::class.java.name)
            val checkOpNoThrowMethod = appOpsClass!!.getMethod("checkOpNoThrow", Integer.TYPE, Integer.TYPE,
                    String::class.java)
            val opPostNotificationValue = appOpsClass.getDeclaredField("OP_POST_NOTIFICATION")

            val value = opPostNotificationValue.get(Int::class.java) as Int
            return checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) as Int == AppOpsManager.MODE_ALLOWED

        } else {
            return true
        }
    } catch (e: ClassNotFoundException) {
        e.printStackTrace()
    } catch (e: NoSuchMethodException) {
        e.printStackTrace()
    } catch (e: NoSuchFieldException) {
        e.printStackTrace()
    } catch (e: InvocationTargetException) {
        e.printStackTrace()
    } catch (e: IllegalAccessException) {
        e.printStackTrace()
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return false
}