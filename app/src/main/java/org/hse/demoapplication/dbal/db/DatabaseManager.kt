package org.hse.demoapplication.dbal.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import org.hse.demoapplication.dbal.dao.HseDao
import org.hse.demoapplication.dbal.entity.GroupEntity
import org.hse.demoapplication.dbal.entity.TeacherEntity
import org.hse.demoapplication.dbal.entity.TimeTableEntity
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executors
import kotlin.collections.ArrayList

class DatabaseManager(context: Context) {

    companion object {

        private var instance : DatabaseManager? = null

        fun getInstance(context: Context) : DatabaseManager? {
            if (instance == null) {
                instance = DatabaseManager(context.applicationContext)
            }
            return instance
        }
    }

    private var db : DatabaseHelper
    private val IO_EXECUTOR = Executors.newSingleThreadExecutor()

    init {
        db = Room.databaseBuilder(context, DatabaseHelper::class.java, DatabaseHelper.DATABASE_NAME)
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db : SupportSQLiteDatabase) {
                        ioThread {
                            initData(context)
                        }
                    }
                }).build()
    }
    fun getHseDao() : HseDao {
        return db.hseDao()
    }
    private fun ioThread(f : () -> Unit) {
        IO_EXECUTOR.execute(f)
    }

    fun initData(context: Context) {
        val dateFormat = "yyyy-MM-dd HH:mm"
        val formatter = SimpleDateFormat(dateFormat, Locale("ru"))
        val groups : MutableList<GroupEntity> = ArrayList()
        var group = GroupEntity(id = 1, name = "ПИ-18-1")
        groups.add(group)
        group = GroupEntity(id = 2, name = "ПИ-18-2")
        groups.add(group)
        getInstance(context)?.getHseDao()?.insertGroup(groups)

        val teachers : MutableList<TeacherEntity> = ArrayList()
        val teacher1 = TeacherEntity(id = 1, fio = "Сидоров Петр Сергеевич")
        teachers.add(teacher1)
        val teacher2 = TeacherEntity(id = 2, fio = "Иванов Петр Иванович")
        teachers.add(teacher2)
        getInstance(context)?.getHseDao()?.insertTeacher(teachers)

        val timeTables : MutableList<TimeTableEntity> = ArrayList()
        val timeTable1 = TimeTableEntity(
                id = 1,
                cabinet = "403",
                subGroup = "ПИ",
                subjName = "Компьютерная графика",
                corp = "Корпус 2",
                type = "Семинар",
                timeStart = formatter.parse("2021-03-20 00:30"),
                timeEnd = formatter.parse("2021-03-20 2:50"),
                groupId = 1,
                teacherId = 1)
        timeTables.add(timeTable1)
        val timeTable2 = TimeTableEntity(
                id = 2,
                cabinet = "502",
                subGroup = "ПИ",
                subjName = "Мобильная разработка",
                corp = "Дистанционно",
                type = "Лекция",
                timeStart = formatter.parse("2021-03-21 00:30"),
                timeEnd = formatter.parse("2021-03-21 2:50"),
                groupId = 2,
                teacherId = 2)
        timeTables.add(timeTable2)
        val timeTable3 = TimeTableEntity(
            id = 3,
            cabinet = "502",
            subGroup = "ПИ",
            subjName = "Мобильная разработка",
            corp = "Дистанционно",
            type = "Лекция",
            timeStart = formatter.parse("2021-03-22 00:30"),
            timeEnd = formatter.parse("2021-03-22 2:50"),
            groupId = 2,
            teacherId = 2)
        timeTables.add(timeTable3)
        var timeTable = TimeTableEntity(
                id = 4,
                cabinet = "502",
                subGroup = "ПИ",
                subjName = "Мобильная разработка",
                corp = "Дистанционно",
                type = "Лекция",
                timeStart = formatter.parse("2021-03-20 00:30"),
                timeEnd = formatter.parse("2021-03-20 2:50"),
                groupId = 2,
                teacherId = 2)
        timeTables.add(timeTable)
        timeTable = TimeTableEntity(
                id = 5,
                cabinet = "502",
                subGroup = "ПИ",
                subjName = "Мобильная разработка",
                corp = "Дистанционно",
                type = "Лекция",
                timeStart = formatter.parse("2021-03-20 04:30"),
                timeEnd = formatter.parse("2021-03-20 6:50"),
                groupId = 2,
                teacherId = 2)
        timeTables.add(timeTable)
        timeTable = TimeTableEntity(
                id = 6,
                cabinet = "502",
                subGroup = "ПИ",
                subjName = "Мобильная разработка",
                corp = "Дистанционно",
                type = "Лекция",
                timeStart = formatter.parse("2021-03-21 05:30"),
                timeEnd = formatter.parse("2021-03-21 6:50"),
                groupId = 2,
                teacherId = 2)
        timeTables.add(timeTable)
        getInstance(context)?.getHseDao()?.insertTimeTable(timeTables)
    }
}

