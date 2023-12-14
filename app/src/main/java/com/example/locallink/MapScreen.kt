package com.example.locallink

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import com.example.locallink.CreatingAccount.Biography
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mapbox.geojson.Point
import com.mapbox.maps.MapView
import com.mapbox.maps.plugin.annotation.AnnotationOptions
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.OnPointAnnotationClickListener
import com.mapbox.maps.plugin.annotation.generated.PointAnnotation
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.mapbox.maps.viewannotation.viewAnnotationOptions
import java.util.HashSet

class MapScreen(private val bottomNav: BottomNavigationView) : Fragment() {
    private lateinit var coordToBuildingNamePairs: List<Pair<String,Point>>
    private lateinit var options: List<PointAnnotationOptions>
    private var buildingSet: MutableSet<String> = HashSet()// set of strings to determine whether we
    // should have locations enabled before loading map -> preserved data
    private lateinit var mapView: MapView
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    private lateinit var searchButton: Button
    private lateinit var clearButton: Button
    private lateinit var addAllButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bottomNav.visibility = View.VISIBLE
        bottomNav.selectedItemId = R.id.menu_map
        sharedPreferences = context?.getSharedPreferences("myPref", Context.MODE_PRIVATE)!!
        editor = sharedPreferences.edit()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_map_screen, container, false)
        mapView = view.findViewById(R.id.mapViewScreen)
        val annotationApi = mapView.annotations
        val pointAnnotationManager = annotationApi.createPointAnnotationManager()


        searchButton = view.findViewById(R.id.mapView_endSearchButton)
        clearButton = view.findViewById(R.id.mapView_resetSearch)
        addAllButton = view.findViewById(R.id.mapView_addAllSearchButton)

        clearButton.setOnClickListener {
            clearBuildings()
        }

        addAllButton.setOnClickListener {
           addAllBuildings()
        }
        pointAnnotationManager.addClickListener(OnPointAnnotationClickListener { annotation ->
            val name =  annotation.textField.toString()
            Log.i("testing", "name building is $name")
            displayPopup(name)
            true
        })


        searchButton.setOnClickListener {
            UserDatabase.init(buildingSet)
            fragmentManager?.beginTransaction()
                ?.replace(R.id.nav_container, SearchResults())
                ?.commit()
        }
        Log.i("user", "size of user database: ${UserDatabase.users.size}")

        val icon: Bitmap = convertDrawableToBitMap(resources.getDrawable(R.drawable.ic_building))!!
        coordToBuildingNamePairs = generatePairs()
        options = generateOptions(coordToBuildingNamePairs, icon)

        pointAnnotationManager.create(options)
        generateSet()
        checkToViewButton()
        return view
    }

    private fun clearBuildings() {
        for (pair in coordToBuildingNamePairs) {
            editor.remove(pair.first)
        }
        editor.commit()
        buildingSet.clear()
        searchButton.visibility = View.INVISIBLE
    }

    private fun addAllBuildings() {
        for (pair in coordToBuildingNamePairs) {
            buildingSet.add(pair.first)
            editor.putBoolean(pair.first, true)
        }
        editor.commit()
        enableSearchButton()
    }

    private fun checkToViewButton() {
        if (buildingSet.size >= 1) {
            enableSearchButton()
            searchButton.visibility = View.VISIBLE
        } else {
            searchButton.visibility = View.INVISIBLE
        }
    }

    private fun enableSearchButton() {
        searchButton.visibility = View.VISIBLE
        var searchString = "Search ${buildingSet.size} Building"
        if (buildingSet.size > 1) {
            searchString += "s"
        }
        searchButton.text = searchString
    }

    private fun displayPopup(name: String) {
        val builder = AlertDialog.Builder(context)
        builder.apply {
            val customLayout: View = layoutInflater.inflate(R.layout.building_popup, null)
            val buildingName: TextView = customLayout.findViewById(R.id.building_popup_building_name)
            buildingName.text = name
            val enabledSwitch: SwitchCompat = customLayout.findViewById(R.id.building_popup_switch)
            enabledSwitch.isChecked = buildingSet.contains(name)
            setView(customLayout)
            setOnDismissListener {
                if (enabledSwitch.isChecked) {
                    buildingSet.add(name)
                    editor.putBoolean(name, true)
                    checkToViewButton()

                } else {
                    buildingSet.remove(name)
                    editor.remove(name)
                    checkToViewButton()
                }
                editor.commit()
            }
            setPositiveButton("OK") { _,_ ->
                if (enabledSwitch.isChecked) {
                    buildingSet.add(name)
                    editor.putBoolean(name, true)
                    checkToViewButton()

                } else {
                    buildingSet.remove(name)
                    editor.remove(name)
                    checkToViewButton()
                }
                editor.commit()
            }
        }
        builder.show()
    }

    private fun generateSet() {
        for (pair in coordToBuildingNamePairs) {
            if (sharedPreferences.contains(pair.first)) {
                Log.i("mapGenerate", "adding ${pair.first} to set!")
                buildingSet.add(pair.first)
            }
        }
    }

    private fun generatePairs(): List<Pair<String,Point>> {
        var res: List<Pair<String,Point>> = listOf(
            Pair("Hayden Hall", Point.fromLngLat(-71.088470, 42.339269)),
            Pair("Forsyth Building", Point.fromLngLat(-71.089581, 42.338569)),
            Pair("Ryder Hall", Point.fromLngLat(-71.090626, 42.336590)),
            Pair("Richards Hall", Point.fromLngLat(-71.088795,42.339873)),
            Pair("ISEC", Point.fromLngLat(-71.086942, 42.337695)),
            Pair("Snell Library", Point.fromLngLat(-71.087972,42.338412)),
            Pair("Ell Hall", Point.fromLngLat(-71.088074, 42.339752)),
            Pair("Shillman Hall", Point.fromLngLat(-71.090225, 42.337581)),
            Pair("Snell Engineering Center", Point.fromLngLat(-71.088840, 42.338283)),
            Pair("Lake Hall", Point.fromLngLat(-71.090726, 42.338202)),
            Pair("Nightingale Hall", Point.fromLngLat(-71.090128,42.338129)),
            Pair("Meserve Hall" , Point.fromLngLat(-71.09086393160773, 42.337653988383835)),
            Pair("Behrakis Health Science Center", Point.fromLngLat(-71.09152151335596, 42.33695568964913)),
            Pair("Cargill Hall", Point.fromLngLat(-71.09160548796162, 42.338952474689165)),
            Pair("Stearns Center", Point.fromLngLat(-71.09139282195547, 42.33904000480399)),
            Pair("Kariotis Hall", Point.fromLngLat(-71.09087365147718, 42.338647343614554)),
            Pair("Cabot Physical Education Center", Point.fromLngLat(-71.08971301218136, 42.33941142175959)),
            Pair("Dodge Hall", Point.fromLngLat(-71.08777378457647, 42.34021090057888)),
            Pair("Mugar Life Sciences Building", Point.fromLngLat( -71.08712611991956, 42.33980005153151)),
            Pair("West Village F", Point.fromLngLat(-71.09149353103591, 42.337552752335085)),
            Pair("Robinson Hall", Point.fromLngLat( -71.08670863877933, 42.339292305451806)),
            Pair("Cullinane Hall", Point.fromLngLat(-71.08653222265981, 42.34010686230749)),
            Pair("Hurtig Hall", Point.fromLngLat(-71.08616247380738, 42.3397924731928)),

        )
        return res
    }

    private fun generateOptions(listPairs: List<Pair<String, Point>>, icon: Bitmap): List<PointAnnotationOptions> {
        val res: MutableList<PointAnnotationOptions> = mutableListOf()
        for (pair in listPairs) {
            val pointAnnotationOption: PointAnnotationOptions = PointAnnotationOptions()
                .withPoint(pair.second)
                .withIconImage(icon)
                .withTextField(pair.first)
                .withTextColor(Color.BLACK)
                .withTextSize(12.0)
                .withTextOffset(listOf(0.0, -3.0))
            res.add(pointAnnotationOption)
        }
        return res
    }

    private fun convertDrawableToBitMap(drawable: Drawable): Bitmap? {
        return if (drawable is BitmapDrawable) {
            drawable.bitmap
        } else {
            val constantState = drawable.constantState ?: return null
            val drawable = constantState.newDrawable().mutate()
            val bitmap: Bitmap = Bitmap.createBitmap(
                drawable.intrinsicWidth + 15, drawable.intrinsicHeight + 15,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            bitmap
        }
    }
}