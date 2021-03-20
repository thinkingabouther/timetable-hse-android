package org.hse.demoapplication.dbal.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import org.hse.demoapplication.dbal.entity.GroupEntity
import org.hse.demoapplication.dbal.entity.TeacherEntity
import org.hse.demoapplication.dbal.entity.TimeTableEntity
import org.hse.demoapplication.dbal.entity.TimeTableWithTeacherEntity
import java.util.*

@Dao
interface HseDao {

    @Query("SELECT * FROM `group`")
    fun getAllGroup() : LiveData<List<GroupEntity>>

    @Insert
    fun insertGroup(data : List<GroupEntity>)

    @Delete
    fun delete(data : GroupEntity)

    @Query("SELECT * FROM `teacher`")
    fun getAllTeacher() : LiveData<List<TeacherEntity>>

    @Insert
    fun insertTeacher(data : List<TeacherEntity>)

    @Delete
    fun delete(data: TeacherEntity)

    @Transaction
    @Query("SELECT * FROM time_table")
    fun getAllTimeTable() : LiveData<List<TimeTableWithTeacherEntity>>

    @Transaction
    @Query("SELECT * FROM time_table tt WHERE tt.teacher_id = :teacherId AND tt.time_start < :date AND tt.time_end > :date LIMIT 1")
    fun getCurrentTimetableByTeacher(teacherId : Int, date : Date) : LiveData<TimeTableWithTeacherEntity>

    @Transaction
    @Query("SELECT * FROM time_table tt WHERE tt.group_id = :groupId AND tt.time_start < :date AND tt.time_end > :date LIMIT 1")
    fun getCurrentTimetableByGroup(groupId : Int, date : Date) : LiveData<TimeTableWithTeacherEntity>

    @Transaction
    @Query("SELECT * FROM time_table tt WHERE tt.group_id = :groupId AND tt.time_start > :dateFrom AND tt.time_start < :dateTo ORDER BY tt.time_start")
    fun getTimetableForTimePeriodByGroup(groupId : Int, dateFrom : Date, dateTo : Date) : LiveData<List<TimeTableWithTeacherEntity>>

    @Transaction
    @Query("SELECT * FROM time_table tt WHERE tt.teacher_id = :teacherId AND tt.time_start > :dateFrom AND tt.time_start < :dateTo ORDER BY tt.time_start")
    fun getTimetableForTimePeriodByTeacher(teacherId: Int, dateFrom : Date, dateTo : Date) : LiveData<List<TimeTableWithTeacherEntity>>

    @Insert
    fun insertTimeTable(data : List<TimeTableEntity>)

}