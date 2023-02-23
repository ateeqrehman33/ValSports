package com.halil.ozel.unsplashexample.ui.main

import android.os.Bundle
import android.os.Handler
import android.text.TextUtils.replace
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.postDelayed
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.halil.ozel.unsplashexample.R
import com.halil.ozel.unsplashexample.databinding.ActivityMainBinding
import com.halil.ozel.unsplashexample.ui.fragment.brackets.BracketsFragment
import com.halil.ozel.unsplashexample.ui.fragment.livematches.LiveFragment
import com.halil.ozel.unsplashexample.ui.fragment.schedule.ScheduleFragment
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.ScheduledThreadPoolExecutor

@AndroidEntryPoint
class MainActivity : AppCompatActivity(){


    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                else->{
                    false
                }

            }
            return@setOnItemSelectedListener true
        }

        navView.setOnItemReselectedListener {

        }



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

}

//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