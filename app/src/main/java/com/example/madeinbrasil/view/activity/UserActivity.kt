package com.example.madeinbrasil.view.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.madeinbrasil.adapter.FavoriteMidiaAdapter
import com.example.madeinbrasil.database.MadeInBrazilDatabase
import com.example.madeinbrasil.database.entities.midia.MidiaFirebase
import com.example.madeinbrasil.databinding.ActivityUserBinding
import com.example.madeinbrasil.utils.Constants.ConstantsFilms.BASE_FILM_KEY
import com.example.madeinbrasil.utils.Constants.ConstantsFilms.BASE_MIDIA_KEY
import com.example.madeinbrasil.utils.Constants.ConstantsFilms.ID_FRAGMENTS
import com.example.madeinbrasil.utils.Constants.ConstantsFilms.VALUE
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.launch
import java.io.File

class UserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserBinding
    private val ACTIVITY_CALLBACK = 1
    private var reviewInfo: ReviewInfo? = null
    private lateinit var reviewManager: ReviewManager
    private var favList = mutableListOf<MidiaFirebase>()
    private var countMovie = 0
    private var countSerie = 0

    private val auth by lazy {
        Firebase.auth
    }
    private val storageRef by lazy {
        Firebase.storage.reference
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvNomeProfile.text = auth.currentUser?.displayName

        binding.btLogOut.setOnClickListener {
            auth.signOut()
            signIn()
        }

        binding.imBackButton.setOnClickListener {
            finish()
        }

        binding.tvNumAmigos.setOnClickListener {
            startFriendsActivity(this@UserActivity)
        }

        binding.btGoToFavorites.setOnClickListener {
            startFavoritesActivity(this@UserActivity)
        }

        binding.btGoToLists.setOnClickListener {
            startListsActivity(this@UserActivity)
        }

        filterWatchedAndFavorites()

        binding.rvCardsListFavorites.apply {
            layoutManager = LinearLayoutManager(this@UserActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = FavoriteMidiaAdapter(favList) {midia ->
                when(midia.midiaType) {
                    1 -> {
                        val intent = Intent(this@UserActivity, FilmsAndSeriesActivity::class.java)
                        intent.putExtra(BASE_MIDIA_KEY, midia)
                        intent.putExtra(ID_FRAGMENTS, 1)
                        startActivity(intent)
                    }
                    2 -> {
                        val intent = Intent(this@UserActivity, FilmsAndSeriesActivity::class.java)
                        intent.putExtra(BASE_MIDIA_KEY, midia)
                        intent.putExtra(ID_FRAGMENTS, 2)
                        startActivity(intent)
                    }
                }
            }
        }

        binding.tvNumMovies.text = countMovie.toString()
        binding.tvNumSeries.text = countSerie.toString()

        setupUser()
//        tutorialImplementation()
    }

    private fun startListsActivity(context: Context) {
        val intent = Intent(context, MyProfileOptionsActivity::class.java)
        intent.putExtra(VALUE, 0)
        startActivity(intent)
    }

    private fun startFavoritesActivity(context: Context) {
        val intent = Intent(context, MyProfileOptionsActivity::class.java)
        intent.putExtra(VALUE, 2)
        startActivity(intent)
    }

    private fun filterWatchedAndFavorites() {
        MenuActivity.USER.favorites.forEach {fav ->
            val filter = MenuActivity.MIDIA.filter { it.id == fav }
            filter.forEach {
                favList.add(it)
            }
        }

        MenuActivity.USER.watched.forEach {watched ->
            val filter = MenuActivity.MIDIA.filter { it.id == watched }
            filter.forEach {
                when(it.midiaType) {
                    1 -> countMovie++
                    2 -> countSerie++
                }
            }
        }
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

    private fun startFriendsActivity(context: Context) {
        val intent = Intent(context, FriendsActivity::class.java)
        startActivity(intent)
    }

    private fun startLoginActivity(context: Context) {
        val intent = Intent(context, LogInActivity::class.java)
        startActivity(intent)
    }

    private fun signIn() {
        startActivity(Intent(this, InitialActivity::class.java))
        finish()
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

//    private fun setUpAvaliation() {
//        val manager = ReviewManagerFactory.create(this)
//        val request = manager.requestReviewFlow()
//        var reviewInfo: ReviewInfo? = null
//
//        request.addOnCompleteListener { request ->
//            if (request.isSuccessful) {
//                reviewInfo = request.result
//            }else {
//                reviewInfo = null
//            }
//        }
//        reviewInfo?.let {
//            val flow = manager.launchReviewFlow(this@UserActivity, it)
//            flow.addOnCompleteListener {  }
//                    .addOnFailureListener {  }
//                    .addOnSuccessListener {  }
//        }
//    }
}