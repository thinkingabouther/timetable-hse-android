package org.hse.demoapplication.dbal.entity

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import java.util.*

@Entity(
    tableName = "time_table",
    foreignKeys = [
    ForeignKey(
        entity = GroupEntity::class,
        parentColumns = ["id"],
        childColumns = ["group_id"],
        onDelete = CASCADE
    ),
    ForeignKey(
        entity = TeacherEntity::class,
        parentColumns = ["id"],
        childColumns = ["teacher_id"],
        onDelete = CASCADE
    )
])
data class TimeTableEntity (
    @PrimaryKey
    val id : Int,

    @ColumnInfo(name = "subj_name")
    val subjName : String,

    @ColumnInfo(name = "type")
    val type : Int,

    @ColumnInfo(name = "time_start")
    val timeStart : Date?,

    @ColumnInfo(name = "time_end")
    val timeEnd : Date?,

    @ColumnInfo(name = "sub_group")
    val subGroup : String,

    @ColumnInfo(name = "cabinet")
    val cabinet : String,

    @ColumnInfo(name = "corp")
    val corp : String,

    @ColumnInfo(name = "group_id", index = true)
    val groupId : Int?,

    @ColumnInfo(name = "teacher_id", index = true)
    val teacherId : Int?
)