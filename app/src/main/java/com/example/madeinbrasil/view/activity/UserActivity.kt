package com.example.madeinbrasil.view.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.madeinbrasil.R
import com.example.madeinbrasil.databinding.ActivityUserBinding
import org.w3c.dom.Text

class UserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btLogOut.setOnClickListener {
            startLoginActivity(this@UserActivity)
        }

        binding.imBackButton.setOnClickListener {
            finish()
        }

        binding.tvNumListas.setOnClickListener {
            startMyProfileOptionsActivity(this@UserActivity)
        }

        binding.tvNumAmigos.setOnClickListener {
            startFriendsActivity(this@UserActivity)
        }

        binding.tvFavoritesRecycler.setOnClickListener {
            startMyProfileOptionsActivity(this@UserActivity)
        }

        binding.tvListasRecycler.setOnClickListener {
            startMyProfileOptionsActivity(this@UserActivity)
        }

        setupUser()
    }

    private fun startMyProfileOptionsActivity(context: Context) {
        val intent = Intent(context, MyProfileOptionsActivity::class.java)
        startActivity(intent)
    }

    private fun startFriendsActivity(context: Context) {
        val intent = Intent(context, FriendsActivity::class.java)
        startActivity(intent)
    }

    private fun startLoginActivity(context: Context) {
        val intent = Intent(context, LogInActivity::class.java)
        startActivity(intent)
    }

    private fun setupUser(){
        val image = "https://images.unsplash.com/photo-1511367461989-f85a21fda167?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&w=1000&q=80"
        Glide.with(this)
            .load(image)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.ivProfile)
        Glide.with(this)
            .load("https://pareto.io/wp-content/uploads/2019/06/bg-full.png")
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.ivUserBackgroundPhoto)


        binding.rvCardsListFavorites.apply {
            layoutManager = LinearLayoutManager(this@UserActivity, LinearLayoutManager.HORIZONTAL, false)
        }

        binding.rvCardsListLists.apply {
            layoutManager = LinearLayoutManager(this@UserActivity, LinearLayoutManager.HORIZONTAL, false)
        }
    }
}