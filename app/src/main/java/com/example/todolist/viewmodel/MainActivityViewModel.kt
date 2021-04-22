package com.example.todolist.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todolist.R
import com.example.todolist.model.DataListItem
import com.example.todolist.model.Header
import com.example.todolist.model.MockTaskList
import com.example.todolist.model.Task

class MainActivityViewModel : ViewModel() {

    companion object {
        private val TAG = MainActivityViewModel::class.java.simpleName
    }

    // GET LIST OF TASKS
    private val taskListData = MutableLiveData<List<DataListItem>>()
    val fetchTasks: LiveData<List<DataListItem>>
        get() = taskListData

    // TRACK TASK STATE CHANGE WHEN USER ADDS, COMPLETES, OR DELETES A TASK
    private val taskStateChange = MutableLiveData<Task>()
    val fetchTaskState: LiveData<Task>
        get() = taskStateChange

    private val currentTaskList = arrayListOf<DataListItem>()
    private val completedTaskList = arrayListOf<DataListItem>()

    private val mockCurrentTasks = MockTaskList.getCurrentTaskList()
    private val mockCompletedTasks = MockTaskList.getCompletedTaskList()

    private fun updateLiveData() {
        val updatedList = mutableListOf<DataListItem>()
        val currentTaskHeader = Header(R.string.header_current_tasks)
        val completedTaskHeader = Header(R.string.header_completed_tasks)

        if (currentTaskList.isNotEmpty()) updatedList.add(currentTaskHeader)
        updatedList.addAll(currentTaskList)
        if (completedTaskList.isNotEmpty()) updatedList.add(completedTaskHeader)
        updatedList.addAll(completedTaskList)

        // Construct items in list
        taskListData.value = updatedList
    }

    fun fetchMockTasks() {
        val updatedList = mutableListOf<DataListItem>()
        val currentTaskHeader = Header(R.string.header_current_tasks)
        val completedTaskHeader = Header(R.string.header_completed_tasks)

        updatedList.add(currentTaskHeader)
        updatedList.addAll(mockCurrentTasks)
        updatedList.add(completedTaskHeader)
        updatedList.addAll(mockCompletedTasks)

        updateLiveData()
    }

    fun addTask(taskContent: String) {
        val newTask = Task(state = 0, content = taskContent).apply {
            completeTaskAction = { task ->
                completeTask(task)
            }
            resetTaskAction = { task ->
                resetToCurrent(task)
            }
            deleteTaskAction = { task ->
                deleteTask(task)
            }
        }
        currentTaskList.add(newTask)
        taskStateChange.postValue(newTask)
        updateLiveData()
    }

    private fun completeTask(task: Task) {
        Log.d(TAG, "Original state is $task")

        // Search for task in list
        val itemInList = currentTaskList.firstOrNull {
            // Verify model type and the correct data
            it is Task && it == task
        }

        val taskInList = itemInList as? Task ?: return

        // Update state to COMPLETE
        taskInList.complete()

        // Remove task from current list and add to completed list
        currentTaskList.remove(taskInList)
        completedTaskList.add(taskInList)
        taskStateChange.postValue(taskInList)
        updateLiveData()

        Log.d(TAG, "State updated to $task")
    }

    private fun resetToCurrent(task: Task) {
        val itemInList = completedTaskList.firstOrNull {
            it is Task && it == task
        } ?: return

        val taskInList = itemInList as? Task ?: return

        // Update state to CURRENT
        taskInList.resetToCurrent()

        // Remove task from complete list and add it back to current list
        completedTaskList.remove(taskInList)
        currentTaskList.add(0, taskInList)

        taskStateChange.postValue(taskInList)
        updateLiveData()
    }

    private fun deleteTask(task: Task) {
        val itemInList = completedTaskList.firstOrNull {
            it is Task && it == task
        } ?: return

        val taskInList = itemInList as? Task?: return

        // Update state to DELETED
        taskInList.delete()

        // Remove task from completed task list
        taskStateChange.postValue(taskInList)
        completedTaskList.remove(taskInList)
        updateLiveData()
    }

}