package com.example.todolist.database

import androidx.lifecycle.LiveData
import com.example.todolist.model.Task
import com.example.todolist.utils.subscribeOnBackground
import javax.inject.Inject

class TaskRepository @Inject constructor(private val taskDao: TaskDao) {

    fun insert(task: Task) {
        subscribeOnBackground {
            val taskItem = TaskEntity(task.id, task.state, task.content)
            taskDao.insertTask(taskItem)
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
        return taskDao.getAllTasks()
    }
}