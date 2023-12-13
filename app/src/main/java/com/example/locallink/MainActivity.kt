package com.example.locallink

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.locallink.databinding.ActivityMainBinding
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.plugin.Plugin
import java.util.prefs.Preferences

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.nav_container, HomeScreen())
            .commit()

    }
}