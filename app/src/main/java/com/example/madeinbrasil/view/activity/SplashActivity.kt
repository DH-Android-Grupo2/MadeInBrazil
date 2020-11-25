package com.example.projetointegrador

import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.content.Intent
import android.os.Handler
import androidx.core.os.postDelayed
import com.example.madeinbrasil.R
import com.example.madeinbrasil.InitialActivity

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