package com.tuankiet.sample.features.admin.data.repositories

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.tuankiet.sample.features.admin.data.models.MessageModel
import java.util.Calendar
import kotlin.Int as Int

class MessageRepository {
    val db = FirebaseFirestore.getInstance()
    val messageRef = db.collection("messages")
    fun getData(document : QueryDocumentSnapshot): MessageModel {
        val id = document.getString("id") ?: ""
        val senderId = document.getString("senderId") ?: ""
        val content = document.getString("content") ?: ""
        val timestamp = document.getLong("timestamp") ?: 0L
        val completionTime = document.getLong("completionTime") ?: 0L
        val type = document.getString("type") ?: ""
        return MessageModel(id, senderId, content, type, completionTime, timestamp)
    }
    fun getMessages(onComplete: (List<MessageModel>) -> Unit, onError: (Exception) -> Unit){
        messageRef.get().addOnSuccessListener { querySnapshot ->
            val messages = mutableListOf<MessageModel>()
            for (document in querySnapshot) {
                val message = getData(document)
                messages.add(message)
            }
            onComplete(messages)
        }.addOnFailureListener { exception ->
            onError(exception)
        }
    }
    fun ConvertMonth(time: Long) : Int {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = time
        }
        return calendar.get(Calendar.MONTH) + 1
    }
    fun ConvertDate(time : Long) : Int {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = time
        }
        return calendar.get(Calendar.DAY_OF_MONTH)
    }
    fun ConvertYear(time : Long) : Int {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = time
        }
        return calendar.get(Calendar.YEAR)
    }

    fun fetchMessageCountByDayInMonth(month: Int, year :Int , onComplete: (Map<Int, Int>) -> Unit, onError: (Exception) -> Unit) {
        messageRef.get().addOnSuccessListener { querySnapshot ->
            val dailyMessageCount = mutableMapOf<Int, Int>()

            for (document in querySnapshot) {
                val message = getData(document)
                if (month == ConvertMonth(message.timestamp) && year == ConvertYear(message.timestamp)) {
                    val date: Int = ConvertDate(message.timestamp)
                    dailyMessageCount[date] = (dailyMessageCount[date] ?: 0) + 1
                }
            }

            onComplete(dailyMessageCount)
        }.addOnFailureListener { exception ->
            onError(exception)
        }
    }



}