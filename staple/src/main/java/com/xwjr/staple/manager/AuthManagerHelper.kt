package com.xwjr.staple.manager

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import com.google.gson.Gson
import com.xwjr.staple.constant.StapleConfig
import com.xwjr.staple.constant.StapleHttpUrl
import com.xwjr.staple.extension.logE
import com.xwjr.staple.extension.logI
import com.xwjr.staple.extension.showToast
import com.xwjr.staple.model.StapleAuthIDCardBean
import com.xwjr.staple.model.StapleAuthLiveBean
import com.xwjr.staple.util.FileUtil
import okhttp3.*
import java.io.IOException
import okhttp3.MultipartBody
import okhttp3.RequestBody


/**
 * 身份证识别，活体检测功能
 */
class AuthManagerHelper {


    /**
     * 统一的handler数据发送
     */
    private fun sendData(url: String, data: String, code: Int) {
        Handler(Looper.getMainLooper()).post {
            when (code) {
                0 -> {
                    dealIDCardResponse(url, data)
                }
                1 -> {
                    dealLiveResponse(url, data)
                }
                2 -> {
                    showToast(data)
                }
            }
        }
    }


    fun upLoadIDCardInfo(imagePath: String, owner: String = "0") {
        try {
            logI("开始上传身份证识别数据...")
            val url = StapleHttpUrl.upLoadIDCardInfo()

            val requestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            val file = FileUtil.getFileByPath(imagePath)
            val body = RequestBody.create(MediaType.parse("image/*"), file)
            requestBody.addFormDataPart("image", file.name, body)
            requestBody.addFormDataPart("source", StapleConfig.getRiskShieldSource())
            requestBody.addFormDataPart("owner", owner)

            logI("请求URL:$url  请求参数：{'image':$imagePath 'source':${StapleConfig.getRiskShieldSource()} 'owner':$owner}")

            OkHttpClient().newCall(Request.Builder()
                    .addHeader("Authorization", "Bearer e1a385b59e064a916d0dc9c5ade676f6bda844db3e3f1b0e8f3a4decf39c1d22")
                    .url(url)
                    .post(requestBody.build())
                    .build()
            ).enqueue(object : Callback {

                override fun onFailure(call: Call, e: IOException) {
                    logE("发生异常：上传身份证识别数据失败 $url")
                    sendData("", "网络异常，上传身份证识别数据失败", 2)
                    e.printStackTrace()
                }

                override fun onResponse(call: Call, response: Response) {
                    val data = response.body()?.string().toString()
                    sendData(url, data, 0)
                }
            })
        } catch (e: Exception) {
            logE("发生异常，身份证数据上传异常")
            e.printStackTrace()
        }
    }

    fun dealIDCardResponse(url: String, data: String) {
        logI("返回数据 $url ：\n$data")
        val authIDCardBean = Gson().fromJson(data, StapleAuthIDCardBean::class.java)
        if (authIDCardBean.checkCodeErrorShow()) {
            if (authIDCardBean.result != null)
                riskShieldData?.idCardData(authIDCardBean.result!!)
            else
                showToast("身份证识别，关键数据为空，请重试..")
        }
    }

