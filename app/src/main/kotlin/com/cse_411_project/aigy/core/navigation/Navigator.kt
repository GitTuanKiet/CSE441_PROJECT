package com.cse_411_project.aigy.core.navigation

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.FragmentActivity
import com.cse_411_project.aigy.features.admin.ui.activitys.AdminActivity
import com.cse_411_project.aigy.features.agents.ui.AgentDetailsActivity
import com.cse_411_project.aigy.features.auth.credentials.Authenticator
import com.cse_411_project.aigy.features.agents.ui.AgentView
import com.cse_411_project.aigy.features.agents.ui.AgentsActivity
import com.cse_411_project.aigy.features.chat.WelcomeAgentActivity
import com.cse_411_project.aigy.khanh.activity.MainActivity

class Navigator(private val authenticator: Authenticator) {
//    private fun showLogin(context: Context) =
//        context.startActivity(LoginActivity.callingIntent(context))

    fun showMain(context: Context) {
//        showAgents(context)

//        showAdmin(context)

        showWelcome(context)

//        showKhanhMain(context)

//        when (authenticator.userLoggedIn()) {
//            true -> showAgents(context)
//            false -> showLogin(context)
//        }
    }


    // Display UI Admin
    private fun showAdmin(context: Context) = context.startActivity(AdminActivity.callingIntent(context))

    private fun showWelcome(context: Context) = context.startActivity(WelcomeAgentActivity.callingIntent(context))

    private fun showKhanhMain(context: Context) = context.startActivity(MainActivity.callingIntent(context))

    private fun showAgents(context: Context) =
        context.startActivity(AgentsActivity.callingIntent(context))

    fun showAgentDetails(activity: FragmentActivity, agent: AgentView, navigationExtras: Extras) {
        val intent = AgentDetailsActivity.callingIntent(activity, agent)
        val sharedView = navigationExtras.transitionSharedElement as ImageView
        val activityOptions = ActivityOptionsCompat
            .makeSceneTransitionAnimation(activity, sharedView, sharedView.transitionName)
        activity.startActivity(intent, activityOptions.toBundle())
    }

    class Extras(val transitionSharedElement: View)
}