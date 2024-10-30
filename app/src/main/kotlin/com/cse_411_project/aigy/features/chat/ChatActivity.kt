package com.cse_411_project.aigy.features.chat

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cse_411_project.aigy.R

class ChatActivity : AppCompatActivity() {

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val backButton = findViewById<ImageButton>(R.id.btn_back)
                backButton.setOnClickListener {
            finish()
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
                val adapter = QuestionAdapter(questionList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val attachButton = findViewById<ImageButton>(R.id.attach_button)
                attachButton.setOnClickListener {
            openFileChooser()
        }
    }

    private fun openFileChooser() {
        fileChooserLauncher.launch("*/*")
    }
}
