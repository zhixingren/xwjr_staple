package com.xwjr.staple.constant

import android.os.Environment
import com.xwjr.staple.extension.logI
import com.xwjr.staple.extension.md5
import java.io.File

object StapleConfig {
    const val WWXHC = "WWXHC"//望望先花c端来源
    const val WWXJK = "WWXJK"//望望小金卡来源
    const val XWJR = "XWJR"//希望金融来源
    const val WWXHB = "WWXHB"//望望先花b端来源
    const val XWB = "XWB"//希望宝来源


    private val fileDir = Environment.getExternalStorageDirectory().path//下载的apk存储路径

    var isDebug = true//是否为debug模式
    var appSource = ""//app来源

    /**
     * 根据不同的app处理不同的逻辑
     */
    fun dealAppSource(wwxhb: (() -> Unit)? = null,
                      wwxhc: (() -> Unit)? = null,
                      xwjr: (() -> Unit)? = null,
                      xwb: (() -> Unit)? = null,
                      wwxjk: (() -> Unit)? = null) {
        when (appSource) {
            WWXHB -> {
                if (wwxhb != null) {
                    wwxhb()
                }
            }
            WWXHC -> {
                if (wwxhc != null) {
                    wwxhc()
                }
            }
            XWJR -> {
                if (xwjr != null) {
                    xwjr()
                }
            }
            XWB -> {
                if (xwb != null) {
                    xwb()
                }
            }
            WWXJK -> {
                if (wwxjk != null) {
                    wwxjk()
                }
            }
        }
    }

    /**
     * appKey
     */
    fun getAppKey(): String {
        return when (appSource) {
            WWXHB -> {
                if (isDebug) {
                    "59ef5110-05ac-11e8-9c72-1773a464aa46"
                } else {
                    "370d0b10-392e-11e7-8c26-0d85857b18c9"
                }
            }
            WWXHC -> {
                if (isDebug) {
                    "49dd08f0-24e6-11e7-b026-6b0b8b32be51"
                } else {
                    "43cea230-257b-11e7-a4be-8f9a9b1b9df4"
                }
            }
            XWJR -> {
                if (isDebug) {
                    "b26fe990-de1f-11e6-9653-cd9f91ade5f0"
                } else {
                    "8f930060-de29-11e6-818e-cd9279385fa2"
                }
            }
            XWB -> {
                if (isDebug) {
                    "dd1a32b0-4421-11e6-a221-d7a475267062"
                } else {
                    "3ec6e250-04c5-11e8-a056-09c2a021e679"
                }
            }
            WWXJK -> {
                if (isDebug) {
                    "8c3653d0-2e42-11e8-8435-7345f0d2ca6a"
                } else {
                    "697417b0-9824-11e7-ba0b-17dfb53ee53f"
                }
            }
            else -> {
                logI("发生异常：未找到对应的AppKey")
                ""
            }
        }
    }

    /**
     * appSecret
     */
    private fun getAppSecret(): String {
        return when (appSource) {
            WWXHB -> {
                if (isDebug) {
                    "63766a9aa7ee116218eb724e0bcfc99b"
                } else {
                    "0252af6b8276ba91e535d4f5ab2ba637"
                }
            }
            WWXHC -> {
                if (isDebug) {
                    "7631919f530ace962eb057d716bacd41"
                } else {
                    "037c3b2f03e4bfca0ebc770c15b5a1de"
                }
            }
            XWJR -> {
                if (isDebug) {
                    "54658b805ec6e46202b0f61f375301b7"
                } else {
                    "f44117fe6aaf84bc8aaab60c9816f147"
                }
            }
            XWB -> {
                if (isDebug) {
                    "d678be54a5aba1ef2965c69051857ec4"
                } else {
                    "72a2820334bc1bb0fa279570f52e159e"
                }
            }
            WWXJK -> {
                if (isDebug) {
                    "b68c488f4513fe970d338cca48022634"
                } else {
                    "c99671ef1617b6fe19ba1d3cb34550cc"
                }
            }
            else -> {
                logI("发生异常：未找到对应的AppSecret")
                ""
            }
        }
    }

