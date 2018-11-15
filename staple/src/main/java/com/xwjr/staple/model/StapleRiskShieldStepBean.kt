package com.xwjr.staple.model

class StapleRiskShieldStepBean : StapleBaseBean() {


    var result: ResultBean? = null


    class ResultBean {
        var idCard: Boolean = false
        var faceId: Boolean = false
        var carrier: Boolean = false
        override fun toString(): String {
            return "ResultBean(idCard=$idCard, faceId=$faceId, carrier=$carrier)"
        }

    }

    override fun toString(): String {
        return "StapleRiskShieldStepBean(result=$result)"
    }
}
