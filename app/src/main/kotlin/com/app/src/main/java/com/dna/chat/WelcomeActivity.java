package com.dna.chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // Back button to go to the previous activity
        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the current activity and go back
                finish();
            }
        });

        // Get Started button to move to the next activity (e.g., ChatActivity)
        Button getStartedButton = findViewById(R.id.get_started_button);
//        getStartedButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Start ChatActivity (you can replace ChatActivity with your target activity)
//                Intent intent = new Intent(WelcomeActivity.this, ChatActivity.class);
//                startActivity(intent);
//            }
//        });
    }
}
