package com.example.todolist.viewmodel

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

    private val newTaskList = arrayListOf<Task>()
    private val mockTasks = MockTaskList.getMockTaskList()

    private fun updateLiveData(list: List<Task>) {
        taskListData.value = list
    }

    fun fetchMockTasks() {
        newTaskList.addAll(mockTasks)
        updateLiveData(newTaskList)
    }

}