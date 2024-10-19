package com.tuankiet.sample.features.admin.ui.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatDelegate
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import com.tuankiet.sample.R
import com.tuankiet.sample.core.platform.BaseFragment
import com.tuankiet.sample.databinding.ActivityLayoutBinding
import com.tuankiet.sample.features.admin.data.UserModel
import java.util.Locale

class AdminFragment : BaseFragment() {
    private lateinit var binding: ActivityLayoutBinding
    private var pageIndex = 0
    private lateinit var btnMenu: ImageButton
    private lateinit var taskBar: DrawerLayout
    private lateinit var main: ConstraintLayout
    private lateinit var navbar: TableLayout
    private lateinit var locale: Locale
    private lateinit var prefs: SharedPreferences
    private lateinit var newLanguage: String
    private lateinit var currentLanguage: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLayoutBinding.inflate(layoutInflater)
        prefs = requireContext().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        currentLanguage = prefs.getString("language", "en").toString()
        setLocale(currentLanguage)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val isDarkMode = prefs.getBoolean("isDarkMode", false)
        setDarkMode(isDarkMode)

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_admin, container, false)

        Log.d("AdminFragment", "Dark mode status: $isDarkMode")

        Mapping(view)
        loadingFragment(pageIndex)
        getEvent()

        return view
    }

    private fun getEvent() {
        btnMenu.setOnClickListener {
            if (taskBar.isDrawerOpen(GravityCompat.START)) {
                taskBar.closeDrawer(GravityCompat.START)
            } else {
                taskBar.openDrawer(GravityCompat.START)
            }
        }

        for (i in 0 until navbar.childCount) {
            val child = navbar.getChildAt(i)
            if (child is TableRow) {
                when (i) {
                    0 -> child.setOnClickListener { loadingFragment(0) }
                    1 -> child.setOnClickListener { loadingFragment(1) }
                    2 -> child.setOnClickListener { toggleDarkMode() }
                    3 -> child.setOnClickListener { switchLanguage() }
                    4 -> Log.d("AdminFragment", "Logout clicked")
                }
            }
        }
    }

    private fun Mapping(view: View?) {
        taskBar = view?.findViewById(R.id.Taskbar)!!
        navbar = view.findViewById(R.id.NavBar)
        btnMenu = view.findViewById(R.id.btnMenu)
    }

    private fun loadingFragment(pageIndex: Int) {
        val fragmentManager: FragmentManager = parentFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        when (pageIndex) {
            0 -> {
                val myFragment1 = HomeAdminFragment()
                fragmentTransaction.replace(R.id.displayData, myFragment1)
            }
            1 -> {
                val myFragment2 = UseManagementFragment()
                fragmentTransaction.replace(R.id.displayData, myFragment2)
            }
        }
        fragmentTransaction.commit()
    }

    private fun toggleDarkMode() {
        val isDarkMode = prefs.getBoolean("isDarkMode", false)
        if (isDarkMode) {
            prefs.edit().putBoolean("isDarkMode", false).apply()
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        } else {
            prefs.edit().putBoolean("isDarkMode", true).apply()
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        requireActivity().recreate()
    }

    private fun switchLanguage() {
        newLanguage = if (currentLanguage == "en") "vi" else "en"
        setLocale(newLanguage)
        prefs.edit().putString("language", newLanguage).apply()
        requireActivity().recreate()
    }

    private fun setLocale(languageCode: String) {
        locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }

    private fun setDarkMode(isDarkMode: Boolean) {
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    fun updateUserList(users: List<UserModel>) {
        // Implement the method to update the user list in your UI
    }
}
