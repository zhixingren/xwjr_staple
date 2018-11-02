package com.xwjr.staple.model

class StapleSMSCaptchaBean : StapleBaseBean() {

    var result: ResultBean? = null

    class ResultBean {

        var smsCaptchaToken: String? = null
//        var expiredAt: Long = 0
    }
}
