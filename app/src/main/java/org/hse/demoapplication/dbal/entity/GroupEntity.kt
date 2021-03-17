package org.hse.demoapplication.dbal.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "group", indices = [Index(value = ["name"], unique = true)])
data class GroupEntity(

    @PrimaryKey
    val id: Int,

    @ColumnInfo(name = "name")
    val name: String
)