package com.dna.chat;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("https://chat-agents.lobehub.com/")
    Call<AgentResponse> getAgents();}
