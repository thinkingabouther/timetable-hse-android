package org.hse.demoapplication.dbal.entity

import androidx.room.*

data class TimeTableWithTeacherEntity(

    @Embedded
    val timeTableEntity: TimeTableEntity,

    @Relation(
        parentColumn = "teacher_id",
        entityColumn = "id"
    ) val teacherEntity: TeacherEntity
)