package com.example.madeinbrasil.view.activity

import android.app.Activity
import android.content.Intent
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns.EMAIL_ADDRESS
import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import com.example.madeinbrasil.R
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_log_in.*


class LogInActivity : AppCompatActivity() {
    private var isEmailOk = false
    private var isPasswordOk = false
    private var validationEmail = false
    private var validationPassword = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        textChangeDefault(tietLoginEmail, tilLoginEmail, R.string.string_email)
        textChangeDefault(tietLoginPassword, tilLoginPassword, R.string.string_password)

        btSaveLogin.setOnClickListener {
            startSelectActivity(this)
        }

        ivArrowBackLogin.setOnClickListener {
            startInitialActivity(this)
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
        if (count >= 5) {
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
        val intent = Intent(context, SelectActivity::class.java)
        startActivity(intent)
    }

    private fun startInitialActivity(context: Context) {
        val intent = Intent(context, InitialActivity::class.java)
        startActivity(intent)
    }

    }


