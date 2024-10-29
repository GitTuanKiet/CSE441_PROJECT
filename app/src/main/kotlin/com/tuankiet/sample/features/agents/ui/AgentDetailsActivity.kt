package com.tuankiet.sample.features.agents.ui

import android.content.Context
import android.content.Intent
import com.tuankiet.sample.core.platform.BaseActivity

class AgentDetailsActivity : BaseActivity() {

    companion object {
        private const val INTENT_EXTRA_PARAM_MOVIE = "com.tuankiet.sample.INTENT_PARAM_AGENT"

        fun callingIntent(context: Context, agent: AgentView) = Intent(context, AgentDetailsActivity::class.java).apply {
            putExtra(INTENT_EXTRA_PARAM_MOVIE, agent)
        }
    }

    override fun fragment() = AgentDetailsFragment.forAgent(intent.getParcelableExtra(INTENT_EXTRA_PARAM_MOVIE))
}