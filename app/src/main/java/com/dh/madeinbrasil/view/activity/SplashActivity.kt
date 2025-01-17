package com.dh.madeinbrasil.view.activity

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.dh.madeinbrasil.R
//import com.firebase.ui.auth.AuthMethodPickerLayout
//import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SplashActivity : AppCompatActivity() {
    private val firebaseAuth by lazy {
        Firebase.auth
    }
    private val db by lazy {
        Firebase.firestore
    }
//    private val providers by lazy {
//        arrayListOf(
//            AuthUI.IdpConfig.EmailBuilder().build(),
//            AuthUI.IdpConfig.GoogleBuilder().build(),
//            AuthUI.IdpConfig.FacebookBuilder().build()
//        )
//    }

    override fun onResume() {
        super.onResume()
        firebaseAuth.currentUser?.let { user->
            temBanco(user)
        }?: run {
            changeToLogin()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        var selectedGenres = mutableListOf<String>()

        if (requestCode == RC_SIGN_IN) {
            if (resultCode == Activity.RESULT_OK) {
                val user = firebaseAuth.currentUser
                user?.let{ it ->
                    temBanco(user)
                }
            } else Toast.makeText(this,"Login não foi bem Sucedido",Toast.LENGTH_SHORT).show()
        }
    }

    private fun changeToLogin(){
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, InitialActivity::class.java)
            startActivity(intent)
            finish()
//            val customLayout = AuthMethodPickerLayout.Builder(R.layout.activity_initial)
//                .setEmailButtonId(R.id.btLoginInitial)
////                .setGoogleButtonId(R.id.btGoogleInitial)
////                .setFacebookButtonId(R.id.btFaceInitial)
//                .build()
//
//            startActivityForResult(
//                AuthUI.getInstance()
//                    .createSignInIntentBuilder()
//                    .setTheme(R.style.AppTheme)
//                    .setAuthMethodPickerLayout(customLayout)
//                    .setAvailableProviders(providers)
//                    .setIsSmartLockEnabled(false)
//                    .build(),
//                RC_SIGN_IN
//            )
        }, 1000)
    }

    private fun Intent.change(){
        startActivity(this)
        finish()
    }

    private fun temBanco(user:FirebaseUser){
        val intentFirstTime = Intent(this, SelectActivity::class.java)
        val intent = Intent(this, MenuActivity::class.java)

        val documentReference = db.collection(FIREBASE_COLLECTION_USERS).document(user.uid)
        documentReference.get()
                .addOnSuccessListener { snapshot ->
                    snapshot.data?.let {
                        startActivity(intent)
                        finish()
                    } ?: run {
                        startActivity(intentFirstTime)
                        finish()
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(this, it.localizedMessage, Toast.LENGTH_SHORT).show()
                }
    }

    companion object {
        private const val RC_SIGN_IN = 999
        private const val FIREBASE_COLLECTION_USERS = "users"
    }
}