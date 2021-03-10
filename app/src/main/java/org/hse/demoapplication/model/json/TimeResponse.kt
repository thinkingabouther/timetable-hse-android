package org.hse.demoapplication.model.json

import com.google.gson.annotations.SerializedName

class TimeResponse {

    @SerializedName("time_zone")
    private lateinit var timeZone : TimeZone

    fun getTimeZone(): TimeZone {
        return timeZone
    }

    fun setTimeZone(timeZone : TimeZone) {
        this.timeZone = timeZone
    }
}