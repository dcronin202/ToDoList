package com.example.todolist.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todolist.model.MockTaskList
import com.example.todolist.model.Task

class MainActivityViewModel : ViewModel() {

    companion object {
        private val TAG = MainActivityViewModel::class.java.simpleName
    }

    // GET LIST OF TASKS
    private val taskListData = MutableLiveData<List<Task>>()
    val fetchTasks: LiveData<List<Task>>
        get() = taskListData

    // TRACK TASK STATE CHANGE WHEN USER ADDS, COMPLETES, OR DELETES A TASK
    private val taskStateChange = MutableLiveData<Task>()
    val fetchTaskState: LiveData<Task>
        get() = taskStateChange

    private val newTaskList = arrayListOf<Task>()
    private val mockTasks = MockTaskList.getMockTaskList()

    private fun updateLiveData(list: List<Task>) {
        taskListData.value = list
    }

    fun fetchMockTasks() {
        newTaskList.addAll(mockTasks)
        updateLiveData(newTaskList)
    }

    fun addTask(task: Task) {
        newTaskList.add(task)
        taskStateChange.postValue(task)
        updateLiveData(newTaskList)
    }

    fun completeTask(task: Task) {
        Log.d(TAG, "Original state is $task")

        // Search for task in list
        val taskInList = newTaskList.firstOrNull {
            it == task
        } ?: return

        // Update state to COMPLETE
        taskInList.complete()

        // Remove from current position in list...
        newTaskList.remove(taskInList)

        // ...and add to end of the list
        newTaskList.add(newTaskList.size, taskInList)

        taskStateChange.postValue(taskInList)
        updateLiveData(newTaskList)

        Log.d(TAG, "State updated to $task")
    }

    fun resetToCurrent(task: Task) {
        val taskInList = newTaskList.firstOrNull {
            it == task
        } ?: return

        taskInList.resetToCurrent()
        // Move to top of the list
        newTaskList.remove(taskInList)
        newTaskList.add(0, taskInList)

        taskStateChange.postValue(taskInList)
        updateLiveData(newTaskList)
    }

    fun deleteTask(task: Task) {
        val taskInList = newTaskList.firstOrNull {
            it == task
        } ?: return

        taskInList.delete()

        taskStateChange.postValue(taskInList)
        newTaskList.remove(taskInList)

        updateLiveData(newTaskList)
    }

}