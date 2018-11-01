package com.xwjr.staple.manager

import android.os.Handler

class test {

    internal fun test() {
        Handler().post { println("-=-=-=>>xianchengid11111 = " + Thread.currentThread().id) }
    }
}
