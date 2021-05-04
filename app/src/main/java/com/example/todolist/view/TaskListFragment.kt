package com.example.todolist.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.R
import com.example.todolist.databinding.TaskListFragmentBinding
import com.example.todolist.model.TaskState
import com.example.todolist.viewmodel.MainActivityViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.AndroidSupportInjection
import java.lang.IllegalStateException
import javax.inject.Inject

class TaskListFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var mainActivityViewModel: MainActivityViewModel
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var recyclerAdapter: TaskListRecyclerAdapter
    private lateinit var binding: TaskListFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.task_list_fragment, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViewModel()
        setUpTaskList()
        observeTaskData()
        setUpFab()
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    // SET UP VIEW MODEL
    private fun setUpViewModel() {
        val viewModelStoreOwner: ViewModelStoreOwner = activity as? ViewModelStoreOwner
                ?: throw IllegalStateException("Activity cannot be cast to ViewModelStoreOwner")
        mainActivityViewModel = ViewModelProvider(viewModelStoreOwner, viewModelFactory).get(MainActivityViewModel::class.java)
        binding.viewModel = mainActivityViewModel
    }

    // SET UP TASK LIST IN RECYCLER VIEW
    private fun setUpTaskList() {
        linearLayoutManager = LinearLayoutManager(context)
        recyclerAdapter = TaskListRecyclerAdapter(taskList = arrayListOf())
        binding.recyclerView.layoutManager = linearLayoutManager
        binding.recyclerView.adapter = recyclerAdapter
    }

    // OBSERVE DATA
    private fun observeTaskData() {
        mainActivityViewModel.fetchTasksFromDatabase(this)

        mainActivityViewModel.fetchTasks.observe(viewLifecycleOwner) { task ->
            recyclerAdapter.updateTasks(task)
        }

        mainActivityViewModel.fetchTaskState.observe(viewLifecycleOwner) { task ->
            fetchSnackbar(task)
        }
    }

    // FLOATING ACTION BUTTON CLICK LISTENER
    private fun setUpFab() {
        binding.fab.setOnClickListener {
            activity?.supportFragmentManager?.let { it1 -> TaskEntryDialog().show(it1, "TaskEntryDialog") }
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
}