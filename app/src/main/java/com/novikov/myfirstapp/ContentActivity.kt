package com.novikov.myfirstapp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class ContentActivity : AppCompatActivity() {

    private lateinit var sharedPrefHelper: SharedPrefHelper
    private lateinit var textWelcome: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content)

        sharedPrefHelper = SharedPrefHelper(this)
        textWelcome = findViewById(R.id.text_welcome)

        val identifier = sharedPrefHelper.getUserIdentifier()
        val welcomeText = if (sharedPrefHelper.isPhoneMode()) {
            "Добро пожаловать!\nВаш номер: $identifier"
        } else {
            "Добро пожаловать!\nВаш email: $identifier"
        }
        textWelcome.text = welcomeText

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.setupWithNavController(navController)
    }
}