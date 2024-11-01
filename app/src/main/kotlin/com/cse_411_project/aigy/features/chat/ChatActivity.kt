package com.cse_411_project.aigy.features.chat

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cse_411_project.aigy.R
import com.cse_411_project.aigy.core.extension.inTransaction
import com.cse_411_project.aigy.databinding.ActivityChatBinding
import com.cse_411_project.aigy.databinding.ActivityLayoutBinding
import com.cse_411_project.aigy.features.agents.ui.AgentDetailsView
import com.cse_411_project.aigy.features.chat.WelcomeAgentActivity.Companion
import com.cse_411_project.aigy.features.chat.ui.ChatFragment

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    private lateinit var adapter: QuestionAdapter

    private val questionList = listOf(
            "Ghi nhớ những gì người dùng đã nói trước đó trong cuộc trò chuyện",
            "Cho phép người dùng cung cấp các chỉnh sửa tiếp theo với AI",
            "Kiến thức hạn chế về thế giới và các sự kiện sau năm 2021",
            "Đôi khi có thể tạo ra thông tin không chính xác",
            "Đôi khi có thể tạo ra các hướng dẫn có hại hoặc nội dung thiên vị"
    )

    private val fileChooserLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityChatBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.inTransaction {
                add(binding.fragmentContainer.id, ChatFragment())
            }
        }

        val agentDetails =
            intent.getParcelableExtra(PARAM_AGENT, AgentDetailsView::class.java) as AgentDetailsView

//        findViewById<ImageButton>(R.id.btn_back).setOnClickListener {
//            finish()
//        }

//        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
//                val adapter = QuestionAdapter(questionList)
//        recyclerView.layoutManager = LinearLayoutManager(this)
//        recyclerView.adapter = adapter

        findViewById<ImageButton>(R.id.btn_attach_img)?.setOnClickListener {
            openFileChooser()
        }


    }

    private fun openFileChooser() {
        fileChooserLauncher.launch("*/*")
    }

    companion object {
        private const val PARAM_AGENT = "param_agent"

        fun callingIntent(context: Context, agentDetails: AgentDetailsView): Intent {
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra(PARAM_AGENT, agentDetails)
            return intent
        }
    }
}
