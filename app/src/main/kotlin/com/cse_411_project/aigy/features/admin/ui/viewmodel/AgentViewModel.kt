package com.cse_411_project.aigy.features.admin.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cse_411_project.aigy.core.failure.Failure
import com.cse_411_project.aigy.core.platform.BaseViewModel
import com.cse_411_project.aigy.features.admin.data.models.AgentModel
import com.cse_411_project.aigy.features.admin.data.repositories.AgentRepository

class AgentViewModel(private val agentRepository : AgentRepository) : BaseViewModel() {
    private val _agents : MutableLiveData<List<AgentModel>> = MutableLiveData()
    val agents : LiveData<List<AgentModel>> = _agents
    fun fetchAgent(){
        agentRepository.getAgents(
            onComplete = {agentList ->
                _agents.value = agentList
            },
            onError = { error ->
                handleFailure(Failure.DatabaseError)
                Log.e("loi", "Error fetching agents: ${error.message}")
            }
        )
    }
}