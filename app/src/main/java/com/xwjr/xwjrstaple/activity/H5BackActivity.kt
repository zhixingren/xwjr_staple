package com.xwjr.xwjrstaple.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.xwjr.staple.extension.isExistActivity
import com.xwjr.staple.extension.setTopApp
import com.xwjr.xwjrstaple.R

class H5BackActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_h5_back)

        if (isExistActivity(MainActivity())) {
            setTopApp()
        } else {
           val  intent = Intent(this, SplashActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
        finish()
    }
}
