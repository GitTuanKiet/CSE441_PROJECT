package com.cse_411_project.aigy.khanh.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import com.cse_411_project.aigy.R
import com.cse_411_project.aigy.adapter.ViewPageAdapter
import com.cse_411_project.aigy.khanh.screen.FirstScreen
import com.cse_411_project.aigy.screen.SecondScreen
import com.cse_411_project.aigy.screen.ThirdScreen
import com.cse_411_project.aigy.khanh.activity.WelcomeActivity
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator

class OnBoardingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_on_boarding, container, false)

        val fragmentList = arrayListOf(
            FirstScreen(),
            SecondScreen(),
            ThirdScreen()
        )

        val adapter = ViewPageAdapter(fragmentList, requireActivity().supportFragmentManager, lifecycle)

        val viewPager = view.findViewById<ViewPager2>(R.id.view_pager)

        viewPager.adapter = adapter

        val indicator = view.findViewById<DotsIndicator>(R.id.dots_indicator)

        indicator.attachTo(viewPager)

        val btnNext = view.findViewById<ImageButton>(R.id.ibtn_next)
        val btnPrevious = view.findViewById<ImageButton>(R.id.ibtn_previous)
        val btnSkip = view.findViewById<TextView>(R.id.txt_skip)

        btnSkip.setOnClickListener {
            val preferences = requireActivity().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
            val editor = preferences.edit()
            editor.putBoolean("is_onboarding_shown", true)
            editor.apply()

            startActivity(Intent(requireContext(), WelcomeActivity::class.java))
            requireActivity().finish()
        }

        btnPrevious.isEnabled = false

        btnNext.setOnClickListener {
            if (viewPager.currentItem < fragmentList.size - 1) {
                viewPager.currentItem += 1
                btnPrevious.isEnabled = true
            } else {
                val preferences = requireActivity().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
                val editor = preferences.edit()
                editor.putBoolean("is_onboarding_shown", true)
                editor.apply()

                startActivity(Intent(requireContext(), WelcomeActivity::class.java))
                requireActivity().finish()
            }
        }


        btnPrevious.setOnClickListener {
            if (viewPager.currentItem > 0) {
                viewPager.currentItem -= 1
            }
        }

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                if (position == 0) {
                    btnPrevious.isEnabled = false
                    btnPrevious.setImageResource(R.drawable.previous_disable)
                } else {
                    btnPrevious.isEnabled = true
                    btnPrevious.setImageResource(R.drawable.previous_able)
                }
            }
        })

        return view
    }
}
