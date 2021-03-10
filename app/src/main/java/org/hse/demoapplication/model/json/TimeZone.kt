package org.hse.demoapplication.model.json

import com.google.gson.annotations.SerializedName

class TimeZone {

    @SerializedName("current_time")
    private lateinit var currentTime : String

    fun getCurrentTime(): String {
        return currentTime
    }

    fun setCurrentTime(currentTime : String) {
        this.currentTime = currentTime
    }
}