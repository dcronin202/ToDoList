package com.example.todolist.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.R
import com.example.todolist.databinding.TaskListFragmentBinding
import com.example.todolist.viewmodel.MainActivityViewModel
import dagger.android.support.AndroidSupportInjection
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
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    // SET UP VIEW MODEL
    private fun setUpViewModel() {
        mainActivityViewModel = ViewModelProvider(this, viewModelFactory).get(MainActivityViewModel::class.java)
        binding.viewModel = mainActivityViewModel
    }

    // SET UP TASK LIST IN RECYCLER VIEW
    private fun setUpTaskList() {
        linearLayoutManager = LinearLayoutManager(context)
        recyclerAdapter = TaskListRecyclerAdapter(taskList = arrayListOf())
        binding.recyclerView.layoutManager = linearLayoutManager
        binding.recyclerView.adapter = recyclerAdapter
    }

    private fun observeTaskData() {
        mainActivityViewModel.fetchTasksFromDatabase(this)

        mainActivityViewModel.fetchTasks.observe(viewLifecycleOwner) { task ->
            recyclerAdapter.updateTasks(task)
        }

        mainActivityViewModel.fetchTaskState.observe(viewLifecycleOwner) { task ->
            // TODO - snackbar message
        }

    }
}