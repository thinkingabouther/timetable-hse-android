package org.hse.demoapplication.shared

import java.text.SimpleDateFormat
import java.util.*

class CurrentTimeProvider {
    companion object {

        private const val datePattern = "h:mm, EEEE"

        fun hoursMinutesDoW() : String {
            val currentDate : Date = Calendar.getInstance().time;
            return SimpleDateFormat(datePattern, Locale.getDefault()).format(currentDate.time)
        }
    }
}