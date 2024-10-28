package com.cse_411_project.aigy.khanh.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cse_411_project.aigy.R
import com.cse_411_project.aigy.khanh.activity.WelcomeActivity

class SplashFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val preferences = requireActivity().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val isOnboardingShown = preferences.getBoolean("is_onboarding_shown", false)

        Handler(Looper.getMainLooper()).postDelayed({
            if (isOnboardingShown) {
                startActivity(Intent(activity, WelcomeActivity::class.java))
                activity?.finish()
            } else {
                replaceFragment(OnBoardingFragment())
            }
        }, 3000)

        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
