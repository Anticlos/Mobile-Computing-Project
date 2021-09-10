package com.ucph.mc.dui.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.ucph.mc.dui.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        showDUI()
    }

    fun showDUI(){
        val duration = 3000L
        val handle = Handler()
        handle.postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        },duration)
    }
}
