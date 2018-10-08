package com.xwjr.staple.model

class StapleUpdateBean {

    var success: Boolean = false
    var data: DataBean? = null
    var message: String? = null

    class DataBean {

        var nativeVersion: NativeVersionBean? = null
        var bundleVersion: BundleVersionBean? = null

        class NativeVersionBean {

            var hasNew: Boolean = false
            var version: String? = null
            var downloadUrl: String? = null
            var forceUpdate: Boolean = false
            var changeLog: String? = null
        }

        class BundleVersionBean {

            var hasNew: Boolean = false
        }
    }
}