    fun upLoadLiveData(name: String, idNumber: String, delta: String, imgMap: MutableMap<String, String>) {
        try {
            logI("开始上传活体识别数据...")
            val url = StapleHttpUrl.upLoadLiveInfo()
            logI("请求URL:$url")
            val requestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            for (key in imgMap.keys) {
                when (key) {
                    "image_best" -> {
                        val imgBest = FileUtil.getFileByPath(imgMap["image_best"])
                        val imgBestBody = RequestBody.create(MediaType.parse("image/*"), imgBest!!)
                        requestBody.addFormDataPart("imgBest", imgBest.name, imgBestBody)
                        logI("请求参数:'imgBest':${imgMap["image_best"]}")
                    }
                    "image_action1" -> {
                        val imgAction1 = FileUtil.getFileByPath(imgMap["image_action1"])
                        val imgAction1Body = RequestBody.create(MediaType.parse("image/*"), imgAction1!!)
                        requestBody.addFormDataPart("imgAction1", imgAction1.name, imgAction1Body)
                        logI("请求参数:'imgAction1':${imgMap["image_action1"]}")
                    }
                    "image_action2" -> {
                        val imgAction2 = FileUtil.getFileByPath(imgMap["image_action2"])
                        val imgAction2Body = RequestBody.create(MediaType.parse("image/*"), imgAction2!!)
                        requestBody.addFormDataPart("imgAction2", imgAction2.name, imgAction2Body)
                        logI("请求参数:'imgAction2':${imgMap["image_action2"]}")
                    }
                    "image_action3" -> {
                        val imgAction3 = FileUtil.getFileByPath(imgMap["image_action3"])
                        val imgAction3Body = RequestBody.create(MediaType.parse("image/*"), imgAction3!!)
                        requestBody.addFormDataPart("imgAction3", imgAction3.name, imgAction3Body)
                        logI("请求参数:'imgAction3':${imgMap["image_action3"]}")
                    }
                    "image_action4" -> {
                        val imgAction4 = FileUtil.getFileByPath(imgMap["image_action4"])
                        val imgAction4Body = RequestBody.create(MediaType.parse("image/*"), imgAction4!!)
                        requestBody.addFormDataPart("imgAction4", imgAction4.name, imgAction4Body)
                        logI("请求参数:'image_action4':${imgMap["imgAction4"]}")
                    }
                    "image_env" -> {
                        val imageEnv = FileUtil.getFileByPath(imgMap["image_env"])
                        val imageEnvBody = RequestBody.create(MediaType.parse("image/*"), imageEnv!!)
                        requestBody.addFormDataPart("imageEnv", imageEnv.name, imageEnvBody)
                        logI("请求参数:'imageEnv':${imgMap["image_env"]}")
                    }
                }

            }

            requestBody.addFormDataPart("source", StapleConfig.getRiskShieldSource())
            requestBody.addFormDataPart("name", name)
            requestBody.addFormDataPart("idNumber", idNumber)
            requestBody.addFormDataPart("delta", delta)
            logI("请求参数:'source':${StapleConfig.getRiskShieldSource()} 'name':$name  'idNumber':$idNumber 'delta':$delta")


            OkHttpClient().newCall(Request.Builder()
                    .addHeader("Authorization", "Bearer e1a385b59e064a916d0dc9c5ade676f6bda844db3e3f1b0e8f3a4decf39c1d22")
                    .url(url)
                    .post(requestBody.build())
                    .build()
            ).enqueue(object : Callback {

                override fun onFailure(call: Call, e: IOException) {
                    logE("发生异常：上传活体识别数据失败 $url")
                    sendData("", "网络异常，上传活体识别数据失败", 2)
                    e.printStackTrace()
                }

                override fun onResponse(call: Call, response: Response) {
                    val data = response.body()?.string().toString()
                    sendData(url, data, 1)
                }
            })
        } catch (e: Exception) {
            logE("发生异常，活体识别数据上传异常")
            e.printStackTrace()
        }
    }

    private fun dealLiveResponse(url: String, data: String) {
        logI("返回数据 $url ：\n$data")
        val authLiveBean = Gson().fromJson(data, StapleAuthLiveBean::class.java)
        if (authLiveBean.checkCodeErrorShow()) {
            if (authLiveBean.result != null && authLiveBean?.result?.approved != null) {
                riskShieldData?.liveData(authLiveBean.result?.approved!!)
            } else {
                showToast("活体识别，关键数据为空，请重试..")
            }
        }
    }

    private var riskShieldData: RiskShieldData? = null

    interface RiskShieldData {
        fun idCardData(authIDCardBean: StapleAuthIDCardBean.ResultBean)
        fun liveData(isApproved: Boolean)
    }

    fun setRiskShieldDataListener(riskShieldData: RiskShieldData) {
        this.riskShieldData = riskShieldData
    }


}
