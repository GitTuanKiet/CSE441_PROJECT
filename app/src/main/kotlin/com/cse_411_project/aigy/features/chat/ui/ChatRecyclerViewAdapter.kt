package com.cse_411_project.aigy.features.chat.ui

import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cse_411_project.aigy.R
import com.cse_411_project.aigy.databinding.ChatRecycleMessageBinding
import com.cse_411_project.aigy.features.chat.data.ChatRequest
import com.cse_411_project.aigy.features.chat.data.ChatResponse

class ChatRecyclerViewAdapter (
    private val messageList: MutableList<Any>,
    private val scrollToBottom: () -> Unit
    ) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        companion object {
            private const val VIEW_TYPE_USER = 1
            private const val VIEW_TYPE_BOT = 2
        }

    override fun getItemViewType(position: Int): Int {
        return if (messageList[position] is ChatRequest) VIEW_TYPE_USER else VIEW_TYPE_BOT
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ChatRecycleMessageBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return if (viewType == VIEW_TYPE_USER) UserViewHolder(binding) else BotViewHolder(binding)
    }

    override fun getItemCount(): Int = messageList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = messageList[position]
        if (holder is UserViewHolder && item is ChatRequest) {
            holder.bind(item)
        } else if (holder is BotViewHolder && item is ChatResponse.Data) {
            holder.bind(item)
        }
    }


    inner class UserViewHolder(private val binding: ChatRecycleMessageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(chatRequest: ChatRequest) {
            binding.rightChatTextview.text = chatRequest.question
            binding.rightChatTextview.visibility = View.VISIBLE
            binding.leftChatLayout.visibility = View.GONE
        }
    }


    inner class BotViewHolder(private val binding: ChatRecycleMessageBinding) : RecyclerView.ViewHolder(binding.root) {
        private val handler = Handler()
        private var currentIndex = 0
        private var displayedText = ""

        fun bind(item: ChatResponse.Data) {
            Glide.with(binding.root.context).load(R.drawable.robot_image_1).into(binding.imageMessageItem)
            displayedText = item.answer
            binding.leftChatTextview.text = ""
            binding.leftChatTextview.visibility = View.VISIBLE
            binding.rightChatLayout.visibility = View.GONE


            showTextDynamically()
        }

        private fun showTextDynamically() {
            currentIndex = 0
            handler.postDelayed(object : Runnable {
                override fun run() {
                    if (currentIndex < displayedText.length) {
                        binding.leftChatTextview.text = displayedText.substring(0, currentIndex + 1)
                        currentIndex++
                        scrollToBottom()
                        handler.postDelayed(this, 10)
                    } else {
                        handler.removeCallbacks(this)
                    }
                }
            }, 1)
        }
    }
}