package com.example.todolist.model

import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import com.example.todolist.R
import com.example.todolist.databinding.TaskItemLayoutBinding

data class Task(val id: Int, var state: Int, val content: String) : DataListItem {

    var completeTaskAction: ((Task) -> Unit)? = null
    var resetTaskAction: ((Task) -> Unit)? = null
    var deleteTaskAction: ((Task) -> Unit)? = null

    private var binding: TaskItemLayoutBinding? = null

    override fun bind(binding: ViewBinding) {
        this.binding = binding as TaskItemLayoutBinding
        binding.task = this
        binding.taskContent.text = content
        binding.lifecycleOwner = binding.root.context as LifecycleOwner

        binding.clickToComplete.setOnClickListener { completeTaskAction?.invoke(this) }
        binding.clickToReset.setOnClickListener { resetTaskAction?.invoke(this) }
        binding.clickToDelete.setOnClickListener { deleteTaskAction?.invoke(this) }
    }

    override val layoutId: Int = R.layout.task_item_layout

    fun getStateAsEnum(): TaskState? {
        return when (state) {
            TaskState.CURRENT.ordinal -> TaskState.CURRENT
            TaskState.COMPLETED.ordinal -> TaskState.COMPLETED
            TaskState.DELETED.ordinal -> TaskState.DELETED
            else -> null
        }
    }
    
    fun complete() {
        state = TaskState.COMPLETED.ordinal
    }

    fun resetToCurrent() {
        state = TaskState.CURRENT.ordinal
    }

    fun delete() {
        state = TaskState.DELETED.ordinal
    }

}
