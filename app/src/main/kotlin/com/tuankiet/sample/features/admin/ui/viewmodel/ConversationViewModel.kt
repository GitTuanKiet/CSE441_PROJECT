package com.tuankiet.sample.features.admin.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tuankiet.sample.core.platform.BaseViewModel
import com.tuankiet.sample.features.admin.data.models.ConversationModel
import com.tuankiet.sample.features.admin.data.repositories.ConversationRepository

class ConversationViewModel(private val conversationRepository: ConversationRepository) : BaseViewModel(){
    private val _conversations = MutableLiveData<List<ConversationModel>>()
    val conversations: LiveData<List<ConversationModel>> = _conversations

}