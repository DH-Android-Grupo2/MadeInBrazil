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
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_log_in.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//184980070731-6dpbve1t4asg125d9b69c5qk9tor0n39.apps.googleusercontent.com
//viCv12wGxwi56K9f-IMhJKoM
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
    private var loginNumber = 1
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
            loginNumber = 1
            signInAuthentication(binding.tietLoginEmail.text.toString(), binding.tietLoginPassword.text.toString())
//            CoroutineScope(Dispatchers.IO).launch {
//                val userDao = MadeInBrazilDatabase.getDatabase(this@LogInActivity).userDao()
//                val userInDatabase = userDao.login(tietLoginEmail.text.toString(),tietLoginPassword.text.toString())
//
//                userInDatabase?.let {
//                    startSelectActivity(this@LogInActivity)
//                }
//            }
        }

        binding.ivArrowBackLogin.setOnClickListener {
            startInitialActivity(this)
        }

        //login google
//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestEmail()
//            .build()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        signInClient = GoogleSignIn.getClient(this, gso)

        binding.btGoogleLogin.setOnClickListener {
            loginNumber = 2
            val signInIntent: Intent = signInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }

        binding.btFaceLogin.setOnClickListener {
            LoginManager.getInstance().registerCallback(callbackManager,
                object : FacebookCallback<LoginResult?> {
                    override fun onSuccess(loginResult: LoginResult?) {
                        loginResult?.accessToken?.let { it1 -> firebaseAuthWithfacebook(it1) }
                        val request = GraphRequest.newMeRequest(
                            loginResult?.accessToken
                        ) { jsonResponse, response ->
                            jsonResponse
                            response
                        }
                        val parameters = Bundle()
                        parameters.putString("fields", "id,name,link,email")
                        request.parameters = parameters
                        request.executeAsync()
                    }

                    override fun onCancel(){  }

                    override fun onError(exception: FacebookException) {  }
                })
        }
    }

//    override fun onResume() {
//        super.onResume()
//        val account = GoogleSignIn.getLastSignedInAccount(this)
//
//        val accessToken = AccessToken.getCurrentAccessToken()
//        val isLoggedIn = accessToken != null && !accessToken.isExpired
//
//        if (isLoggedIn) {
//            binding.btFaceLogin.isVisible = false
//        } else {
////            binding.btFaceLogin.setPermissions("email")
//        }
//    }

    override fun onStart() {
        super.onStart()
        viewModelLogin.login.observe(this) {
            updateUI(it)
        }
//        when(loginNumber) {
//            1 -> {
//                viewModelLogin.login.observe(this) {
//                    updateUI(it)
//                }
//            }
//            2 -> {
//                viewModelLogin.loginGoogle.observe(this) {
//                    updateUI(it)
//                }
//            }
//        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == RC_SIGN_IN) {
//            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
//            handleSignInResult(task)
//        }
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                account?.idToken?.let {
                    firebaseAuthWithGoogle(it)
                }?: run {
                    firebaseAuthWithGoogle(null)
                }
            } catch (e: ApiException) {
                e.statusCode
            }
        }else {
            callbackManager.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun firebaseAuthWithfacebook(idToken: AccessToken) {
        viewModelLogin.logInWithFacebook(idToken)
        viewModelLogin.login.observe(this) {
            updateUI(it)
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String?) {
        viewModelLogin.logInWithGoogle(idToken)
        viewModelLogin.login.observe(this) {
            updateUI(it)
        }
//        viewModelLogin.loginGoogle.observe(this) {
//            updateUI(it)
//        }
    }

//    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
//        try {
//            val account = completedTask.getResult(ApiException::class.java)
//            // Signed in successfully, show authenticated UI.
//            binding.btGoogleLogin.isVisible = false
//        } catch (e: ApiException) {
//            // The ApiException status code indicates the detailed failure reason.
//            // Please refer to the GoogleSignInStatusCodes class reference for more information.
//            Log.w("TAG", "signInResult:failed code=" + e.statusCode)
//        }
//    }

    private fun signInAuthentication(email: String, password: String) {
        if(!activatingButton()){
            return
        }

        viewModelLogin.signInAuthentication(email, password)
        viewModelLogin.login.observe(this) {
            updateUI(it)
        }
    }

    private fun updateUI(user: FirebaseUser?) {
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

    private fun setByTag(tag: String,
                         isOk: Boolean) {
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
        val intent = Intent(context, MenuActivity::class.java)
        startActivity(intent)
        finish()
//        val intent = Intent(context, SelectActivity::class.java)
//        startActivity(intent)
//        finish()
    }

    private fun startInitialActivity(context: Context) {
        val intent = Intent(context, InitialActivity::class.java)
        startActivity(intent)
        finish()
    }

}


