package com.tuankiet.sample.features.admin.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tuankiet.sample.core.failure.Failure
import com.tuankiet.sample.core.platform.BaseViewModel
import com.tuankiet.sample.features.admin.data.models.MessageModel
import com.tuankiet.sample.features.admin.data.repositorys.MessageRepository

class MessageViewModel(private val  messagesRepossitory : MessageRepository) : BaseViewModel(){
    private val _messages: MutableLiveData<List<MessageModel>> = MutableLiveData()
    val message: LiveData<List<MessageModel>> = _messages
    private val _messageCount: MutableLiveData<Map<Int, Int>> = MutableLiveData()
    val messageCount: LiveData<Map<Int, Int>> = _messageCount
    fun fetchMessage(){
        messagesRepossitory.getMessages(onComplete = {
            messageList ->
                _messages.value = messageList
        }, onError = {error ->
            handleFailure(Failure.DatabaseError)
            Log.e("loi", "Error fetching agents: ${error.message}")
        })
    }
    fun fetchMessageByMonth(month : Int , year : Int){
        messagesRepossitory.fetchMessageCountByDayInMonth(month, year , onComplete = { messageList ->
            _messageCount.value = messageList
        }, onError = {error ->
            handleFailure(Failure.DatabaseError)
            Log.e("loi", "Error fetching agents: ${error.message}")
        })
    }
}