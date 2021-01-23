package com.example.madeinbrasil.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.Switch
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.madeinbrasil.R
import com.example.madeinbrasil.databinding.ActivityMenuBinding
import com.example.madeinbrasil.model.gender.GenreSelected
import com.example.madeinbrasil.view.fragment.FilmsFragment
import com.example.madeinbrasil.view.fragment.HomeFragment
import com.example.madeinbrasil.view.fragment.ListsFragment
import com.example.madeinbrasil.view.fragment.SeriesFragment
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetSequence
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import com.google.firebase.storage.ktx.storage

class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding
    var  genreList: GenreSelected? = null

    private val db by lazy {
        Firebase.firestore
    }
    private val auth by lazy {
        Firebase.auth
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)


      intent?.let {
       genreList = it.getParcelableExtra<GenreSelected>("genreList")
     }

            initFragmentsHome(HomeFragment(), genreList)


        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.buttonHome -> {
                    initFragmentsHome(HomeFragment(),genreList)
                    true
                }
                R.id.buttonFilms -> {
                    initFragments(FilmsFragment())
                    true
                }
                R.id.buttonLists -> {
                    initFragments(ListsFragment())
                    true
                }
                R.id.buttonSeries -> {
                    initFragments(SeriesFragment())
                    true
                }
                else -> false
            }
        }

        supportActionBar?.hide()
    }

    override fun onResume() {
        super.onResume()
        auth.currentUser?.let {
            val documentReference = db.collection(FIREBASE_COLLECTION_USERS).document(it.uid)
            documentReference.get()
                    .addOnSuccessListener { snapshot ->

                    }.addOnFailureListener {
                        Toast.makeText(this, it.localizedMessage, Toast.LENGTH_SHORT).show()
                    }
        } ?: run {
            signIn()
        }
    }

    private fun signIn() {
        startActivity(Intent(this, InitialActivity::class.java))
        finish()
    }


    private fun initFragments(fragment: Fragment) {
        val fragmentStart = supportFragmentManager.beginTransaction()
        fragmentStart.replace(R.id.flContainerMenu, fragment)
        fragmentStart.commit()
    }

      private fun initFragmentsHome(fragment: Fragment, genreSelected: GenreSelected?) {
       val bundle = Bundle()
       bundle.putParcelable("Selected", (genreSelected))
       fragment.arguments = bundle

       val fragmentStart = supportFragmentManager.beginTransaction()
       fragmentStart.replace(R.id.flContainerMenu, fragment)
        fragmentStart.commit()
    }

    companion object {
        private const val FIREBASE_COLLECTION_USERS = "users"
    }

}