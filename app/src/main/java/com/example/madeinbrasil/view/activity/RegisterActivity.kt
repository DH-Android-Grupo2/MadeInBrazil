package com.example.madeinbrasil.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import com.example.madeinbrasil.R
import com.example.madeinbrasil.databinding.ActivityRegisterBinding
import com.example.madeinbrasil.databinding.ActivitySelectBinding
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
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.coroutines.handleCoroutineException


class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private var isNameOk = false
    private var isEmailOk = false
    private var isPasswordOk = false
    private var isConfirmPasswordOk = false
    private var validationName = false
    private var validationEmail = false
    private var validationPassword = false

    private val callbackManager by lazy {
        CallbackManager.Factory.create()
    }
    private lateinit var registerClient: GoogleSignInClient
    private val RC_REGISTER = 999


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btFaceRegister.setPermissions("email,name")


        textChangeDefault(tietRegisterName, tilRegisterName, R.string.string_name)
        textChangeDefault(tietRegisterEmail, tilRegisterEmail, R.string.string_email)
        textChangeDefault(tietRegisterPassword, tilRegisterPassword, R.string.string_password)
        confirmPassword(tietRegisterConfirmPassword, tietRegisterPassword)

        setActionBar()

        btSaveRegister.setOnClickListener {
            startSelectActivity(this)
        }


        ivArrowBackRegister.setOnClickListener {
            startInitialActivity(this)
        }
        //cadastrar com google
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        binding.btGoogleRegister.setOnClickListener {
            val registerIntent: Intent = registerClient.signInIntent
            startActivityForResult(registerIntent, RC_REGISTER)
        }
        registerClient = GoogleSignIn.getClient(this, gso)

        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult?> {
                override fun onSuccess(registerResult: LoginResult?) {
                    val request = GraphRequest.newMeRequest(
                        registerResult?.accessToken

                    ) { jsonResponse, response ->
                        jsonResponse
                        response
                    }
                    val parameters = Bundle()
                    parameters.putString("fields", "id,name,link, email")
                    request.parameters = parameters
                    request.executeAsync()
                }

                override fun onCancel() {
                    Log.i("teste", "teste")
                    TODO("Not yet implemented")
                }

                override fun onError(exception: FacebookException) {
                    exception
                }
            })
    }

    override fun onResume() {
        super.onResume()
        val account = GoogleSignIn.getLastSignedInAccount(this)
        val accessToken = AccessToken.getCurrentAccessToken()
        val isRegistered = accessToken != null && !accessToken.isExpired

        if (isRegistered) {
            binding.btFaceRegister.isVisible = false
        } else {
            binding.btFaceRegister.setPermissions("email")
        }
    }

    override fun onActivityResult(requestCode: Int,
                                  resultCode: Int,
                                  data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_REGISTER) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            // Signed in successfully, show authenticated UI.
            binding.btGoogleRegister.isVisible = false
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("TAG", "signInResult:failed code=" + e.statusCode)
        }
    }




    private fun setActionBar() {
        supportActionBar?.setIcon(R.drawable.ic_baseline_arrow_back_ios_20)
        supportActionBar?.setTitle(R.string.string_register)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun textChangeDefault(editText: EditText, textInputLayout: TextInputLayout, errorString: Int) {
        editText.doOnTextChanged { text, start, _, count ->
            if (text?.isBlank() == true) {
                textInputLayout.error = getString(R.string.errorMessage, getString(errorString))
                setByTag(editText.tag as String, false)
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
            if (editText.tag == getString(R.string.string_name)) {
                nameLength(count)
            }
            activatingButton()
        }
    }

    private fun confirmPassword(editText1: EditText, editText2: EditText) {
        editText1.doOnTextChanged { text, _, _, _ ->
            if (text.toString() == editText2.text.toString()) {
                setByTag(editText1.tag as String, true)
            } else {
                setByTag(editText1.tag as String, true)
            }
            activatingButton()
        }
    }

    private fun passwordLength(count: Int) {
        if (count >= 5) {
            validationPassword = true
            tilRegisterPassword.isErrorEnabled = false
        } else {
            validationPassword = false
            tilRegisterPassword.error = getString(R.string.validationPassword)

        }
    }

    private fun nameLength(count: Int) {
        if (count >= 2) {
            validationName = true
            tilRegisterName.isErrorEnabled = false
        } else {
            validationName = false
            tilRegisterName.error = getString(R.string.validationName)
        }
    }

    private fun validatingEmail(email: String) {
        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            validationEmail = true
            tilRegisterEmail.isErrorEnabled = false
        } else {
            validationEmail = false
            tilRegisterEmail.error = getString(R.string.validationEmail)
        }
    }

    private fun setByTag(tag: String, isOk: Boolean) {
        when (tag) {
            getString(R.string.string_email) -> isEmailOk = isOk
            getString(R.string.string_password) -> isPasswordOk = isOk
            getString(R.string.string_name) -> isNameOk = isOk
            getString(R.string.string_confirm_password) -> isConfirmPasswordOk = isOk
        }
    }

    private fun activatingButton(): Boolean {
        val isOk: Boolean
        if (isEmailOk && isPasswordOk && validationEmail && isNameOk && isConfirmPasswordOk && validationPassword && validationName) {
            btSaveRegister.isEnabled = true
            isOk = true
        } else {
            isOk = false
            btSaveRegister.isEnabled = false

        }
        return isOk
    }

    private fun startSelectActivity(context: Context){
        val intent = Intent(context, SelectActivity::class.java)
        startActivity(intent)
    }


    private fun startInitialActivity(context: Context) {
        val intent = Intent(context, InitialActivity::class.java)
        startActivity(intent)
    }



}
