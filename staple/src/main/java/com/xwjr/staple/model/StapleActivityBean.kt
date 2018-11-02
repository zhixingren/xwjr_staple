package com.xwjr.staple.model

class StapleActivityBean : StapleCGBean() {
    var data: DataBean? = null

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
