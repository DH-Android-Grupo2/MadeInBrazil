package com.example.madeinbrasil.view.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatButton
import com.example.madeinbrasil.R

class LogInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        findViewById<AppCompatButton>(R.id.btFaceLogin).setOnClickListener(openActivity(SelectActivity::class.java))
        findViewById<AppCompatButton>(R.id.btGoogleLogin).setOnClickListener(openActivity(SelectActivity::class.java))
        findViewById<AppCompatButton>(R.id.btSaveLogin).setOnClickListener(openActivity(SelectActivity::class.java))
        findViewById<ImageView>(R.id.ivArrowBackLogin).setOnClickListener(openActivity(InitialActivity::class.java))
    }

    private fun openActivity(activity: Class<out Activity>): View.OnClickListener? {
        return View.OnClickListener {
            startActivity(Intent(this, activity))
        }
    }
}

