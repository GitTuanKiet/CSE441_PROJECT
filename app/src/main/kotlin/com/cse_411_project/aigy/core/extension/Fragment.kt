package com.cse_411_project.aigy.core.extension

import android.content.Context
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.cse_411_project.aigy.core.platform.BaseActivity
import com.cse_411_project.aigy.core.platform.BaseFragment

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) =
    beginTransaction().func().commit()

fun BaseFragment.close() = getParentFragmentManager().popBackStack()

val BaseFragment.viewContainer: View get() = (activity as BaseActivity).fragmentContainer()

val BaseFragment.appContext: Context get() = activity?.applicationContext!!
