package com.xwjr.staple.model

class StapleSplashBean : StapleCGBean() {

    var data: DataBean? = null

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
