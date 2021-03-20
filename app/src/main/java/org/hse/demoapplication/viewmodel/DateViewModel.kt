package org.hse.demoapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class DateViewModel : ViewModel() {
    val currentDate: MutableLiveData<Date> by lazy {
        MutableLiveData<Date>()
    }
}