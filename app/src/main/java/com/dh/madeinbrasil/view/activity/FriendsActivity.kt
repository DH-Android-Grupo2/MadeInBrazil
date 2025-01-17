package com.dh.madeinbrasil.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.dh.madeinbrasil.R
import com.dh.madeinbrasil.databinding.ActivityFriendsBinding

class FriendsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFriendsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends)

        binding = ActivityFriendsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupFriends()

        val backButton = findViewById<ImageView>(R.id.btBackFriends)

        backButton.setOnClickListener {
            super.onBackPressed()
        }
    }

    fun setupFriends(){

        binding.rvCardsListAmigos.apply {
            layoutManager = LinearLayoutManager(this@FriendsActivity, LinearLayoutManager.VERTICAL, false)
        }

    }
}