    /**
     * 获取appToken
     */
    fun getAppToken(): String {
        return (getAppSecret() + getAppKey()).md5()
    }

    /**
     * 获取图片文件存储路径
     */
    fun getImgFilePath(): String {
        return when (appSource) {
            WWXHB -> {
                createFileDir("WWXHYWD", "img")
                "$fileDir/WWXHYWD/img"
            }
            WWXHC -> {
                createFileDir("WWXH", "img")
                "$fileDir/WWXH/img"
            }
            XWJR -> {
                createFileDir("XWJR", "img")
                "$fileDir/XWJR/img"
            }
            XWB -> {
                createFileDir("XWB", "img")
                "$fileDir/XWB/img"
            }
            WWXJK -> {
                createFileDir("WWXJK", "img")
                "$fileDir/WWXJK/img"
            }
            else -> {
                logI("发生异常：未找到对应的App来源,无法创建相应的文件夹")
                ""
            }
        }
    }

    /**
     * 获取开屏页文件存储名
     */
    fun getSplashFileName(): String {
        return when (appSource) {
            WWXHB -> {
                "望望先花业务端.png"
            }
            WWXHC -> {
                "望望先花.png"
            }
            XWJR -> {
                "希望金融.png"
            }
            XWB -> {
                "希望宝.png"
            }
            WWXJK -> {
                "望望小金卡.png"
            }
            else -> {
                logI("发生异常：未找到对应的App来源,无法创建相应的文件夹")
                ""
            }
        }
    }

    /**
     * 获取apk文件存储目录
     */
    fun getAppFilePath(): String {
        return when (appSource) {
            WWXHB -> {
                createFileDir("WWXHYWD", "apk")
                "$fileDir/WWXHYWD/apk"
            }
            WWXHC -> {
                createFileDir("WWXH", "apk")
                "$fileDir/WWXH/apk"
            }
            XWJR -> {
                createFileDir("XWJR", "apk")
                "$fileDir/XWJR/apk"
            }
            XWB -> {
                createFileDir("XWB", "apk")
                "$fileDir/XWB/apk"
            }
            WWXJK -> {
                createFileDir("WWXJK", "apk")
                "$fileDir/WWXJK/apk"
            }
            else -> {
                logI("发生异常：未找到对应的App来源,无法创建相应的文件夹")
                ""
            }
        }
    }

    /**
     * 获取apk文件存储名
     */
    fun getAppFileName(): String {
        return when (appSource) {
            WWXHB -> {
                "望望先花业务端.apk"
            }
            WWXHC -> {
                "望望先花.apk"
            }
            XWJR -> {
                "希望金融.apk"
            }
            XWB -> {
                "希望宝.apk"
            }
            WWXJK -> {
                "望望小金卡.apk"
            }
            else -> {
                logI("发生异常：未找到对应的App来源,无法创建相应的文件夹")
                ""
            }
        }
    }

    /**
     * 获取apk安装失败时的提示
     */
    fun getAppInstallFailHint(): String {
        return when (appSource) {
            WWXHB -> {
                "安装失败，请前往'文件管理--WWXHYWD--apk'手动安装"
            }
            WWXHC -> {
                "安装失败，请前往'文件管理--WWXH--apk'手动安装"
            }
            XWJR -> {
                "安装失败，请前往'文件管理--XWJR--apk'手动安装"
            }
            XWB -> {
                "安装失败，请前往'文件管理--XWB--apk'手动安装"
            }
            WWXJK -> {
                "安装失败，请前往'文件管理--WWXJK--apk'手动安装"
            }
            else -> {
                logI("发生异常：未找到对应的App来源,无法给出提示信息")
                ""
            }
        }
    }


    /**
     * 创建文件夹
     */
    private fun createFileDir(vararg folder: String) {
        var filePath = fileDir
        var file: File
        for (i in folder.indices) {
            filePath = filePath + "/" + folder[i]
            file = File(filePath)
            if (!file.exists()) {
                file.mkdir()
            }
        }
    }

}
