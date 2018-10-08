package com.xwjr.staple.model

class StapleActivityBean {
    var success: Boolean = false
    var data: DataBean? = null
    var message: String? = null

    class DataBean {
        var title: String? = null
        var url: String? = null
        var pcUrl: String? = null
        var imgUrl: String? = null
        var pcImgUrl: String? = null
        var serial: String? = null
        var description: String? = null
        var serverTime: String? = null
    }
}
