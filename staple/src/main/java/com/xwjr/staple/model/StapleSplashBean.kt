package com.xwjr.staple.model

class StapleSplashBean {

    var success: Boolean = false
    var data: DataBean? = null
    var message: String? = null

    class DataBean {

        var id: Int = 0
        var title: String? = null
        var delay: Int = 0
        var imgUrl: String? = null
        var appkey: String? = null
        var url: String? = null
        var description: String? = null
        var enable: Boolean = false
        var createdAt: String? = null
        var updatedAt: String? = null
    }
}
