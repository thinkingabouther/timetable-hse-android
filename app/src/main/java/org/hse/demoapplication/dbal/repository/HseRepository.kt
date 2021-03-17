package org.hse.demoapplication.dbal.repository

import android.content.Context
import androidx.lifecycle.LiveData
import org.hse.demoapplication.dbal.db.DatabaseManager
import org.hse.demoapplication.dbal.entity.GroupEntity
import org.hse.demoapplication.dbal.entity.TeacherEntity
import org.hse.demoapplication.dbal.entity.TimeTableWithTeacherEntity
import java.util.*

class HseRepository(context: Context) {
    private val database : DatabaseManager = DatabaseManager.getInstance(context)!!
    private val hseDao = database.getHseDao();

    fun getGroups(): LiveData<List<GroupEntity>> {
        return hseDao.getAllGroup();
    }

    fun getTeachers(): LiveData<List<TeacherEntity>> {
        return hseDao.getAllTeacher();
    }

    fun getTimeTableTeacherByDate(date : Date): LiveData<List<TimeTableWithTeacherEntity>> {
        return hseDao.getTimeTableTeacher();
    }
}