package com.lopez.myapplication

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        // Display List_Fragment by default when the app opens
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, List_Fragment())
                .commit()
        }


        // Set up button click listener to display FragmentCalculator
        val iconCalculator = findViewById<Button>(R.id.iconCalculator)
        iconCalculator.setOnClickListener { v: View? ->
            // Perform the fragment transaction to replace the container with FragmentCalculator
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, Calculator_Fragment())
                .addToBackStack(null) // Allows back navigation
                .commit() // Execute the transaction
        }


        // Set up button click listener to display FragmentList
        val iconList = findViewById<Button>(R.id.iconList)
        iconList.setOnClickListener { v: View? ->
            // Perform the fragment transaction to replace the container with FragmentList
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, List_Fragment())
                .addToBackStack(null) // Allows back navigation
                .commit() // Execute the transaction
        }


        // Set up button click listener to display FragmentProfile
        val iconProfile = findViewById<Button>(R.id.iconProfile)
        iconProfile.setOnClickListener { v: View? ->
            // Perform the fragment transaction to replace the container with FragmentProfile
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ProfileFragment())
                .addToBackStack(null) // Allows back navigation
                .commit() // Execute the transaction
        }
    }
}