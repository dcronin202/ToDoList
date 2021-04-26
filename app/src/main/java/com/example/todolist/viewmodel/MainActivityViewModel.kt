package com.example.todolist.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todolist.R
import com.example.todolist.database.TaskRepository
import com.example.todolist.model.*

class MainActivityViewModel : ViewModel() {

    companion object {
        private val TAG = MainActivityViewModel::class.java.simpleName
    }

    private lateinit var tasksInDatabase: TaskRepository

    private var currentTaskHeader = Header(R.string.header_current_tasks)
    private var completedTaskHeader = Header(R.string.header_completed_tasks)
    private var pendingDeleteTask: Task? = null

    // GET LIST OF TASKS
    private val taskListData = MutableLiveData<List<DataListItem>>()
    val fetchTasks: LiveData<List<DataListItem>>
        get() = taskListData

    // TRACK TASK STATE CHANGE WHEN USER ADDS, COMPLETES, OR DELETES A TASK
    private val taskStateChange = MutableLiveData<TaskState>()
    val fetchTaskState: LiveData<TaskState>
        get() = taskStateChange


    private fun updateLiveData(currentTaskList: List<DataListItem>, completedTaskList: List<DataListItem>) {
        val updatedList = mutableListOf<DataListItem>()

        if (currentTaskList.isNotEmpty()) updatedList.add(currentTaskHeader)
        updatedList.addAll(currentTaskList)
        if (completedTaskList.isNotEmpty()) updatedList.add(completedTaskHeader)
        updatedList.addAll(completedTaskList)

        // Construct items in list
        taskListData.value = updatedList
    }

    fun fetchTasksFromDatabase(owner: LifecycleOwner, application: Application) {
        tasksInDatabase = TaskRepository(application)
        // Get list of TaskEntities from database and convert to Tasks
        tasksInDatabase.getAllTasks().observe(owner) { tasks ->
            val taskList: List<Task> = tasks.map { taskEntity ->
                Task(id = taskEntity.id, state = taskEntity.state, content = taskEntity.content).apply {
                    completeTaskAction = {
                        completeTask(it)
                    }
                    resetTaskAction = {
                        resetToCurrent(it)
                    }
                    deleteTaskAction = {
                        deleteTask(it)
                        //createTempTaskAndDelete(it)
                    }
                }
            }

            val currentTasks = taskList.filter { it.getStateAsEnum() == TaskState.CURRENT }
            val completedTasks = taskList.filter { it.getStateAsEnum() == TaskState.COMPLETED }

            // Pass current and completed task lists to updateLiveData function
            updateLiveData(currentTasks, completedTasks)
        }
    }

    fun fetchMockTasks() {
        val mockCurrentTasks = MockTaskList.getCurrentTaskList()
        val mockCompletedTasks = MockTaskList.getCompletedTaskList()

        updateLiveData(mockCurrentTasks, mockCompletedTasks)
    }

    fun addTask(taskContent: String) {
        val newTask = Task(id = 0, state = 0, content = taskContent)
        tasksInDatabase.insert(newTask)
        taskStateChange.postValue(TaskState.CURRENT)
    }

    private fun completeTask(task: Task) {
        task.complete()
        tasksInDatabase.update(task)
        taskStateChange.postValue(TaskState.COMPLETED)
    }

    private fun resetToCurrent(task: Task) {
        task.resetToCurrent()
        tasksInDatabase.update(task)
        taskStateChange.postValue(TaskState.CURRENT)
    }

    fun undoDeleteTaskAction() {
        val backupTask = pendingDeleteTask?.let { Task(id = 0, state = 0, content = it.content) }
        if (backupTask != null) {
            tasksInDatabase.insert(backupTask)
        }
    }

    private fun deleteTask(task: Task) {
        pendingDeleteTask = task
        Log.d(TAG, "duplicate task = ${pendingDeleteTask!!.content}")
        task.delete()
        tasksInDatabase.delete(task)
        taskStateChange.postValue(TaskState.DELETED)
    }

}