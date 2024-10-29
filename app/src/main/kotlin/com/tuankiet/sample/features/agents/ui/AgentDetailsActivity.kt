package com.tuankiet.sample.features.agents.ui

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import com.tuankiet.sample.core.platform.BaseActivity

class AgentDetailsActivity : BaseActivity() {

    companion object {
        private const val INTENT_EXTRA_PARAM_MOVIE = "com.tuankiet.INTENT_PARAM_AGENT"

        fun callingIntent(context: Context, agent: AgentView) = Intent(context, AgentDetailsActivity::class.java).apply {
            putExtra(INTENT_EXTRA_PARAM_MOVIE, agent)
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun fragment() = AgentDetailsFragment.forAgent(intent.getParcelableExtra(INTENT_EXTRA_PARAM_MOVIE, AgentView::class.java))
}