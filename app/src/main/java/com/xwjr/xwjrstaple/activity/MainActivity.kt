package com.xwjr.xwjrstaple.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.xwjr.staple.extension.logI
import com.xwjr.staple.extension.showToast
import com.xwjr.staple.manager.AuthManager
import com.xwjr.staple.manager.AuthManagerHelper
import com.xwjr.staple.model.StapleAuthIDCardBean
import com.xwjr.xwjrstaple.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AuthManager.getIDCardLicense(this)
        AuthManager.getLivingLicense(this)
        tv_idCardScan.setOnClickListener {
            AuthManager.openScanIdActivity(this, side = 0)
        }
        tv_idCardScan2.setOnClickListener {
//            AuthManager.openScanIdActivity(this, side = 1)
            showToast("aaaa+${System.currentTimeMillis()}")
        }
        tv_liveScan.setOnClickListener {
            AuthManager.startLivingDetect(this)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            if (resultCode == Activity.RESULT_OK)
                when (requestCode) {
                    AuthManager.PAGE_INTO_IDCARDSCAN -> {
                        AuthManager.dealIDCardScan(data!!) { filePath ->
                            logI(filePath)
                            val authManagerHelper = AuthManagerHelper()
                            authManagerHelper.upLoadIDCardInfo(filePath)
                            authManagerHelper.setRiskShieldDataListener(object : AuthManagerHelper.RiskShieldData {
                                override fun liveData(isApproved: Boolean) {

                                }

                                override fun idCardData(authIDCardBean: StapleAuthIDCardBean.ResultBean) {

                                }
                            })
                        }
                    }

                    AuthManager.PAGE_INTO_LIVENESS -> {
                        AuthManager.dealLivingData(this, data!!) { imagesMap, bestImg, delta ->
                            val authManagerHelper = AuthManagerHelper()
                            authManagerHelper.upLoadLiveData("朱小航", "412326199211116919", delta, imagesMap)
                            authManagerHelper.setRiskShieldDataListener(object : AuthManagerHelper.RiskShieldData {
                                override fun liveData(isApproved: Boolean) {

                                }

                                override fun idCardData(authIDCardBean: StapleAuthIDCardBean.ResultBean) {

                                }
                            })
                        }
                    }
                }

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}
