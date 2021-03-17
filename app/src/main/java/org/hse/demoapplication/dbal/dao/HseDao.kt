package org.hse.demoapplication.dbal.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import org.hse.demoapplication.dbal.entity.GroupEntity
import org.hse.demoapplication.dbal.entity.TeacherEntity
import org.hse.demoapplication.dbal.entity.TimeTableEntity
import org.hse.demoapplication.dbal.entity.TimeTableWithTeacherEntity

@Dao
interface HseDao {

    @Query("SELECT * FROM `group`")
    fun getAllGroup() : LiveData<List<GroupEntity>>;

    @Insert
    fun insertGroup(data : List<GroupEntity>);

    @Delete
    fun delete(data : GroupEntity);

    @Query("SELECT * FROM `teacher`")
    fun getAllTeacher() : LiveData<List<TeacherEntity>>

    @Insert
    fun insertTeacher(data : List<TeacherEntity>);

    @Delete
    fun delete(data: TeacherEntity);

    @Transaction
    @Query("SELECT * FROM time_table")
    fun getAllTimeTable() : LiveData<List<TimeTableWithTeacherEntity>>;

    @Transaction
    @Query("SELECT * FROM time_table")
    fun getTimeTableTeacher() : LiveData<List<TimeTableWithTeacherEntity>>;

    @Insert
    fun insertTimeTable(data : List<TimeTableEntity>)

}