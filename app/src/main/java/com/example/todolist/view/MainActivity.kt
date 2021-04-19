package com.example.todolist.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.R
import com.example.todolist.databinding.ActivityMainBinding
import com.example.todolist.viewmodel.MainActivityViewModel

class MainActivity : AppCompatActivity() {

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

    private lateinit var mainActivityViewModel: MainActivityViewModel
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var recyclerAdapter: TaskListRecyclerAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // DATA BINDING
        setupDataBinding()
        // VIEW MODEL
        setupViewModel()
        // RECYCLER VIEW OF TASKS
        setupTaskList()
        //OBSERVER
        observeTaskData()

    }

    // SET UP DATA BINDING
    private fun setupDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    // SET UP VIEWMODEL
    private fun setupViewModel() {
        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
    }

    // SET UP TASK LIST IN RECYCLER VIEW
    private fun setupTaskList() {
        linearLayoutManager = LinearLayoutManager(this)
        recyclerAdapter = TaskListRecyclerAdapter(
            taskList = arrayListOf(),
            completeTaskAction = { },
            deleteTaskAction = { task ->
                mainActivityViewModel.deleteTask(task)
            }
        )
        binding.recyclerView.adapter = recyclerAdapter
        binding.recyclerView.layoutManager = linearLayoutManager
    }

    // OBSERVE DATA
    private fun observeTaskData() {
        mainActivityViewModel.fetchMockTasks()

        mainActivityViewModel.fetchTasks.observe(this) { tasks ->
            recyclerAdapter.updateTasks(tasks)
        }

    }

}