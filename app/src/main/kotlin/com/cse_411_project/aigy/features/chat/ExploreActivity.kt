package com.cse_411_project.aigy.features.chat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cse_411_project.aigy.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExploreActivity : AppCompatActivity() {

    companion object {
        fun callingIntent(context: Context): Intent {
            return Intent(context, ExploreActivity::class.java)
        }
    }

    private lateinit var adapter: ExploreAdapter
    private val agentList = mutableListOf<ExploreItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_explore)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
                recyclerView.layoutManager = GridLayoutManager(this, 2)

        adapter = ExploreAdapter(this, agentList)
        recyclerView.adapter = adapter

        fetchAgents()

        val searchView = findViewById<SearchView>(R.id.searchView)
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter(newText ?: "")
                return true
            }
        })
    }

    private fun fetchAgents() {
        val apiService = ApiClient.getClient().create(ApiService::class.java)
        val call = apiService.getAgents()

        call.enqueue(object : Callback<AgentResponse> {
            override fun onResponse(call: Call<AgentResponse>, response: Response<AgentResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    agentList.clear()
                    response.body()?.agents?.forEach { agent ->
                            agentList.add(
                                    ExploreItem(
                                            agent.title,
                                            agent.description,
                                            agent.author,
                                            agent.category
                                    )
                            )
                    }
                    adapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(this@ExploreActivity, "Không có dữ liệu", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<AgentResponse>, t: Throwable) {
                Log.e("ExploreActivity", "Error: ${t.message}")

                Toast.makeText(this@ExploreActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
