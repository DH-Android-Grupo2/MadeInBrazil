package com.example.madeinbrasil.view.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.madeinbrasil.R
import com.example.madeinbrasil.adapter.FavoriteMidiaAdapter
import com.example.madeinbrasil.database.MadeInBrazilDatabase
import com.example.madeinbrasil.database.entities.midia.MidiaFirebase
import com.example.madeinbrasil.databinding.ActivityUserBinding
import com.example.madeinbrasil.utils.Constants.ConstantsFilms.BASE_FILM_KEY
import com.example.madeinbrasil.utils.Constants.ConstantsFilms.BASE_MIDIA_KEY
import com.example.madeinbrasil.utils.Constants.ConstantsFilms.ID_FRAGMENTS
import com.example.madeinbrasil.utils.Constants.ConstantsFilms.TUTORIAL
import com.example.madeinbrasil.utils.Constants.ConstantsFilms.VALUE
import com.example.madeinbrasil.utils.Constants.Firebase.DATABASE_USERS
import com.example.madeinbrasil.viewModel.UserViewModel
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetSequence
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_user.*
import kotlinx.coroutines.launch
import java.io.File

class UserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserBinding
    private lateinit var viewModelUser: UserViewModel
    private val ACTIVITY_CALLBACK = 1
    private var reviewInfo: ReviewInfo? = null
    private lateinit var reviewManager: ReviewManager
    private var tutorial = 1
    private var favList = mutableListOf<MidiaFirebase>()
    private var countMovie = 0
    private var countSerie = 0
    private val pickImage = 100
    private var imageUri: Uri? = null

    private val auth by lazy {
        Firebase.auth
    }
    private val storageRef by lazy {
        Firebase.storage.reference
    }
    private val db by lazy {
        Firebase.firestore
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModelUser = ViewModelProvider(this).get(UserViewModel::class.java)
        tutorial = intent.getIntExtra(TUTORIAL, 1)

        binding.tvNomeProfile.text = MenuActivity.USER.name

        binding.btLogOut.setOnClickListener {
            viewModelUser.signOut()
            signIn()
        }

        binding.imBackButton.setOnClickListener {
            finish()
        }



        binding.ivEditIcon.setOnClickListener {
            /*val gallery = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)*/
            val intent: Intent = Intent()
            intent.type = "image/*"
            intent.action = (Intent.ACTION_GET_CONTENT)
            startActivityForResult(intent,pickImage)
        }

       /* binding.tvNumAmigos.setOnClickListener {
            startFriendsActivity(this@UserActivity)
        }*/

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
        if(tutorial == 0) {
            tutorialImplementation()
        }
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

    private fun tutorialImplementation() {
        TapTargetSequence(this).targets(
                TapTarget.forView(binding.ivEditIcon,
                        getString(R.string.string_edit_tutorial_title),
                        getString(R.string.string_edit_tutorial_description))
                        .cancelable(false)
                        .outerCircleColor(R.color.colorAccentOpaque)
                        .targetCircleColor(R.color.colorAccent)
                        .transparentTarget(true).targetRadius(20),
                /*TapTarget.forView(binding.tvAmigos,
                        getString(R.string.string_friends_tutorial_title),
                        getString(R.string.string_friends_tutorial_description))
                        .cancelable(false)
                        .outerCircleColor(R.color.colorAccentOpaque)
                        .targetCircleColor(R.color.colorAccent)
                        .transparentTarget(true).targetRadius(50),*/
                TapTarget.forView(binding.rvCardsListFavorites,
                        getString(R.string.string_my_favorites_tutorial_title),
                        getString(R.string.string_my_favorites_tutorial_description))
                        .cancelable(false)
                        .outerCircleColor(R.color.colorAccentOpaque)
                        .targetCircleColor(R.color.colorAccent)
                        .transparentTarget(true).targetRadius(120),
                TapTarget.forView(binding.rvCardsListLists,
                        getString(R.string.string_my_lists_tutorial_title),
                        getString(R.string.string_my_lists_tutorial_description))
                        .cancelable(false)
                        .outerCircleColor(R.color.colorAccentOpaque)
                        .targetCircleColor(R.color.colorAccent)
                        .transparentTarget(true).targetRadius(120)
        ).listener(object: TapTargetSequence.Listener{
            override fun onSequenceCanceled(lastTarget: TapTarget?) {}
            override fun onSequenceFinish() {
                MenuActivity.USER.tutorial = 1
                viewModelUser.updateUser(MenuActivity.USER)
                val intent = Intent(this@UserActivity, MenuActivity::class.java)
                startActivity(intent)
            }
            override fun onSequenceStep(lastTarget: TapTarget?, targetClicked: Boolean) {}
        }).start()
    }

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
        db.collection(DATABASE_USERS)
            .document(auth.currentUser?.uid ?: "").get().addOnSuccessListener {
                Glide.with(this)
                    .load(it["profilePhoto"])
                    .placeholder(R.drawable.logo_made_in_brasil)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(binding.ivProfile)

                Glide.with(this)
                    .load(it["profilePhoto"])
                    .placeholder(R.drawable.logo_made_in_brasil)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(binding.ivUserBackgroundPhoto)
            }




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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        /*if(resultCode == RESULT_OK && requestCode == pickImage){
            imageUri = data?.data
            binding.ivProfile.setImageURI(imageUri)
        }*/
        if(requestCode==pickImage && resultCode== RESULT_OK && data!=null && data.data!=null){
            data.data?.let{
                imageUri = it
                binding.ivProfile.setImageURI(imageUri)
                uploadPicture()
            }

        }
    }
    private fun uploadPicture() {

        imageUri?.let { imageUri ->

            val savedUri = imageUri
            val profilePhoto = storageRef.child(
                "${(auth.currentUser?.uid ?: "")}/profile"
            )

            profilePhoto.putFile(savedUri)
                .addOnSuccessListener {
                    val downloadUri = it.storage.downloadUrl

                    downloadUri.addOnSuccessListener { thisUri ->
                        val Photodata = hashMapOf<String, Any>(
                            "profilePhoto" to thisUri.toString()
                        )
                        db.collection(DATABASE_USERS)
                            .document(auth.currentUser?.uid ?: "")
                            .update(Photodata)
                    }

                }.addOnFailureListener {
                    Toast.makeText(
                        this@UserActivity,
                        it.localizedMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }

        } ?: run {}

    }
}