package com.example.todolist.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.todolist.model.TaskState

@Entity(tableName = "task_items")
data class TaskEntity(
        @PrimaryKey(autoGenerate = true) var id: Int = 0,

        @ColumnInfo(name = "state") var state: Int,
        @ColumnInfo(name = "content") var content: String
)
