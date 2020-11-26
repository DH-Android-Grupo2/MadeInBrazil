package com.example.madeinbrasil.view.activity

import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.content.Intent
import android.os.Handler
import com.example.madeinbrasil.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        changeToLogin()
    }

    private fun changeToLogin(){
        val intent = Intent(this, InitialActivity::class.java)
        Handler().postDelayed({
            intent.change()
        }, 2000)
    }
    private fun Intent.change(){
        startActivity(this)
        finish()

    }
}