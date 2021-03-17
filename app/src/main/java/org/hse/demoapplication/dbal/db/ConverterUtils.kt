package org.hse.demoapplication.dbal.db

import androidx.room.TypeConverter
import java.util.*

class ConverterUtils {
    @TypeConverter
    public fun fromTimestamp(value: Long?): Date? {
        value?.let{ return Date(it)}
        return null
    }
    @TypeConverter
    public fun dateToTimestamp(date: Date?): Long? {
        date?.let{ return date.time}
        return null
    }
}