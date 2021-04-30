package com.example.todolist.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.R
import com.example.todolist.databinding.ActivityMainBinding
import com.example.todolist.model.TaskState
import com.example.todolist.viewmodel.MainActivityViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.view.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var mainActivityViewModel: MainActivityViewModel
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var recyclerAdapter: TaskListRecyclerAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        // DAGGER
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        // DATA BINDING
        setupDataBinding()
        // VIEW MODEL
        setupViewModel()
        // RECYCLER VIEW OF TASKS
        setupTaskList()
        //OBSERVER
        observeTaskData()
        // FAB
        setupFab()

    }

    // SET UP DATA BINDING
    private fun setupDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this // This is required for the BindingAdapter
    }

    // SET UP VIEWMODEL
    private fun setupViewModel() {
        //mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java) // This is deprecated
        mainActivityViewModel = ViewModelProvider(this, viewModelFactory).get(MainActivityViewModel::class.java)
        binding.viewModel = mainActivityViewModel // This is required for the BindingAdapter
    }

    // SET UP TASK LIST IN RECYCLER VIEW
    private fun setupTaskList() {
        linearLayoutManager = LinearLayoutManager(this)
        recyclerAdapter = TaskListRecyclerAdapter(taskList = arrayListOf())
        binding.recyclerView.adapter = recyclerAdapter
        binding.recyclerView.layoutManager = linearLayoutManager
    }

    // OBSERVE DATA
    private fun observeTaskData() {
        // mainActivityViewModel.fetchMockTasks()
        mainActivityViewModel.fetchTasksFromDatabase(this)

        // Update recycler view when user adds/deletes a task
        mainActivityViewModel.fetchTasks.observe(this) { tasks ->
            recyclerAdapter.updateTasks(tasks)
        }

        // Display Snackbar message based on task state
        mainActivityViewModel.fetchTaskState.observe(this) { taskState ->
            fetchSnackbar(taskState)
        }
    }

    // FLOATING ACTION BUTTON
    private fun setupFab() {
        binding.coordinatorLayout.fab?.let { fab ->
            fab.setOnClickListener {
                // Open TaskEntryDialog fragment
                TaskEntryDialog().show(supportFragmentManager, "TaskEntryDialog")
            }
        }
    }

    // SNACKBAR MESSAGES FOR ADDING, COMPLETING, AND DELETING TASKS
    private fun buildSnackbarMessage(taskState: TaskState?): String? {
        return when (taskState) {
            TaskState.CURRENT ->
                getString(R.string.task_added_snackbar)
            TaskState.COMPLETED ->
                getString(R.string.task_completed_snackbar)
            TaskState.DELETED ->
                getString(R.string.task_deleted_snackbar)
            else -> null
        }
    }

    private fun fetchSnackbar(taskState: TaskState?) {
        buildSnackbarMessage(taskState)?.let { message ->
            val snack = Snackbar.make(binding.coordinatorLayout, message, Snackbar.LENGTH_LONG)
            if (taskState == TaskState.DELETED) {
                snack.setAction(getString(R.string.undo_task_deleted_snackbar)) {
                    // Restore task content if UNDO is clicked
                    mainActivityViewModel.undoDeleteTaskAction()

                }
            }
            snack.show()
        }
    }

    //**  MENU  **//

    // INFLATE CUSTOM MENU
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    // MENU DROPDOWN OPTIONS
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_clear_tasks -> {
                // Do something here
                true
            }
            R.id.menu_sort_tasks -> {
                // Do something here
                true
            }
            R.id.menu_reorder_tasks -> {
                // Do something here
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}