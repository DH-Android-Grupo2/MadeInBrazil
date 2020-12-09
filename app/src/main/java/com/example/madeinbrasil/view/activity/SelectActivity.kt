package com.example.madeinbrasil.view.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.example.madeinbrasil.R
import com.example.madeinbrasil.databinding.ActivitySelectBinding

class SelectActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySelectBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val button = findViewById<Button>(R.id.btContinueGender)

        button.setOnClickListener {
            startMenuActivity(this@SelectActivity)
        }

    }

    fun startMenuActivity(context: Context) {
        val intent = Intent(context, MenuActivity::class.java)
        startActivity(intent)

    }

}