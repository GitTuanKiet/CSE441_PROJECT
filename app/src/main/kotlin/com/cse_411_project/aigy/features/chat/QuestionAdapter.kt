package com.cse_411_project.aigy.features.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.cse_411_project.aigy.R

class QuestionAdapter(private val questionList: List<String>) : RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder>() {

override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.item_question, parent, false)
    return QuestionViewHolder(view)
}

override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
    val question = questionList[position]
    holder.questionButton.text = question
}

override fun getItemCount(): Int = questionList.size

class QuestionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val questionButton: Button = itemView.findViewById(R.id.question_button)
}
}
