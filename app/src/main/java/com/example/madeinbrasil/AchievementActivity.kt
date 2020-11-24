package com.example.madeinbrasil

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.madeinbrasil.databinding.ActivityAchievementBinding

class AchievementActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAchievementBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAchievementBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}