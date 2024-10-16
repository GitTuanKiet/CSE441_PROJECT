package com.tuankiet.sample.features.agents.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.tuankiet.sample.core.platform.BaseActivity

class AgentsActivity : BaseActivity() {

    companion object {
        fun callingIntent(context: Context) = Intent(context, AgentsActivity::class.java)
    }

    override fun fragment() = AgentsFragment()
}
