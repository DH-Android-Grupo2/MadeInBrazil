package com.example.madeinbrasil.view.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatButton
import com.example.madeinbrasil.R

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)




        findViewById<AppCompatButton>(R.id.btSaveRegister).setOnClickListener(openActivity(SelectActivity::class.java))
        findViewById<AppCompatButton>(R.id.btFaceRegister).setOnClickListener(openActivity(SelectActivity::class.java))
        findViewById<AppCompatButton>(R.id.btGoogleRegister).setOnClickListener(openActivity(SelectActivity::class.java))
        findViewById<ImageView>(R.id.ivArrowBackRegister).setOnClickListener(openActivity(InitialActivity::class.java))
    }

    private fun openActivity(activity: Class<out Activity>): View.OnClickListener? {
        return View.OnClickListener {
            startActivity(Intent(this, activity))
        }
    }
}

