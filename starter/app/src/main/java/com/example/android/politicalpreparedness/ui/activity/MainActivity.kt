package com.example.android.politicalpreparedness.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.android.politicalpreparedness.R

class MainActivity : AppCompatActivity() {
    private val navHostFragment by lazy { supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navController = navHostFragment.navController

        this.supportActionBar!!.title=getString(R.string.app_name)
        navController.addOnDestinationChangedListener{controller, destination, arguments ->
            title = when (destination.id) {
                R.id.voterInfoFragment -> "My title"
                else -> "Default title"
            }
        }
    }
}
