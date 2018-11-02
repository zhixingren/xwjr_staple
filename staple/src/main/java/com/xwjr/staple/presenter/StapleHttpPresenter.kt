package com.xwjr.staple.presenter

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.xwjr.staple.constant.StapleConfig
import com.xwjr.staple.constant.StapleHttpUrl
import com.xwjr.staple.extension.err
import com.xwjr.staple.extension.logI
import okhttp3.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

class StapleHttpPresenter(private val context: Context, private val contract: StapleHttpContract) {

    /**
     * 统一的handler数据发送
     */
    private fun sendData(url: String, data: String) {
        Handler(Looper.getMainLooper()).post {
            contract.statusBack(url, data)
        }
    }

    /**
     * 统一的handler数据发送
     */
    private fun sendBundle(data: Bundle) {
        Handler(Looper.getMainLooper()).post {
            contract.statusBack(data.getString("url")!!, data)
        }
    }

    /**
     * 查询升级数据
     */
    fun queryUpdateInfo() {
        logI("开始获取app版本数据...")
        val url = StapleHttpUrl.updateInfoUrl(context)
        logI("请求URL:$url")
        OkHttpClient().newCall(Request.Builder()
                .url(url)
                .get()
                .build()
        ).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                logI("发生异常：查询升级数据失败 $url")
                e.printStackTrace()
                sendData(url.err(), "")
            }

            override fun onResponse(call: Call, response: Response) {
                val data = response.body()?.string().toString()
                logI("返回数据 $url ：\n$data")
                sendData(url, data)
            }
        })
    }

    /**
     * 查询活动数据
     */
    fun queryActivityInfo() {
        logI("开始获取app活动数据...")
        val url = StapleHttpUrl.activityInfoUrl()
        logI("请求URL:$url")
        OkHttpClient().newCall(Request.Builder()
                .url(url)
                .get()
                .build()
        ).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                logI("发生异常：查询活动数据失败 $url")
                e.printStackTrace()
                sendData(url.err(), "")
            }

            override fun onResponse(call: Call, response: Response) {
                val data = response.body()?.string().toString()
                logI("返回数据 $url ：\n$data")
                sendData(url, data)
            }
        })
    }

    /**
     * 查询开屏页数据
     */
    fun querySplashInfo() {
        logI("开始获取app开屏页数据...")
        val url = StapleHttpUrl.splashImgInfoUrl()
        logI("请求URL:$url")
        OkHttpClient().newCall(Request.Builder()
                .url(url)
                .get()
                .build()
        ).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                logI("发生异常：查询开屏页数据失败 $url")
                e.printStackTrace()
                sendData(url.err(), "")
            }

            override fun onResponse(call: Call, response: Response) {
                val data = response.body()?.string().toString()
                logI("返回数据 $url ：\n$data")
                sendData(url, data)
            }
        })
    }


    /**
     * 下载apk
     */
    fun downLoadApk(url: String) {
        logI("开始下载apk...")
        logI("下载URL:$url")
        OkHttpClient().newCall(Request.Builder()
                .url(url)
                .build()
        ).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                logI("发生异常：apk下载失败 $url")
                e.printStackTrace()
                sendData(url.err(), "")
            }

            override fun onResponse(call: Call, response: Response) {
                var inputStream: InputStream? = null
                val buf = ByteArray(2048)
                var len: Int
                var fos: FileOutputStream? = null
                try {
                    inputStream = response.body()?.byteStream()
                    val total = response.body()?.contentLength()
                    val file = File(StapleConfig.getAppFilePath() + "/" + StapleConfig.getAppFileName())
                    if (file.exists()) {
                        file.delete()
                    }
                    file.createNewFile()

                    fos = FileOutputStream(file)
                    var sum: Long = 0
                    while (true) {
                        len = inputStream?.read(buf)!!
                        if (len == -1) {
                            break
                        }
                        fos.write(buf, 0, len)
                        sum += len
                        val progress = (sum * 1.0f / total!! * 100).toInt()
                        // 下载中
                        logI("安装包大小：$total  当前进度：$sum  下载百分比：$progress%")
                        sendBundle(Bundle().apply {
                            putString("url", url)
                            putInt("progress", progress)
                            putLong("total", total)
                            putLong("sum", sum)
                        })
                    }
                    fos.flush()
                } catch (e: Exception) {
                    e.printStackTrace()
                    sendData(url.err(), "")
                } finally {
                    try {
                        inputStream?.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                    try {
                        fos?.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        })
    }

    /**
     * 保存开屏图
     */
    fun downLoadSplashImg(url: String) {
        logI("开始下载开屏图...")
        logI("下载URL:$url")
        OkHttpClient().newCall(Request.Builder()
                .url(url)
                .build()
        ).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                logI("发生异常：开屏图下载失败 $url")
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                var inputStream: InputStream? = null
                val buf = ByteArray(2048)
                var len: Int
                var fos: FileOutputStream? = null
                try {
                    inputStream = response.body()?.byteStream()
                    val total = response.body()?.contentLength()
                    val file = File(StapleConfig.getImgFilePath() + "/" + StapleConfig.getSplashFileName())
                    if (file.exists()) {
                        file.delete()
                    }
                    file.createNewFile()
                    fos = FileOutputStream(file)
                    var sum: Long = 0
                    while (true) {
                        len = inputStream?.read(buf)!!
                        if (len == -1) {
                            break
                        }
                        fos.write(buf, 0, len)
                        sum += len
                        val progress = (sum * 1.0f / total!! * 100).toInt()
                        // 下载中
                        logI("开屏图大小：$total  当前进度：$sum  下载百分比：$progress%")
                    }
                    fos.flush()
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    try {
                        inputStream?.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                    try {
                        fos?.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        })
    }
}
