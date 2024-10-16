package com.tuankiet.sample.features.agents.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tuankiet.sample.core.extension.loadFromUrl
import com.tuankiet.sample.core.navigation.Navigator
import com.tuankiet.sample.databinding.RowAgentBinding
import kotlin.properties.Delegates

@SuppressLint("NotifyDataSetChanged")
class AgentsAdapter : RecyclerView.Adapter<AgentsAdapter.ViewHolder>() {

    internal var collection: List<AgentView> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    internal var clickListener: (AgentView, Navigator.Extras) -> Unit = { _, _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(RowAgentBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) =
        viewHolder.bind(collection[position], clickListener)

    override fun getItemCount() = collection.size

    class ViewHolder(private val binding: RowAgentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(agentView: AgentView, clickListener: (AgentView, Navigator.Extras) -> Unit) {
            binding.agentItem.agentAvatar.loadFromUrl(agentView.meta.avatar)
            binding.agentItem.agentAuthor.text = agentView.author
            binding.agentItem.agentTitle.text = agentView.meta.title
            binding.agentItem.agentDescription.text = agentView.meta.description
            binding.root.setOnClickListener {
                clickListener(
                    agentView,
                    Navigator.Extras(binding.agentItem.agentAvatar)
                )
            }
        }
    }
}