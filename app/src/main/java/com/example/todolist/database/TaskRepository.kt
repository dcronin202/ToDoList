package com.example.todolist.database

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.todolist.model.Task
import com.example.todolist.utils.subscribeOnBackground

class TaskRepository(application: Application) {

    private var taskDao: TaskDao
    private var allTasks: LiveData<List<TaskEntity>>
    private val database = TaskDatabase.getInstance(application)

    init {
        taskDao = database.taskDao()
        allTasks = taskDao.getAllTasks()
    }

    fun insert(task: TaskEntity) {
        subscribeOnBackground {
            taskDao.insertTask(task)
        }
    }

    fun update(task: Task) {
        subscribeOnBackground {
            val taskItem = TaskEntity(task.id, task.state, task.content)
            taskDao.updateTask(taskItem)
        }
    }

    fun delete(task: Task) {
        subscribeOnBackground {
            val taskItem = TaskEntity(task.id, task.state, task.content)
            taskDao.delete(taskItem)
        }
    }

    fun deleteAllTasks() {
        subscribeOnBackground {
            taskDao.deleteAllTasks()
        }
    }

    fun getAllTasks() : LiveData<List<TaskEntity>> {
        return allTasks
    }
}