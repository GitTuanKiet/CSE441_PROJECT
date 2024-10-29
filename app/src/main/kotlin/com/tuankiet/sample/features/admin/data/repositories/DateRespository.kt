package com.tuankiet.sample.features.admin.data.repositories

import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.tuankiet.sample.features.admin.data.models.DateModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DateRespository {
    val databaseRef = FirebaseDatabase.getInstance().getReference("visitCount")
    val time = Date()
    val datetime = SimpleDateFormat("dd_MM_yyyy", Locale.US).format(time)
    fun updateDate(onComplete:() -> Unit, onError: (DatabaseError) -> Unit)
    {
        databaseRef.child(datetime).get()
        .addOnSuccessListener { snapshot ->
            val currentCount = snapshot.getValue(Long::class.java) ?: 0
            val updatedCount = currentCount + 1
            databaseRef.child(datetime).setValue(updatedCount)
                .addOnSuccessListener {onComplete()}.
                addOnFailureListener { error ->
                    val databaseError = DatabaseError.fromException(error)
                    onError(databaseError)
                }
        }.addOnFailureListener { error ->
                val databaseError = DatabaseError.fromException(error)
                onError(databaseError)
        }
    }
    fun fetchDate(onComplete: (List<DateModel>) -> Unit, onError: (DatabaseError) -> Unit){
        databaseRef.get().addOnSuccessListener { snapshot ->
            val dateList = mutableListOf<DateModel>()
            for (dateSnapshot in snapshot.children) {
                val date = dateSnapshot.key
                val count = dateSnapshot.getValue(Long::class.java)
                val dateModel = DateModel(date ?: "", count ?: 0)
                dateList.add(dateModel)
            }
            onComplete(dateList)
        }.addOnFailureListener { error ->
            val databaseError = DatabaseError.fromException(error)
            onError(databaseError)
        }
    }
    fun fetchDateByMonth(month : Int ,onComplete: (List<DateModel>) -> Unit , onError: (DatabaseError) -> Unit){
        databaseRef.get().addOnSuccessListener { snapshot ->
            val dateList = mutableListOf<DateModel>()
            for (dateSnapshot in snapshot.children) {
                val datetime = dateSnapshot.key?.split("_")
                val monthTime = datetime?.get(1)?.toIntOrNull()
                if(monthTime == month){
                    val date = datetime[0]
                    val count = dateSnapshot.getValue(Long::class.java)
                    val dateModel = DateModel(date , count ?: 0)
                    dateList.add(dateModel)
                }
            }
            onComplete(dateList)
        }.addOnFailureListener { error ->
            val databaseError = DatabaseError.fromException(error)
            onError(databaseError)
        }
    }
}




