package com.example.madeinbrasil.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.madeinbrasil.R
import com.example.madeinbrasil.databinding.ActivityUserBinding

class UserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUser()
    }

    fun setupUser(){
        var image = "https://images.unsplash.com/photo-1511367461989-f85a21fda167?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&w=1000&q=80"
        Glide.with(this)
            .load(image)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.ivProfile)
        Glide.with(this)
            .load("https://pareto.io/wp-content/uploads/2019/06/bg-full.png")
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.ivBGProfile)


        binding.rvCardsListFavorites.apply {
            layoutManager = LinearLayoutManager(this@UserActivity, LinearLayoutManager.HORIZONTAL, false)
        }

        binding.rvCardsListLists.apply {
            layoutManager = LinearLayoutManager(this@UserActivity, LinearLayoutManager.HORIZONTAL, false)
        }
    }
}