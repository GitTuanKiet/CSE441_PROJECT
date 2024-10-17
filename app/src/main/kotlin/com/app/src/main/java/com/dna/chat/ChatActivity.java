package com.dna.chat;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        ImageButton backButton = findViewById(R.id.btn_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        List<String> questionList = Arrays.asList(
                "Ghi nhớ những gì người dùng đã nói trước đó trong cuộc trò chuyện",
                "Cho phép người dùng cung cấp các chỉnh sửa tiếp theo với AI",
                "Kiến thức hạn chế về thế giới và các sự kiện sau năm 2021",
                "Đôi khi có thể tạo ra thông tin không chính xác",
                "Đôi khi có thể tạo ra các hướng dẫn có hại hoặc nội dung thiên vị"
        );

        QuestionAdapter adapter = new QuestionAdapter(questionList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }


}
