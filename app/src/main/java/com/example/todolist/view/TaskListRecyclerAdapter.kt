package com.example.todolist.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.todolist.R
import com.example.todolist.databinding.TaskItemHeaderBinding
import com.example.todolist.databinding.TaskItemLayoutBinding
import com.example.todolist.model.DataListItem
import com.example.todolist.model.Task

class TaskListRecyclerAdapter(private var taskList: List<DataListItem> = listOf())
    : RecyclerView.Adapter<TaskListRecyclerAdapter.TaskListViewHolder>() {

    companion object {
        private const val TASK_ITEM = R.layout.task_item_layout
    }

    inner class TaskListViewHolder(val binding: ViewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int = taskList.size

    override fun getItemViewType(position: Int): Int {
        return taskList[position].layoutId
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = if (viewType == TASK_ITEM) {
            TaskItemLayoutBinding.inflate(inflater, parent, false)
        } else {
            TaskItemHeaderBinding.inflate(inflater, parent, false)
        }
        return TaskListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskListViewHolder, position: Int) {
        val listItem = taskList[position]
        listItem.bind(holder.binding)
    }

    fun updateTasks(taskList: List<DataListItem>) {
        this.taskList = taskList
        notifyDataSetChanged()
    }
}