package com.attitude.designs.valtrackr.ui.main

import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.postDelayed
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.attitude.designs.valtrackr.R
import com.attitude.designs.valtrackr.databinding.ActivityMainBinding
import com.attitude.designs.valtrackr.ui.fragment.aboutdev.AboutDevFragment
import com.attitude.designs.valtrackr.ui.fragment.brackets.BracketsFragment
import com.attitude.designs.valtrackr.ui.fragment.livematches.LiveFragment
import com.attitude.designs.valtrackr.ui.fragment.news.NewsFragment
import com.attitude.designs.valtrackr.ui.fragment.newsdetails.WebViewFragment
import com.attitude.designs.valtrackr.ui.fragment.schedule.ScheduleFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity(){


    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()


        setView()
    }

    private fun setView() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragNavHost) as NavHostFragment
        navController = navHostFragment.navController
        val navView: BottomNavigationView = findViewById(R.id.bottomNavView)
        navView.setupWithNavController(navController)

        navView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.live_fragment -> {
                    setCurrentFragment(LiveFragment())
                    true
                }
                R.id.schedule_fragment-> {
                    setCurrentFragment(ScheduleFragment())
                    true
                }
                R.id.bracket_fragment-> {
                    setCurrentFragment(BracketsFragment())
                    true
                }

                R.id.about_dev-> {
                    setCurrentFragment(AboutDevFragment())
                    true
                }
                R.id.news_fragment-> {
                    setCurrentFragment(NewsFragment())
                    true
                }
                R.id.webview_fragment-> {
                    setCurrentFragment(WebViewFragment())
                    true
                }

                else->{
                    false
                }

            }
            return@setOnItemSelectedListener true
        }

        navView.setOnItemReselectedListener {

        }


        AppCenter.start(
            application, "d66153b1-65b7-4c7c-aa72-e636f48e0a0a",
            Analytics::class.java, Crashes::class.java
        )


    }


    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragNavHost, fragment)
            commit()
        }


    private var backPressedOnce = false

    override fun onBackPressed() {
        if (navController.graph.startDestinationId == navController.currentDestination?.id)
        {
            if (backPressedOnce)
            {
                super.onBackPressed()
                return
            }

            backPressedOnce = true
            Toast.makeText(this, "Press BACK again to exit", Toast.LENGTH_SHORT).show()

            Handler().postDelayed(2000) {
                backPressedOnce = false
            }
        }
        else {
            super.onBackPressed()
        }
    }

    fun setBottomNavigationVisibility(visibility: Int) {
        // get the reference of the bottomNavigationView and set the visibility.
        binding.bottomNavView.visibility = visibility
    }

}

