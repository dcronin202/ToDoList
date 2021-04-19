package com.example.todolist.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.databinding.TaskItemLayoutBinding
import com.example.todolist.model.Task

class TaskListRecyclerAdapter(private var taskList: List<Task> = listOf())
    : RecyclerView.Adapter<TaskListRecyclerAdapter.TaskListViewHolder>() {

    inner class TaskListViewHolder(val binding: TaskItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task) {
            binding.taskContent.text = task.content
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = TaskItemLayoutBinding.inflate(inflater, parent, false)
        return TaskListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskListViewHolder, position: Int) =
        holder.bind(taskList[position])

    override fun getItemCount(): Int = taskList.size

    fun updateTasks(taskList: List<Task>) {
        this.taskList = taskList
        notifyDataSetChanged()
    }
}