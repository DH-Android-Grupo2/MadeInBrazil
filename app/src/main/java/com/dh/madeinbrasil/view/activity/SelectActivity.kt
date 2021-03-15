package com.dh.madeinbrasil.view.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.dh.madeinbrasil.databinding.ActivitySelectBinding
import com.dh.madeinbrasil.model.gender.GenreSelected
import com.dh.madeinbrasil.viewModel.SelectViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_select.*
import kotlinx.android.synthetic.main.activity_select.view.*

class SelectActivity : AppCompatActivity() {
    var selectedGenres = mutableListOf<String>()
    var genres: String? = ""
    var count = 0
    private lateinit var viewModelSelect: SelectViewModel

    private val db by lazy {
        Firebase.firestore
    }

    private val firebaseAuth by lazy {
        Firebase.auth
    }

    private lateinit var binding: ActivitySelectBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModelSelect = ViewModelProvider(this).get(SelectViewModel::class.java)

        binding.tags.setOnSelectListener {
            count++
            if(count >= 2) {
                binding.btContinueGender.isEnabled = true
            }
        }

        binding.btContinueGender.setOnClickListener {
            val selectedButtons = binding.tags.selectedButtons
            val listGenres = mutableListOf<String>()
            selectedGenres.clear()
            selectedButtons.forEach {
                selectedGenres.add("${it.tag} ,")
                listGenres.add(it.tag.toString())
                genres+="${it.tag} ,"
            }
            registerDB(listGenres)
            startMenuActivity(this@SelectActivity)
        }

    }

//    override fun onResume() {
//        super.onResume()
//        firebaseAuth.currentUser?.let {
//            val documentReference = db.collection(FIREBASE_COLLECTION_USERS).document(it.uid)
//            documentReference.get()
//                    .addOnSuccessListener { snapshot ->
//
//                    }.addOnFailureListener {
//                        Toast.makeText(this, it.localizedMessage, Toast.LENGTH_SHORT).show()
//                    }
//        } ?: run {
//            signIn()
//        }
//    }
//
//    private fun signIn() {
//        startActivity(Intent(this, SplashActivity::class.java))
//        finish()
//    }

    private fun startMenuActivity(context: Context) {
        val intent = Intent(context, MenuActivity::class.java)
        intent.putExtra("genreList", GenreSelected(selectedGenres))
        startActivity(intent)

    }


    private fun registerDB(genre: MutableList<String>?) {
//        val genres = hashMapOf<String, String?>(
//            "genresSelected" to genre
//        )
        genre?.let {
            viewModelSelect.setUserGenres(it)
        }
//        firebaseAuth.currentUser?.let { user->
//            val userData = hashMapOf(
//                    "name" to (user.displayName ?: ""),
//                    "email" to (user.email ?: ""),
//                    "phone" to (user.phoneNumber ?: ""),
//                    "genresSelected" to (genres ?: "")
//            )
//
//            db.collection(FIREBASE_COLLECTION_USERS)
//                    .document(user.uid ?: "")
//                    .set(userData)
//                    .addOnSuccessListener {
//                        Toast.makeText(this, "Dados salvos com sucesso", Toast.LENGTH_SHORT).show()
//                    }
//                    .addOnFailureListener {
//                        Toast.makeText(this, it.localizedMessage, Toast.LENGTH_SHORT).show()
//                    }
//        }
    }

    companion object {
        private const val FIREBASE_COLLECTION_USERS = "users"
    }



}