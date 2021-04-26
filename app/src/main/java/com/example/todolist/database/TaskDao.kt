package com.example.todolist.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskDao {

    @Insert
    fun insertTask(vararg task: TaskEntity)

    @Update
    fun updateTask(vararg task: TaskEntity)

    @Delete
    fun delete(task: TaskEntity)

    @Query("SELECT * FROM task_items")
    fun getAllTasks(): LiveData<List<TaskEntity>>

    @Query("delete from task_items")
    fun deleteAllTasks()

}