package com.dh.madeinbrasil.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dh.madeinbrasil.databinding.ActivityInitialBinding
//import com.firebase.ui.auth.AuthMethodPickerLayout
//import com.firebase.ui.auth.AuthUI
//import com.firebase.ui.auth.IdpResponse
//import com.firebase.ui.auth.IdpResponse.fromResultIntent

class InitialActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInitialBinding

//    private val providers by lazy {
//        arrayListOf(
//            AuthUI.IdpConfig.EmailBuilder().build(),
//            AuthUI.IdpConfig.GoogleBuilder().build()
//        )
//    }
//
//    private val firebaseAuth by lazy {
//        Firebase.auth
//    }
//    private val db by lazy {
//        Firebase.firestore
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInitialBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btLoginInitial.setOnClickListener {
            val intent = Intent(this, LogInActivity::class.java)
            startActivity(intent)
        }

        binding.btRegisterInitial.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

//        val customLayout = AuthMethodPickerLayout.Builder(R.layout.activity_initial)
//            .setEmailButtonId(R.id.btLoginInitial)
//            .setGoogleButtonId(R.id.btGoogleInitial)
//            .build()
//
//        startActivityForResult(
//            AuthUI.getInstance()
//                .createSignInIntentBuilder()
//                .setTheme(R.style.AppTheme)
//                .setAuthMethodPickerLayout(customLayout)
//                .setAvailableProviders(providers)
//                .setIsSmartLockEnabled(false)
//                .build(),
//            RC_SIGN_IN)

    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        var selectedGenres = mutableListOf<String>()
//        if (requestCode == RC_SIGN_IN) {
//            if (resultCode == Activity.RESULT_OK) {
//                val user = firebaseAuth.currentUser
//                val intentFirstTime = Intent(this, SelectActivity::class.java)
//                val intent = Intent(this, MenuActivity::class.java)
//                user?.let{ it ->
//                    val documentReference = db.collection(FIREBASE_COLLECTION_USERS).document(user.uid)
//                    documentReference.get()
//                            .addOnSuccessListener { snapshot ->
//                                snapshot?.data?.let {dataSnapshot->
//                                    startActivity(intent)
//                                    finish()
//                                } ?: run {
//                                    startActivity(intentFirstTime)
//                                    finish()
//                                }
//                            }
//                            .addOnFailureListener {
//                                Toast.makeText(this, it.localizedMessage, Toast.LENGTH_SHORT).show()
//                            }
//                    //finish()
//                }
//
//
//            } else {
//                Toast.makeText(this,"Login não foi bem Sucedido",Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
//
//    companion object {
//        private const val RC_SIGN_IN = 999
//        private const val FIREBASE_COLLECTION_USERS = "users"
//    }
//
//    fun usuarioOk(){
//
//    }
}

