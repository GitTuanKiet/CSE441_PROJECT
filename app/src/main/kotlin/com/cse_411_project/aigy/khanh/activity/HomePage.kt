package com.cse_411_project.aigy.khanh.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import android.content.Context
import android.util.Log

import androidx.viewpager2.widget.ViewPager2
import com.cse_411_project.aigy.R
import com.cse_411_project.aigy.adapter.ViewPageAdapter

import com.cse_411_project.aigy.khanh.fragment.Tab1
import com.cse_411_project.aigy.khanh.fragment.Tab2
import com.cse_411_project.aigy.khanh.fragment.Tab3
import com.cse_411_project.aigy.khanh.fragment.Tab4
import com.cse_411_project.aigy.khanh.fragment.Tab5
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HomePage : AppCompatActivity(){
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var  viewAdapter : ViewPageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_page)
        Mapping()
        getEvent()
    }

    private fun getEvent() {
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager.currentItem = tab!!.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                tabLayout.getTabAt(position)?.select()
            }
        })
    }

    private fun Mapping() {
        tabLayout = findViewById(R.id.TabLayout)
        viewPager = findViewById(R.id.ViewPager)

        val fragmentList = arrayListOf<Fragment>(
            Tab1(),
            Tab2(),
            Tab3(),
            Tab4(),
            Tab5()
        )

        viewAdapter = ViewPageAdapter(fragmentList, supportFragmentManager, lifecycle)
        viewPager.adapter = viewAdapter
    }


    companion object {
        fun callingIntent(context: Context) = Intent(context, HomePage::class.java)
    }
}