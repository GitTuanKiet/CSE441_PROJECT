package com.cse_411_project.aigy.core.extension

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.cse_411_project.aigy.core.failure.Failure

fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: (T?) -> Unit) =
    liveData.observe(this, Observer(body))

fun <L : LiveData<Failure>> LifecycleOwner.failure(liveData: L, body: (Failure?) -> Unit) =
    liveData.observe(this, Observer(body))
