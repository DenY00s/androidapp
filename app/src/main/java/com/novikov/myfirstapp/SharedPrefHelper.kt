package com.novikov.myfirstapp

import android.content.Context
import android.content.SharedPreferences

class SharedPrefHelper(context: Context) {
    private val sharedPref: SharedPreferences = context.getSharedPreferences(
        "user_prefs",
        Context.MODE_PRIVATE
    )

    companion object {
        const val KEY_EMAIL = "user_email"
        const val KEY_PHONE = "user_phone"
        const val KEY_PASSWORD = "user_password"
        const val KEY_IS_PHONE_MODE = "is_phone_mode"
        const val KEY_AUTO_LOGIN = "auto_login"
        const val KEY_IS_REGISTERED = "is_registered"
    }

    fun saveUserCredentials(identifier: String, password: String, isPhoneMode: Boolean) {
        with(sharedPref.edit()) {
            if (isPhoneMode) {
                putString(KEY_PHONE, identifier)
                putString(KEY_EMAIL, "")
            } else {
                putString(KEY_EMAIL, identifier)
                putString(KEY_PHONE, "")
            }
            putString(KEY_PASSWORD, password)
            putBoolean(KEY_IS_PHONE_MODE, isPhoneMode)
            putBoolean(KEY_IS_REGISTERED, true)
            apply()
        }
    }

    fun setAutoLogin(enabled: Boolean) {
        sharedPref.edit().putBoolean(KEY_AUTO_LOGIN, enabled).apply()
    }

    fun getUserIdentifier(): String {
        return if (isPhoneMode()) {
            sharedPref.getString(KEY_PHONE, "") ?: ""
        } else {
            sharedPref.getString(KEY_EMAIL, "") ?: ""
        }
    }

    fun getPassword(): String {
        return sharedPref.getString(KEY_PASSWORD, "") ?: ""
    }

    fun isPhoneMode(): Boolean {
        return sharedPref.getBoolean(KEY_IS_PHONE_MODE, true)
    }

    fun isAutoLoginEnabled(): Boolean {
        return sharedPref.getBoolean(KEY_AUTO_LOGIN, false)
    }

    fun isUserRegistered(): Boolean {
        return sharedPref.getBoolean(KEY_IS_REGISTERED, false)
    }

    fun checkCredentials(identifier: String, password: String): Boolean {
        val savedIdentifier = getUserIdentifier()
        val savedPassword = getPassword()

        return identifier == savedIdentifier && password == savedPassword
    }

    fun clearAll() {
        sharedPref.edit().clear().apply()
    }
}