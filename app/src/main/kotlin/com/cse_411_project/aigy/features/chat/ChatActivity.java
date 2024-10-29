package com.cse_411_project.aigy.features.chat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

import com.cse_411_project.aigy.R;

public class ChatActivity extends AppCompatActivity {
    private static final int FILE_REQUEST_CODE = 1;

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

        // Khởi tạo RecyclerView
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

        // Xử lý sự kiện cho attach_button
        ImageButton attachButton = findViewById(R.id.attach_button);
        attachButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mở trình chọn tệp
                openFileChooser();
            }
        });
    }

    private void openFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*"); // Cho phép chọn tất cả các loại tệp
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, "Chọn tệp"), FILE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Uri fileUri = data.getData();
            // Gửi tệp hoặc xử lý theo nhu cầu của bạn
            // Ví dụ: gửi tệp đến máy chủ hoặc hiển thị trong giao diện người dùng
        }
    }
}
