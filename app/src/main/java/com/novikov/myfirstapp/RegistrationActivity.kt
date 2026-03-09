package com.novikov.myfirstapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import java.util.regex.Pattern

class RegistrationActivity : AppCompatActivity() {

    private lateinit var btnByPhone: Button
    private lateinit var btnByEmail: Button
    private lateinit var editInput: EditText
    private lateinit var editPassword: EditText
    private lateinit var editPasswordRepeat: EditText
    private lateinit var btnRegister: Button

    private var isPhoneMode = true // true-номер, false-email

    companion object {
        private val PHONE_PATTERN = Pattern.compile("^\\+7[0-9]{10}\$")
        private val EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}\$"
        )
    }

    private lateinit var sharedPrefHelper: SharedPrefHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        sharedPrefHelper = SharedPrefHelper(this)

        btnByPhone = findViewById(R.id.btn_by_phone)
        btnByEmail = findViewById(R.id.btn_by_email)
        editInput = findViewById(R.id.edit_registration_input)
        editPassword = findViewById(R.id.edit_password_registration)
        editPasswordRepeat = findViewById(R.id.edit_password_repeat)
        btnRegister = findViewById(R.id.btn_register)

        setPhoneMode()

        btnByPhone.setOnClickListener {
            setPhoneMode()
        }

        btnByEmail.setOnClickListener {
            setEmailMode()
        }

        btnRegister.setOnClickListener {
            validateAndRegister()
        }
    }

    private fun setPhoneMode() {
        isPhoneMode = true
        btnByPhone.setBackgroundColor(ContextCompat.getColor(this, R.color.purple_500))
        btnByPhone.setTextColor(ContextCompat.getColor(this, R.color.white))
        btnByEmail.setBackgroundColor(ContextCompat.getColor(this, R.color.gray_light))
        btnByEmail.setTextColor(ContextCompat.getColor(this, R.color.gray_dark))

        editInput.hint = "Номер телефона (+79123456789)"
        editInput.inputType = android.text.InputType.TYPE_CLASS_PHONE
        editInput.text.clear()
    }

    private fun setEmailMode() {
        isPhoneMode = false
        btnByEmail.setBackgroundColor(ContextCompat.getColor(this, R.color.purple_500))
        btnByEmail.setTextColor(ContextCompat.getColor(this, R.color.white))
        btnByPhone.setBackgroundColor(ContextCompat.getColor(this, R.color.gray_light))
        btnByPhone.setTextColor(ContextCompat.getColor(this, R.color.gray_dark))

        editInput.hint = "Email (user@example.com)"
        editInput.inputType = android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        editInput.text.clear()
    }

    private fun validateAndRegister() {
        val input = editInput.text.toString().trim()
        val password = editPassword.text.toString()
        val passwordRepeat = editPasswordRepeat.text.toString()

        if (isPhoneMode) {
            if (!isValidPhone(input)) {
                showToast("Некорректный номер телефона. Пример: +79123456789")
                return
            }
        } else {
            if (!isValidEmail(input)) {
                showToast("Некорректный email. Пример: user@example.com")
                return
            }
        }

        // Проверка пароля
        if (password.length < 8) {
            showToast("Пароль должен содержать минимум 8 символов")
            return
        }

        // Проверка совпадения паролей
        if (password != passwordRepeat) {
            showToast("Пароли не совпадают")
            return
        }

        sharedPrefHelper.saveUserCredentials(input, password, isPhoneMode)
        showToast("Регистрация успешна!")

        val intent = Intent(this, ContentActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun isValidPhone(phone: String): Boolean {
        return PHONE_PATTERN.matcher(phone).matches()
    }

    private fun isValidEmail(email: String): Boolean {
        return EMAIL_PATTERN.matcher(email).matches()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}