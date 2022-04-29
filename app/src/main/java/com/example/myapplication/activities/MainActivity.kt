package com.example.myapplication.activities

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.fragments.mainFragment.MapFragment
import com.example.myapplication.fragments.mainFragment.TasksListFragment
import com.example.myapplication.fragments.mainFragment.SettingsFragment
import com.example.myapplication.utils.LocaleUtils

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    private fun setFragment(frameLayout: Int, fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(frameLayout, fragment)
            .commit()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBottomNav()
        setFragment(R.id.mainFragmentLayout, TasksListFragment.newInstance())
    }

    private fun initBottomNav() {
        val bNav = binding.bNav
        bNav.selectedItemId = R.id.tasks
        bNav.setOnItemSelectedListener {
            when (it.itemId){
                R.id.tasks -> setFragment(R.id.mainFragmentLayout, TasksListFragment.newInstance())
                R.id.map -> setFragment(R.id.mainFragmentLayout, MapFragment.newInstance())
                R.id.profile -> setFragment(R.id.mainFragmentLayout, SettingsFragment.newInstance())
            }
            true
        }

    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase?.let { LocaleUtils.attachBaseContext(it)})
    }


}