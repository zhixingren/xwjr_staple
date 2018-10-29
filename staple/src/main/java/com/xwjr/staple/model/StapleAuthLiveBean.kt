package com.xwjr.staple.model

class StapleAuthLiveBean : StapleBaseBean() {


    var result: ResultBean? = null

    class ResultBean {
        var approved: Boolean = false
        override fun toString(): String {
            return "ResultBean(approved=$approved)"
        }
    }

    override fun toString(): String {
        return "StapleAuthIDCardBean(result=$result)"
    }
}
