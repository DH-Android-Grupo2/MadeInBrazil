package com.example.madeinbrasil.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns.EMAIL_ADDRESS
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.example.madeinbrasil.R
import com.example.madeinbrasil.database.MadeInBrazilDatabase
import com.example.madeinbrasil.database.entities.User
import com.example.madeinbrasil.databinding.ActivityLogInBinding
import com.example.madeinbrasil.viewModel.LogInViewModel
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_log_in.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.HashMap

class LogInActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLogInBinding
    private lateinit var viewModelLogin: LogInViewModel
    private var isEmailOk = false
    private var isPasswordOk = false
    private var validationEmail = false
    private var validationPassword = false
    private val callbackManager by lazy {
        CallbackManager.Factory.create()
    }
    private lateinit var signInClient: GoogleSignInClient
    private val RC_SIGN_IN = 999

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModelLogin = ViewModelProvider(this).get(LogInViewModel::class.java)

        textChangeDefault(tietLoginEmail, tilLoginEmail, R.string.string_email)
        textChangeDefault(tietLoginPassword, tilLoginPassword, R.string.string_password)

        binding.btSaveLogin.setOnClickListener {
            signInAuthentication(binding.tietLoginEmail.text.toString(), binding.tietLoginPassword.text.toString())
        }

        binding.ivArrowBackLogin.setOnClickListener {
            startInitialActivity(this)
        }

        //login google
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        signInClient = GoogleSignIn.getClient(this, gso)

        binding.btGoogleLogin.setOnClickListener {
            val signInIntent: Intent = signInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }

        binding.btFaceLogin.isEnabled = true
        binding.btFaceLogin.setOnClickListener {
            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));
            LoginManager.getInstance().registerCallback(callbackManager, object: FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult?) {
                    if (loginResult != null) {
                        val request = GraphRequest.newMeRequest(loginResult.accessToken)
                        { jsonResponse, response ->
                            val url = response.jsonObject
                                .getJSONObject("picture")
                                .getJSONObject("data")
                                .getString("url")
                            val user = User(
                                    jsonResponse.getString("email"), jsonResponse.getString("name"),
                                    mutableListOf(), mutableListOf(), url, mutableListOf(),0)
                            firebaseAuthWithfacebook(loginResult.accessToken, user)
                        }
                        val parameters = Bundle()
                        parameters.putString("fields", "id,name,link,email,picture.type(large)")
                        request.parameters = parameters
                        request.executeAsync()
                    } else {
                        updateUI(null)
                    }
                }
                override fun onCancel() {
                    updateUI(null)
                }
                override fun onError(error: FacebookException?) {
                    updateUI(null)
                }
            })
        }
    }

    override fun onStart() {
        super.onStart()
        viewModelLogin.login.observe(this) {
            updateUI(it)
        }
    }

//    override fun onResume() {
//        super.onResume()
//        val account = GoogleSignIn.getLastSignedInAccount(this)
//
//        val accessToken = AccessToken.getCurrentAccessToken()
//        var isLoggedIn: Boolean = false
//        account?.let {
//            isLoggedIn = accessToken != null && !accessToken.isExpired || account.isExpired
//        }
//
//        if (isLoggedIn) {
//            startMenuActivity(this)
//        }
//    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                account?.idToken?.let {
                    val user = User(account.email.toString(), account.displayName.toString(),
                            mutableListOf(), mutableListOf(), account.photoUrl.toString(), mutableListOf(), 0)

                    firebaseAuthWithGoogle(it, user)
                }?: run {
                    firebaseAuthWithGoogle(null, null)
                }
            } catch (e: ApiException) {
                e.statusCode
            }
        }
    }

    private fun firebaseAuthWithfacebook(idToken: AccessToken?, user: User) {
        if (idToken != null) {
            viewModelLogin.logInWithFacebook(idToken)
        }
        viewModelLogin.loginSocial.observe(this) {
            it?.let {
                if(!it.exists()) {
                    viewModelLogin.setLogin(user)
                    updateUIFromSocialMidia(it.id)
                }else {
                    updateUI(it.id)
                }
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String?, user: User?) {
        idToken?.let {
            viewModelLogin.logInWithGoogle(it)
        }
        viewModelLogin.loginSocial.observe(this) {
            it?.let {
                if(!it.exists()) {
                    user?.let {
                        viewModelLogin.setLogin(user)
                    }
                    updateUIFromSocialMidia(it.id)
                }else {
                    updateUI(it.id)
                }
            }
        }
//        viewModelLogin.login.observe(this) {
//            it?.let {
//                updateUIFromSocialMidia(it)
//            }?: run {
//                updateUI(it)
//            }
//        }
    }

    private fun signInAuthentication(email: String, password: String) {
        if(!activatingButton()){
            return
        }

        viewModelLogin.signInAuthentication(email, password,this)
        viewModelLogin.login.observe(this) {
            updateUI(it)
        }
    }

//    private fun updateUI(user: FirebaseUser?): Boolean {
//        return if(user != null) {
//            startMenuActivity(this)
//            true
//        }else {
//            false
//        }
//    }

    private fun updateUI(user: String?): Boolean {
        return if(user != null) {
            startMenuActivity(this)
            true
        }else {
            false
        }
    }

//    private fun updateUIFromSocialMidia(user: FirebaseUser?) {
//        if(user != null) {
//            startSelectActivity(this)
//        }
//    }
    private fun updateUIFromSocialMidia(user: String?) {
        if(user != null) {
            startSelectActivity(this)
        }
    }

    private fun textChangeDefault(
        editText: EditText,
        textInputLayout: TextInputLayout,
        errorString: Int
    ) {
        editText.doOnTextChanged { text, start, _, _ ->
            if (text?.isBlank() == true) {
                textInputLayout.error = getString(R.string.errorMessage, getString(errorString))
            } else {
                textInputLayout.isErrorEnabled = false
                setByTag(editText.tag as String, true)
            }

            if (editText.tag == getString(R.string.string_email)) {
                validatingEmail(text.toString())
            }
            if (editText.tag == getString(R.string.string_password)) {
                passwordLength(start + 1)
            }
            activatingButton()
        }
    }

    private fun validatingEmail(email: String) {
        if (EMAIL_ADDRESS.matcher(email).matches()) {
            validationEmail = true
            tilLoginEmail.isErrorEnabled = false
        } else {
            validationEmail = false
            tilLoginEmail.error = getString(R.string.validationEmail)
        }
    }


    private fun passwordLength(count: Int) {
        if (count >= 6) {
            validationPassword = true
            tilLoginPassword.isErrorEnabled = false
        } else {
            validationPassword = false
            tilLoginPassword.error = getString(R.string.validationPassword)

        }
    }

    private fun setByTag(tag: String, isOk: Boolean) {
        when (tag) {
            getString(R.string.string_email) -> isEmailOk = isOk
            getString(R.string.string_password) -> isPasswordOk = isOk
        }
    }

    private fun activatingButton(): Boolean {
        val isOk: Boolean
        if (isEmailOk && isPasswordOk && validationEmail && validationPassword) {
            btSaveLogin.isEnabled = true
            isOk = true
        } else {
            isOk = false
            btSaveLogin.isEnabled = false
        }
        return isOk
    }

    private fun startSelectActivity(context: Context) {
        val intent = Intent(context, SelectActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun startMenuActivity(context: Context) {
        val intent = Intent(context, MenuActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun startInitialActivity(context: Context) {
        val intent = Intent(context, InitialActivity::class.java)
        startActivity(intent)
        finish()
    }

}


