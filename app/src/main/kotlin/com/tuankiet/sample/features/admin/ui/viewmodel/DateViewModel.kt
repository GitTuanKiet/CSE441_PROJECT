package com.tuankiet.sample.features.admin.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tuankiet.sample.core.failure.Failure
import com.tuankiet.sample.core.platform.BaseViewModel
import com.tuankiet.sample.features.admin.data.models.DateModel
import com.tuankiet.sample.features.admin.data.repositorys.DateRespository


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