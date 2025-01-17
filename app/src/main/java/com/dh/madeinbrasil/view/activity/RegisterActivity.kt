package com.dh.madeinbrasil.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.dh.madeinbrasil.R
import com.dh.madeinbrasil.database.entities.User
import com.dh.madeinbrasil.databinding.ActivityRegisterBinding
import com.dh.madeinbrasil.viewModel.RegisterViewModel
import com.facebook.*

import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_register.*


class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModelRegister: RegisterViewModel
    private var isNameOk = false
    private var isEmailOk = false
    private var isPasswordOk = false
    private var isConfirmPasswordOk = false
    private var validationName = true
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

        viewModelRegister = ViewModelProvider(this).get(RegisterViewModel::class.java)

        textChangeDefault(tietRegisterName, tilRegisterName, R.string.string_name)
        textChangeDefault(tietRegisterEmail, tilRegisterEmail, R.string.string_email)
        textChangeDefault(tietRegisterPassword, tilRegisterPassword, R.string.string_password)
        confirmPassword(tietRegisterConfirmPassword, tietRegisterPassword)

        setActionBar()

        binding.btSaveRegister.setOnClickListener {
            createNewUser(binding.tietRegisterEmail.text.toString(), binding.tietRegisterPassword.text.toString())
//            CoroutineScope(Dispatchers.IO).launch {
//                val userDao = MadeInBrazilDatabase.getDatabase(this@RegisterActivity).userDao()
//                val usersInDatabase = userDao.getAllUsers()
//                val newUser = Users(0,tietRegisterName.text.toString(),tietRegisterEmail.text.toString(),tietRegisterPassword.text.toString(),"PICTURE")
//
//                if(!usersInDatabase.contains(newUser)) {
//                    userDao.insertUser(newUser)
//                    startSelectActivity(this@RegisterActivity)
//                }else {
//
//                }
//            }
        }

        binding.ivArrowBackRegister.setOnClickListener {
            startInitialActivity(this)
        }

    }

    override fun onStart() {
        super.onStart()
        viewModelRegister.register.observe(this) {
            updateUi(it)
        }
    }
//    override fun onResume() {
//        super.onResume()
//        val account = GoogleSignIn.getLastSignedInAccount(this)
//        val accessToken = AccessToken.getCurrentAccessToken()
//        val isRegistered = accessToken != null && !accessToken.isExpired
//
//    }

    private fun createNewUser(email: String, password: String) {
        val user = User(binding.tietRegisterEmail.text.toString(), binding.tietRegisterName.text.toString(), mutableListOf(), mutableListOf(), "", mutableListOf(),0)

        viewModelRegister.createNewUser(email, password, user,this)
        viewModelRegister.register.observe(this) {
            updateUi(it)
        }
    }

    private fun updateUi(user: FirebaseUser?) {
        if(user != null) {
            startSelectActivity(this)
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
        if (count >= 6) {
            validationPassword = true
            tilRegisterPassword.isErrorEnabled = false
        } else {
            validationPassword = false
            tilRegisterPassword.error = getString(R.string.validationPassword)

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
        var isOk: Boolean
        if (isEmailOk && isPasswordOk && validationEmail && isNameOk && isConfirmPasswordOk && validationPassword && validationName) {
            btSaveRegister.isEnabled = true
            isOk = true
        } else {
            isOk = false
            btSaveRegister.isEnabled = false

        }
        isOk = true
        btSaveRegister.isEnabled = true
        return isOk
    }

    private fun startSelectActivity(context: Context){
        val intent = Intent(context, SelectActivity::class.java)
        startActivity(intent)
        finish()
    }


    private fun startInitialActivity(context: Context) {
        val intent = Intent(context, InitialActivity::class.java)
        startActivity(intent)
        finish()
    }
}
