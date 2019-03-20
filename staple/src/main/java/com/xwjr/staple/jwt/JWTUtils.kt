package com.xwjr.staple.jwt

import android.util.Base64
import com.xwjr.staple.constant.StapleConfig
import com.xwjr.staple.extension.logE
import java.net.URLEncoder
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

@Suppress("DEPRECATION")
object JWTUtils {

    val CONTRACT = "CONTRACT"//合同中心数据
    val SMS = "SMS"//短信中心数据
    val LOCATION = "LOCATION"//定位相关数据
    val XIAODAI = "XIAODAI"//小贷
    val FKZX = "FKZX"//风控中心

    fun getKey(type: String): String {
        return when (type) {
            LOCATION -> {
                if (StapleConfig.isDebug) {
                    "a93981e52197e71c353219ee5f3bfe9f"
                } else {
                    "c6f948b0783a2db556c40d488e281e1c"
                }
            }
            CONTRACT -> {
                if (StapleConfig.isDebug) {
                    "bc148b51237d36b1a9c4fc71f21d28f6"
                } else {
                    "fe897cffada1cb2c8821b0b29eadbd8b"
                }
            }
            SMS -> {
                if (StapleConfig.isDebug) {
                    "85a88981250f5c38c053e8de1fcba11a"
                } else {
                    "51b3776aa44ed7d08d4f541ef0f99d26"
                }
            }
            XIAODAI -> {
                if (StapleConfig.isDebug) {
                    "85a88981250f5c38c053e8de1fcba11a"
                } else {
                    "11f4e7da4ced644befc897a483da2dfb"
                }
            }
            FKZX -> {
                if (StapleConfig.isDebug) {
                    "12a57d7feab2e2c73701258e0bdd4a14"
                } else {
                    "de914736569cd5baaf96e7d0a3e824da"
                }
            }
            else -> {
                ""
            }
        }
    }

    fun getJWT(type: String): String {
        return getToken(getKey(type))
    }

    fun getJWT(type: String, playHolders: String): String {
        return getToken(getKey(type), playHolders)
    }

    fun getToken(key: String): String {
        val header = getBase64("{\"alg\":\"HS256\",\"typ\":\"JWT\"}")
        val playHolder = getBase64("{}")
        val secret = hMacSHA256(URLEncoder.encode(header) + "." + URLEncoder.encode(playHolder), key)
        return "$header.$playHolder.$secret"
    }

    fun getToken(key: String, playHolders: String): String {
        val header = getBase64("{\"alg\":\"HS256\",\"typ\":\"JWT\"}")
        val playHolder = getBase64(playHolders)
        val secret = hMacSHA256(URLEncoder.encode(header) + "." + URLEncoder.encode(playHolder), key)
        return "$header.$playHolder.$secret"
    }

    private fun getBase64(data: String): String {
        return try {
            Base64.encodeToString(data.toByteArray(Charsets.UTF_8), Base64.NO_PADDING or Base64.URL_SAFE or Base64.NO_WRAP)
        } catch (e: Exception) {
            logE("Base64 加密失败 data:$data")
            e.printStackTrace()
            ""
        }
    }

    private fun hMacSHA256(data: String, key: String): String {
        return try {
            val sha256 = Mac.getInstance("HmacSHA256")
            val secretKey = SecretKeySpec(key.toByteArray(Charsets.UTF_8), "HmacSHA256")
            sha256.init(secretKey)
            encodeBase64String(sha256, data.toByteArray(Charsets.UTF_8))
        } catch (e: Exception) {
            ""
        }
    }

    private fun encodeBase64String(mac: Mac, data: ByteArray): String {
        return Base64.encodeToString(mac.doFinal(data), Base64.NO_PADDING or Base64.URL_SAFE or Base64.NO_WRAP)
    }

}