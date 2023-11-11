package com.atom.android.lebo.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import com.atom.android.lebo.R
import com.atom.android.lebo.ui.main.MainActivity
import com.atom.android.lebo.utils.constants.Constant
import java.util.Timer
import kotlin.concurrent.schedule

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val intentMainActivity = Intent(this@SplashActivity, MainActivity::class.java)
        if (intent.hasExtra(Constant.BUNDLED.ID)) {
            val id = intent.getStringExtra(Constant.BUNDLED.ID)
            intentMainActivity.putExtras(bundleOf(Constant.BUNDLED.BILL_NOTIFICATION to id))
        }
        Timer().schedule(Constant.DELAY.SPLASH_SCREEN) {
            this@SplashActivity.runOnUiThread {
                startActivity(intentMainActivity)
                finish()
            }
        }
    }
}
