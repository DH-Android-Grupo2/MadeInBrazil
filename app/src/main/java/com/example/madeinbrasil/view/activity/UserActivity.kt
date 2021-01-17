package com.example.madeinbrasil.view.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.madeinbrasil.R
import com.example.madeinbrasil.adapter.FavoriteMidiaAdapter
import com.example.madeinbrasil.database.MadeInBrazilDatabase
import com.example.madeinbrasil.databinding.ActivityUserBinding
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetSequence
import kotlinx.coroutines.launch
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

        binding.tvNumAmigos.setOnClickListener {
            startFriendsActivity(this@UserActivity)
        }

        binding.tvFavoritesRecycler.setOnClickListener {
            startMyProfileOptionsActivity(this@UserActivity)
        }

        binding.tvListasRecycler.setOnClickListener {
            startMyProfileOptionsActivity(this@UserActivity)
        }

        lifecycleScope.launch {
            val db = MadeInBrazilDatabase.getDatabase(this@UserActivity).favoriteDao()
            val dbWatched = MadeInBrazilDatabase.getDatabase(this@UserActivity).watchedDao()
            var countMovie = 0
            var countSerie = 0

            binding.rvCardsListFavorites.apply {
                layoutManager = LinearLayoutManager(this@UserActivity, LinearLayoutManager.HORIZONTAL, false)
                adapter = FavoriteMidiaAdapter(db.getMidiaWithFavorites())
            }

            dbWatched.getMidiaWithWatched().forEach {
                when(it.midia.midiaType) {
                    1 -> {
                        countMovie++
                    }
                    2 -> {
                        countSerie++
                    }
                }
            }

            binding.tvNumMovies.text = countMovie.toString()
            binding.tvNumSeries.text = countSerie.toString()
        }

        setupUser()
//        tutorialImplementation()
    }

//    private fun tutorialImplementation() {
//        TapTargetSequence(this).targets(
//                TapTarget.forView(binding.ivEditIcon,
//                        getString(R.string.string_edit_tutorial_title),
//                        getString(R.string.string_edit_tutorial_description))
//                        .cancelable(false)
//                        .outerCircleColor(R.color.colorAccentOpaque)
//                        .targetCircleColor(R.color.colorAccent)
//                        .transparentTarget(true).targetRadius(20),
//                TapTarget.forView(binding.tvAmigos,
//                        getString(R.string.string_friends_tutorial_title),
//                        getString(R.string.string_friends_tutorial_description))
//                        .cancelable(false)
//                        .outerCircleColor(R.color.colorAccentOpaque)
//                        .targetCircleColor(R.color.colorAccent)
//                        .transparentTarget(true).targetRadius(50),
//                TapTarget.forView(binding.rvCardsListFavorites,
//                        getString(R.string.string_my_favorites_tutorial_title),
//                        getString(R.string.string_my_favorites_tutorial_description))
//                        .cancelable(false)
//                        .outerCircleColor(R.color.colorAccentOpaque)
//                        .targetCircleColor(R.color.colorAccent)
//                        .transparentTarget(true).targetRadius(120),
//                TapTarget.forView(binding.rvCardsListLists,
//                        getString(R.string.string_my_lists_tutorial_title),
//                        getString(R.string.string_my_lists_tutorial_description))
//                        .cancelable(false)
//                        .outerCircleColor(R.color.colorAccentOpaque)
//                        .targetCircleColor(R.color.colorAccent)
//                        .transparentTarget(true).targetRadius(120)
//        ).listener(object: TapTargetSequence.Listener{
//            override fun onSequenceCanceled(lastTarget: TapTarget?) {}
//            override fun onSequenceFinish() {}
//            override fun onSequenceStep(lastTarget: TapTarget?, targetClicked: Boolean) {}
//        }).start()
//    }

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