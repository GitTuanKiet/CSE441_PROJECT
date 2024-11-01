package com.cse_411_project.aigy.features.chat.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.cse_411_project.aigy.BuildConfig
import com.cse_411_project.aigy.R
import com.cse_411_project.aigy.core.extension.loadFromUrl
import com.cse_411_project.aigy.core.extension.loadUrlAndPostponeEnterTransition
import com.cse_411_project.aigy.databinding.FragmentChatBinding
import com.cse_411_project.aigy.features.agents.ui.AgentDetailsView
import com.cse_411_project.aigy.features.chat.ChatActivity
import com.cse_411_project.aigy.features.chat.data.ChatResponse
import com.cse_411_project.aigy.features.chat.data.ChatRequest
import com.google.android.material.bottomnavigation.BottomNavigationView


class ChatFragment : Fragment() {
    companion object {
        private const val PARAM_AGENT = "param_agent"

    }

    private lateinit var binding: FragmentChatBinding
    private val viewModel: ChatViewModel by viewModels() // Sử dụng ViewModel
    private var adapter: ChatRecyclerViewAdapter? = null

    private var messageList = mutableListOf<Any>()

    private var agentDetails: AgentDetailsView? = null

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            agentDetails = it.getParcelable(PARAM_AGENT, AgentDetailsView::class.java)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatBinding.inflate(inflater, container, false)

        agentDetails?.meta?.avatar?.let { binding.avatar.loadFromUrl(it) }
        agentDetails?.meta?.title?.let { binding.title.text = it }

        customFirstBotResponse()
        setupRecyclerView()
        onClickView()
        observerViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        customLayoutWhenKeyboardOpen()
    }

    private fun customFirstBotResponse() {
        val defaultBotResponse = ChatResponse.Data(
            answer = "Hello! I'm ${agentDetails?.meta?.title}. How can I help you?",
            suggestedQuestions = emptyList()
        )
        messageList.add(defaultBotResponse)
    }

    private fun customLayoutWhenKeyboardOpen() {
//        val bottomNav = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav_menu)
        binding.edtMessage.setOnFocusChangeListener { _, hasFocus ->
            val guideline = binding.guideline2.layoutParams as ConstraintLayout.LayoutParams
            if (hasFocus) {
                guideline.guidePercent = 0.83f
//                bottomNav?.visibility = View.GONE
            } else {
                guideline.guidePercent = 0.80f
//                bottomNav?.visibility = View.VISIBLE
            }
            binding.guideline2.layoutParams = guideline
            binding.guideline2.requestLayout()
        }
        binding.root.viewTreeObserver.addOnGlobalLayoutListener {
            val r = Rect()
            binding.root.getWindowVisibleDisplayFrame(r)
            val screenHeight = binding.root.rootView.height
            val keypadHeight = screenHeight - r.bottom

            if (keypadHeight < screenHeight * 0.15) {
//                bottomNav?.visibility = View.VISIBLE
            }else{
//                bottomNav?.visibility = View.GONE
            }
        }
    }

    open fun onBackPressed() {
        activity?.onBackPressedDispatcher?.onBackPressed()
    }

    @SuppressLint("SetTextI18n")
    private fun onClickView() {
        binding.btnSend.setOnClickListener {
            val question = binding.edtMessage.text.toString()
            if (question.isNotBlank()) {
                val chatRequest = ChatRequest("agentID", "knowledgeId", question, "sessionId")
                messageList.add(chatRequest)
                adapter?.notifyItemInserted(messageList.size - 1)
                binding.edtMessage.setText("")

//                viewModel.getMessages(accessToken, apiKey, chatRequest)
                viewModel.sendPrompt(agentDetails?.config?.systemRole ?: "", question)
                binding.recyclerViewChat.scrollToPosition(messageList.size - 1)
            }
        }
    }

    private fun observerViewModel() {
        viewModel.botMessages.observe(viewLifecycleOwner) { response ->
            response?.let {
                messageList.add(it.data)
                adapter?.notifyItemInserted(messageList.size - 1)
                binding.recyclerViewChat.scrollToPosition(messageList.size - 1)
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { exception ->
            exception?.let {
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = ChatRecyclerViewAdapter(messageList){
            scrollToBottom()
        }
        binding.recyclerViewChat.layoutManager = LinearLayoutManager(context).apply {
            scrollToBottom()
        }
        binding.recyclerViewChat.adapter = adapter
    }

    private fun scrollToBottom() {
        binding.recyclerViewChat.scrollToPosition(messageList.size - 1)
    }
}

