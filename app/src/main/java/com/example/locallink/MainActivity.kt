package com.example.locallink

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.locallink.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bottomNav = findViewById(R.id.bottomNavigationView)
        bottomNav.visibility = View.INVISIBLE
        BottomNav.bottomNav = bottomNav

        bottomNav.setOnItemReselectedListener {
            if (it.itemId == R.id.menu_map && BottomNav.mapFromSearchResults) {
                BottomNav.mapFromSearchResults = false
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.nav_container, MapScreen(bottomNav))
                    .commit()
            }
        }
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_profile -> supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.nav_container, ProfileScreen(bottomNav))
                    .commit()

                R.id.menu_map -> supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.nav_container, MapScreen(bottomNav))
                    .commit()

                R.id.menu_chat -> supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.nav_container, ChatScreen(bottomNav))
                    .commit()

            }
            true
        }

        supportActionBar?.hide()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.nav_container, HomeScreen(bottomNav))
            .commit()

    }
}

class BottomNav {
    companion object {
        var mapFromSearchResults: Boolean = false
        lateinit var bottomNav: BottomNavigationView
    }
}