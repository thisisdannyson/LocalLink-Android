package com.example.locallink

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.locallink.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.nav_container, HomeScreen())
            .commit()

    }
}