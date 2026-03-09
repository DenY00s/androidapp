package com.novikov.myfirstapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val progressBar: ProgressBar = findViewById(R.id.progressBar)
        val sharedPrefHelper = SharedPrefHelper(this)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, ContentActivity::class.java)
            startActivity(intent)
            finish()

            startActivity(intent)
        }, 3000)
    }
}