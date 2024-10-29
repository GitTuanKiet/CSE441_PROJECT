package com.cse_411_project.aigy.features.chat;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.cse_411_project.aigy.R;

public class ExploreActivity extends AppCompatActivity {

    private ExploreAdapter adapter;
    private final List<ExploreItem> agentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        adapter = new ExploreAdapter(this, agentList);
        recyclerView.setAdapter(adapter);

        fetchAgents();

        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filter(newText);
                return true;
            }
        });
    }

    private void fetchAgents() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<AgentResponse> call = apiService.getAgents();

        call.enqueue(new Callback<AgentResponse>() {
            @Override
            public void onResponse(@NonNull Call<AgentResponse> call, @NonNull Response<AgentResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    agentList.clear();
                    for (Agent agent : response.body().getAgents()) { // Lấy danh sách agents từ phản hồi
                        agentList.add(new ExploreItem(
                                agent.getTitle(),
                                agent.getDescription(),
                                agent.getAuthor(),
                                agent.getCategory()
                        ));
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(ExploreActivity.this, "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<AgentResponse> call, @NonNull Throwable t) {
                // In lỗi ra console
                Log.e("ExploreActivity", "Error: " + t.getMessage());

                // Hiển thị thông báo lỗi
                Toast.makeText(ExploreActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
    }
}
