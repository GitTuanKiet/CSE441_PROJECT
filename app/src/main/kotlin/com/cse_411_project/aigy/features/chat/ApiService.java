package com.cse_411_project.aigy.features.chat;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("/")
    Call<AgentResponse> getAgents();
}
