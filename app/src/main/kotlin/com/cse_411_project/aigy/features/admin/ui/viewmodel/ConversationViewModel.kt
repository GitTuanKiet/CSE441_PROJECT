package com.cse_411_project.aigy.features.admin.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cse_411_project.aigy.core.failure.Failure
import com.cse_411_project.aigy.core.platform.BaseViewModel
import com.cse_411_project.aigy.features.admin.data.models.ConversationModel
import com.cse_411_project.aigy.features.admin.data.repositories.ConversationRepository

class ConversationViewModel(private val conversationRepository: ConversationRepository) : BaseViewModel(){
    private val _conversations = MutableLiveData<List<ConversationModel>>()
    val conversations: LiveData<List<ConversationModel>> = _conversations
    fun getConversationByUserID(userId : String ){
        conversationRepository.getConversationsByUserId(userId,
            onComplete = {
                _conversations.value = it
            },
            onError = {error ->
                handleFailure(Failure.DatabaseError)
            }
        )
    }
}