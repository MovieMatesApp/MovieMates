package com.example.moviemates

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        replaceFragment(FavoritesFragment())
        
        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottomNavigation)

        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    replaceFragment(MoviesFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.nav_favorites -> {
                    replaceFragment(FavoritesFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.nav_account -> {
                    replaceFragment(AccountFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                else -> false
            }
        }


    }
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

}