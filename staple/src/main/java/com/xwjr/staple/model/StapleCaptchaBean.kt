package com.xwjr.staple.model

class StapleCaptchaBean {

    var created_at: String? = null
    var ttl: String? = null
    var token: String? = null
    var captcha: String? = null

    override fun toString(): String {
        return "StapleCaptchaBean(created_at=$created_at, ttl=$ttl, token=$token, captcha=$captcha)"
    }
}
