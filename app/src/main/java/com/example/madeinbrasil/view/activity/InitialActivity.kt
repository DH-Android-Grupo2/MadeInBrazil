package com.example.madeinbrasil

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatButton

class InitialActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_initial)


        findViewById<AppCompatButton>(R.id.btLogin).setOnClickListener(openActivity(LogInActivity::class.java))
        findViewById<AppCompatButton>(R.id.btRegister).setOnClickListener(openActivity(RegisterActivity::class.java))
    }

    private fun openActivity(activity: Class<out Activity>): View.OnClickListener?{
        return View.OnClickListener {
            startActivity(Intent(this, activity))
        }

    }
}

