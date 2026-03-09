package com.novikov.myfirstapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class LoginActivity : AppCompatActivity() {

    private lateinit var editEmailLogin: EditText
    private lateinit var editPasswordLogin: EditText
    private lateinit var checkRemember: CheckBox
    private lateinit var btnLogin: Button
    private lateinit var textRegisterLink: TextView
    private lateinit var textForgotPassword: TextView

    private lateinit var sharedPrefHelper: SharedPrefHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        sharedPrefHelper = SharedPrefHelper(this)

        editEmailLogin = findViewById(R.id.edit_email_login)
        editPasswordLogin = findViewById(R.id.edit_password_login)
        checkRemember = findViewById(R.id.check_remember)
        btnLogin = findViewById(R.id.btn_login)
        textRegisterLink = findViewById(R.id.text_register_link)
        textForgotPassword = findViewById(R.id.text_forgot_password)

        val identifier = sharedPrefHelper.getUserIdentifier()
        if (identifier.isNotEmpty()) {
            editEmailLogin.setText(identifier)
        }

        btnLogin.setOnClickListener {
            val identifier = editEmailLogin.text.toString().trim()
            val password = editPasswordLogin.text.toString()

            if (identifier.isEmpty() || password.isEmpty()) {
                showToast("Заполните все поля")
                return@setOnClickListener
            }

            if (sharedPrefHelper.checkCredentials(identifier, password)) {
                sharedPrefHelper.setAutoLogin(checkRemember.isChecked)

                val intent = Intent(this, ContentActivity::class.java)
                startActivity(intent)
            } else {
                showToast("Неверный email/телефон или пароль")
            }
        }

        textRegisterLink.setOnClickListener {
            startActivity(Intent(this, RegistrationActivity::class.java))
        }
        textForgotPassword.setOnClickListener {
            showToast("Функция восстановления пароля в разработке")
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}