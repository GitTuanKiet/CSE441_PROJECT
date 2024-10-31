package com.cse_411_project.aigy.features.chat

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cse_411_project.aigy.R

class ExploreAdapter(
        private val context: Context,
        itemList: List<ExploreItem>
) : RecyclerView.Adapter<ExploreAdapter.ExploreViewHolder>() {

private val originalList = ArrayList(itemList)
private val filteredList = ArrayList(itemList)

inner class ExploreViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val title: TextView = view.findViewById(R.id.item_title)
    val description: TextView = view.findViewById(R.id.item_description)
    val category: TextView = view.findViewById(R.id.item_category)
    val author: TextView = view.findViewById(R.id.item_author)
}

override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExploreViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.item_explore, parent, false)
    return ExploreViewHolder(view)
}

override fun onBindViewHolder(holder: ExploreViewHolder, position: Int) {
    val item = filteredList[position]
    holder.title.text = item.title
    holder.description.text = item.description
    holder.category.text = item.category
    holder.author.text = item.author

    holder.itemView.setOnClickListener {
        val intent = Intent(context, WelcomeAgentActivity::class.java).apply {
            putExtra("agent_title", item.title)
            putExtra("agent_description", item.description)
            putExtra("agent_category", item.category)
            putExtra("agent_author", item.author)
        }
        context.startActivity(intent)
    }
}

override fun getItemCount(): Int = filteredList.size

fun filter(text: String) {
    filteredList.clear()
    if (text.isEmpty()) {
        filteredList.addAll(originalList)
    } else {
        val query = text.lowercase()
        filteredList.addAll(originalList.filter {
            it.title.lowercase().contains(query) ||
                    it.description.lowercase().contains(query) ||
                    it.category.lowercase().contains(query) ||
                    it.author.lowercase().contains(query)
        })
    }
    notifyDataSetChanged()
}
}
