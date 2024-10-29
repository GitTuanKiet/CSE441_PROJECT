package com.cse_411_project.aigy.features.admin.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cse_411_project.aigy.core.failure.Failure
import com.cse_411_project.aigy.core.platform.BaseViewModel
import com.cse_411_project.aigy.features.admin.data.models.DateModel
import com.cse_411_project.aigy.features.admin.data.repositories.DateRespository


class DateViewModel(private val dateRespository: DateRespository) : BaseViewModel() {
    private val _date = MutableLiveData<List<DateModel>>()
    val date: LiveData<List<DateModel>> = _date
    fun fetchDate(){
        dateRespository.fetchDate(onComplete = { dates ->
            _date.value = dates
        }, onError = {
            error ->
            handleFailure(Failure.DatabaseError)
            Log.e("loi", "Error fetching users: ${error.message}")
        })
    }
    fun updateDate(){
        dateRespository.updateDate(onComplete = {
            Log.d("loi", "Cập nhật thành công")
        },
        onError = { error ->
            handleFailure(Failure.DatabaseError)
            Log.e("loi", "Error fetching users: ${error.message}")
        })
    }
    fun fetchDateByMonth(month :Int){
        dateRespository.fetchDateByMonth(month , onComplete = { dates ->
            _date.value = dates
        }, onError = {
                error ->
            handleFailure(Failure.DatabaseError)
            Log.e("loi", "Error fetching users: ${error.message}")
        })
    }

}