package com.example.madeinbrasil.view.activity

import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.content.Intent
import android.os.Handler
import android.widget.Toast
import com.example.madeinbrasil.R
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

    override fun onResume() {
        super.onResume()
        firebaseAuth.currentUser?.let {
            val documentReference = db.collection(FIREBASE_COLLECTION_USERS).document(it.uid)
            documentReference.get()
                .addOnSuccessListener { snapshot ->
                    changeToMain()
                }.addOnFailureListener {
                    Toast.makeText(this, it.localizedMessage, Toast.LENGTH_SHORT).show()
                }
        }?: run {
            changeToLogin()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    private fun changeToLogin(){
        val intent = Intent(this, InitialActivity::class.java)
        Handler().postDelayed({
            intent.change()
        }, 2000)
    }

    private fun changeToMain(){
        val intent = Intent(this, MenuActivity::class.java)
        Handler().postDelayed({
            intent.change()
        }, 2000)
    }

    private fun Intent.change(){
        startActivity(this)
        finish()

    }

    companion object {
        private const val FIREBASE_COLLECTION_USERS = "users"
    }
}