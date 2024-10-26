package com.tuankiet.sample.features.admin.data.repositorys

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.tuankiet.sample.features.admin.data.models.AgentModel
import com.tuankiet.sample.features.admin.data.models.ConversationModel

class AgentRepository {
    private val database = FirebaseDatabase.getInstance()
    private val agentsRef = database.getReference("agents")
    val conversationRepository = ConversationRepository()
    val conversations = mutableListOf<ConversationModel>()

    fun getData(userSnapshot: DataSnapshot): AgentModel {
        val id = userSnapshot.child("id").getValue(String::class.java)
        val userId = userSnapshot.child("user_id").getValue(String::class.java)


        conversationRepository.getConversations(
            onComplete = { conversation ->
                conversations.clear()
                conversations.addAll(conversation)
            },
            onError = { error ->
                Log.e("Error", error.toString())
            }
        )

        return AgentModel(id ?: "", userId ?: "", conversations)
    }

    fun getAgents(onComplete: (List<AgentModel>) -> Unit, onError: (DatabaseError) -> Unit) {
        agentsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val agentList = mutableListOf<AgentModel>()
                for (agentSnapshot in snapshot.children) {
                    agentList.add(getData(agentSnapshot))
                }
                onComplete(agentList)
            }

            override fun onCancelled(error: DatabaseError) {
                onError(error)
            }
        })
    }
}
