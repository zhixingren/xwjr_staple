package com.xwjr.staple.model

class StapleAuthIDCardBean : StapleBaseBean() {


    var result: ResultBean? = null

    class ResultBean {
        var side: Int = 0
        var name: String? = null
        var idNumber: String? = null
        var gender: String? = null
        var nationality: String? = null
        var address: String? = null
        var birth: String? = null
        var issuedBy: String? = null
        var validDateEnd: String? = null
        var validDateStart: String? = null
        var imgUrls: List<ImgUrlsBean>? = null

        class ImgUrlsBean {
            var url: String? = null
            var type: String? = null
            override fun toString(): String {
                return "ImgUrlsBean(url=$url, type=$type)"
            }
        }

        override fun toString(): String {
            return "ResultBean(side=$side, name=$name, idNumber=$idNumber, gender=$gender, nationality=$nationality, address=$address, birth=$birth, issuedBy=$issuedBy, validDateEnd=$validDateEnd, validDateStart=$validDateStart, imgUrls=$imgUrls)"
        }

    }

    override fun toString(): String {
        return "StapleAuthIDCardBean(result=$result)"
    }
}
