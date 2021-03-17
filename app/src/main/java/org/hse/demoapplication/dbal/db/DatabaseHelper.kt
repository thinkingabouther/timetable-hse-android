package org.hse.demoapplication.dbal.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.hse.demoapplication.dbal.dao.HseDao
import org.hse.demoapplication.dbal.entity.GroupEntity
import org.hse.demoapplication.dbal.entity.TeacherEntity
import org.hse.demoapplication.dbal.entity.TimeTableEntity


@Database(entities = [
    GroupEntity::class,
    TeacherEntity::class,
    TimeTableEntity::class
],
          version = 1,
          exportSchema = false)
@TypeConverters(ConverterUtils::class)
abstract class DatabaseHelper : RoomDatabase() {

    companion object {
        val DATABASE_NAME = "hse_timetable"
    }

    abstract fun hseDao() : HseDao
}