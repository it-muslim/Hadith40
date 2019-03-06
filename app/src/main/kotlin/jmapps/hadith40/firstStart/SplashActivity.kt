package jmapps.hadith40.firstStart

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import jmapps.hadith40.mainScreen.MainActivity

class SplashActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler().postDelayed({
            val toMainActivity = Intent(this, MainActivity::class.java)
            startActivity(toMainActivity)
            finish()
        }, 500)
    }
}