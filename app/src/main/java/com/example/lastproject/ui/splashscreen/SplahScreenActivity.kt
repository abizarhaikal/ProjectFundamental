package com.example.lastproject.ui.splashscreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.example.lastproject.SettingPreferences
import com.example.lastproject.dataStore
import com.example.lastproject.databinding.ActivitySplahScreenBinding
import com.example.lastproject.ui.MainActivity
import kotlinx.coroutines.launch


class SplahScreenActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySplahScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplahScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val settingPreferences = SettingPreferences.getInstance(application.dataStore)

        lifecycleScope.launch {
            settingPreferences.getThemeSetting().collect { isDarkModeActive ->
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }
        }

        @Suppress("DEPRECATION")
        Handler().postDelayed(Runnable {
            val intent = Intent(
                this@SplahScreenActivity ,
                MainActivity::class.java
            )
            startActivity(intent)
            finish()
        } ,2000)
    }
}