package org.hse.demoapplication.dbal.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.google.gson.internal.bind.TimeTypeAdapter
import org.hse.demoapplication.dbal.db.DatabaseManager
import org.hse.demoapplication.dbal.entity.GroupEntity
import org.hse.demoapplication.dbal.entity.TeacherEntity
import org.hse.demoapplication.dbal.entity.TimeTableWithTeacherEntity
import java.util.*

class HseRepository(context: Context) {
    private val database : DatabaseManager = DatabaseManager.getInstance(context)!!
    private val hseDao = database.getHseDao()

    fun getGroups(): LiveData<List<GroupEntity>> {
        return hseDao.getAllGroup()
    }

    fun getTeachers(): LiveData<List<TeacherEntity>> {
        return hseDao.getAllTeacher()
    }

    fun getCurrentTimetableByTeacher(teacherId: Int, date: Date): LiveData<TimeTableWithTeacherEntity> {
        return hseDao.getCurrentTimetableByTeacher(teacherId, date)
    }

    fun getCurrentTimetableByGroup(groupId: Int, date: Date): LiveData<TimeTableWithTeacherEntity> {
        return hseDao.getCurrentTimetableByGroup(groupId, date)
    }

    fun getTimetableForDayByGroup(groupId: Int, date: Date) : LiveData<List<TimeTableWithTeacherEntity>> {
        return hseDao.getTimetableForTimePeriodByGroup(groupId, getStartOfDay(date), getEndOfDay(date))
    }

    fun getTimetableForWeekByGroup(groupId: Int, date: Date) : LiveData<List<TimeTableWithTeacherEntity>> {
        return hseDao.getTimetableForTimePeriodByGroup(groupId, getStartOfDay(date), getEndOfWeek(date))
    }

    fun getTimetableForDayByTeacher(teacherId: Int, date: Date) : LiveData<List<TimeTableWithTeacherEntity>> {
        return hseDao.getTimetableForTimePeriodByTeacher(teacherId, getStartOfDay(date), getEndOfDay(date))
    }

    fun getTimetableForWeekByTeacher(teacherId: Int, date: Date) : LiveData<List<TimeTableWithTeacherEntity>> {
        return hseDao.getTimetableForTimePeriodByTeacher(teacherId, getStartOfDay(date), getEndOfWeek(date))
    }

    private fun getStartOfDay(date: Date): Date {
        date.hours = 0
        date.minutes = 0
        return date
    }

    private fun getEndOfDay(date: Date): Date {
        date.hours = 23
        date.minutes = 59
        return date
    }

    private fun getEndOfWeek(date: Date): Date {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.MONTH, date.month)
        calendar.set(Calendar.DAY_OF_YEAR, date.day)
        calendar.firstDayOfWeek = Calendar.MONDAY
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
        val newDate = calendar.time
        newDate.hours = 23
        newDate.minutes = 59
        return newDate
    }
}