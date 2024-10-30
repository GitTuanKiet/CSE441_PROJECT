package com.cse_411_project.aigy.features.admin.data.repositories

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.cse_411_project.aigy.features.admin.data.models.ConversationModel
import com.cse_411_project.aigy.features.admin.data.models.MessageModel

class ConversationRepository {
    private val database = FirebaseDatabase.getInstance()
    private val conversationsRef = database.getReference("conversations")
    val MessageRepository = MessageRepository()
    val messages = mutableListOf<MessageModel>()
    fun getData(userSnapshot : DataSnapshot): ConversationModel {
        val id  = userSnapshot.child("id").getValue(String::class.java)
        val user_id = userSnapshot.child("user_id").getValue(String::class.java)
        val agent_id = userSnapshot.child("agent_id").getValue(String::class.java)
        MessageRepository.getMessages(
            onComplete = {
                message ->
                run {
                    messages.clear()
                    messages.addAll(message)
                }
            },
            onError = {
                    error ->
                run {
                    Log.e("loi", error.toString())
                }
            }
        )
        return ConversationModel(id ?: "", user_id ?: "", agent_id ?: "", messages)
    }
    fun getConversations(onComplete: (List<ConversationModel>) -> Unit, onError: (DatabaseError) -> Unit){
        conversationsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val conversationList = mutableListOf<ConversationModel>()
                for (conversationSnapshot in snapshot.children) {
                    conversationList.add(getData(conversationSnapshot))
                }
                onComplete(conversationList)
            }

            override fun onCancelled(error: DatabaseError) {
                onError(error)
            }

        })
    }
    fun getConversationsByUserId(
        userId: String,
        onComplete: (List<ConversationModel>) -> Unit,
        onError: (DatabaseError) -> Unit
    ) {
        conversationsRef.orderByChild("user_id").equalTo(userId)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val conversationList = mutableListOf<ConversationModel>()
                    for (conversationSnapshot in snapshot.children) {
                        conversationList.add(getData(conversationSnapshot))
                    }
                    onComplete(conversationList)
                }

                override fun onCancelled(error: DatabaseError) {
                    onError(error)
                }
            })
    }


}