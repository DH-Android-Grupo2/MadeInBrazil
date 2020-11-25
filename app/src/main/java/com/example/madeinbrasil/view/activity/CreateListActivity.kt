package com.example.madeinbrasil.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.madeinbrasil.databinding.ActivityCreateListBinding

class CreateListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateListBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}