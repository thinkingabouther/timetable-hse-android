package org.hse.demoapplication.dbal.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "teacher", indices = [Index(value = ["fio"], unique = true)])
data class TeacherEntity(

    @PrimaryKey
    val id: Int,

    @ColumnInfo(name = "fio")
    val fio: String
)