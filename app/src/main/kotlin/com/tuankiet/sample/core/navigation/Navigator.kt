package com.tuankiet.sample.core.navigation

import android.content.ActivityNotFoundException
import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.FragmentActivity
import com.tuankiet.sample.core.extension.emptyString
import com.tuankiet.sample.features.auth.credentials.Authenticator
import com.tuankiet.sample.features.agents.ui.AgentView
import com.tuankiet.sample.features.agents.ui.AgentsActivity

class Navigator(private val authenticator: Authenticator) {
//    private fun showLogin(context: Context) =
//        context.startActivity(LoginActivity.callingIntent(context))

    fun showMain(context: Context) {
        showAgents(context)
//        when (authenticator.userLoggedIn()) {
//            true -> showAgents(context)
//            false -> showLogin(context)
//        }
    }

    private fun showAgents(context: Context) =
        context.startActivity(AgentsActivity.callingIntent(context))

    fun showAgentDetails(activity: FragmentActivity, agent: AgentView, navigationExtras: Extras) {
//        val intent = AgentDetailsActivity.callingIntent(activity, agent)
//        val sharedView = navigationExtras.transitionSharedElement as ImageView
//        val activityOptions = ActivityOptionsCompat
//            .makeSceneTransitionAnimation(activity, sharedView, sharedView.transitionName)
//        activity.startActivity(intent, activityOptions.toBundle())
    }

    class Extras(val transitionSharedElement: View)
}