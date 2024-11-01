package com.cse_411_project.aigy.features.chat

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.cse_411_project.aigy.R
import com.cse_411_project.aigy.core.extension.loadUrlAndPostponeEnterTransition
import com.cse_411_project.aigy.features.agents.ui.AgentDetailsView

class WelcomeAgentActivity : AppCompatActivity() {
    private lateinit var agentDetails: AgentDetailsView
    private fun layoutId() = R.layout.activity_welcome_agent;

    @SuppressLint("WrongViewCast")
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId())

        agentDetails =
            intent.getParcelableExtra(PARAM_AGENT, AgentDetailsView::class.java) as AgentDetailsView
        val backButton: ImageButton = findViewById(R.id.back_button)
        backButton.setOnClickListener {
            finish()
        }

        findViewById<ImageView>(R.id.tiktok_logo).loadUrlAndPostponeEnterTransition(agentDetails.meta.avatar, this)
        findViewById<TextView>(R.id.welcome_text).text = agentDetails.meta.title
        findViewById<TextView>(R.id.app_name_text).text = agentDetails.author
        findViewById<TextView>(R.id.subtext).text = agentDetails.meta.description

        val getStartedButton: Button = findViewById(R.id.get_started_button)
        getStartedButton.setOnClickListener {
            startActivity(ChatActivity.callingIntent(this, agentDetails))
        }
    }

    companion object {
        private const val PARAM_AGENT = "param_agent"

        fun callingIntent(context: Context, agentDetails: AgentDetailsView): Intent {
            val intent = Intent(context, WelcomeAgentActivity::class.java)
            intent.putExtra(PARAM_AGENT, agentDetails)
            return intent
        }
    }
}