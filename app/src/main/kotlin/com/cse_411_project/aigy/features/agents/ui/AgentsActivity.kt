package com.cse_411_project.aigy.features.agents.ui

import android.content.Context
import android.content.Intent
import com.cse_411_project.aigy.core.platform.BaseActivity

class AgentsActivity : BaseActivity() {

    companion object {
        fun callingIntent(context: Context) = Intent(context, AgentsActivity::class.java)
    }

    override fun fragment() = AgentsFragment()
}
