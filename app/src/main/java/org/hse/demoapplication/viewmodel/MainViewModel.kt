package org.hse.demoapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import org.hse.demoapplication.dbal.entity.GroupEntity
import org.hse.demoapplication.dbal.entity.TeacherEntity
import org.hse.demoapplication.dbal.entity.TimeTableWithTeacherEntity
import org.hse.demoapplication.dbal.repository.HseRepository
import java.util.*

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val hseRepository : HseRepository = HseRepository(application)

    fun getGroups(): LiveData<List<GroupEntity>> {
        return hseRepository.getGroups()
    }

    fun getTeachers(): LiveData<List<TeacherEntity>> {
        return hseRepository.getTeachers()
    }

    fun getCurrentTimetableByTeacher(teacherId: Int, date: Date): LiveData<TimeTableWithTeacherEntity> {
        return hseRepository.getCurrentTimetableByTeacher(teacherId, date)
    }

    fun getCurrentTimetableByGroup(groupId: Int, date: Date): LiveData<TimeTableWithTeacherEntity> {
        return hseRepository.getCurrentTimetableByGroup(groupId, date)
    }

    fun getTimetableForDayByGroup(groupId: Int, date: Date) : LiveData<List<TimeTableWithTeacherEntity>> {
        return hseRepository.getTimetableForDayByGroup(groupId, date)
    }

    fun getTimetableForWeekByGroup(groupId: Int, date: Date) : LiveData<List<TimeTableWithTeacherEntity>> {
        return hseRepository.getTimetableForWeekByGroup(groupId, date)
    }

    fun getTimetableForDayByTeacher(teacherId: Int, date: Date) : LiveData<List<TimeTableWithTeacherEntity>> {
        return hseRepository.getTimetableForDayByTeacher(teacherId, date)
    }

    fun getTimetableForWeekByTeacher(teacherId: Int, date: Date) : LiveData<List<TimeTableWithTeacherEntity>> {
        return hseRepository.getTimetableForWeekByTeacher(teacherId, date)
    }
}