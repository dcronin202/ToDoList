package com.example.todolist.utils

import android.graphics.Paint
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.todolist.model.DataListItem
import com.example.todolist.model.Task
import com.example.todolist.model.TaskState

@BindingAdapter ("start_view_visible")
fun updateStartViewVisibility(view: TextView, taskList: List<DataListItem>?) {
    taskList?.let { tasks ->
        view.visibility = if (tasks.isEmpty()) View.VISIBLE else View.GONE
    }
}

@BindingAdapter ("current_state_visible")
fun updateCurrentStateVisibility(view: ImageView, state: TaskState?) {
    state?.let { taskState ->
        view.visibility = if (taskState == TaskState.CURRENT) View.VISIBLE else View.GONE
    }
}

@BindingAdapter ("completed_state_visible")
fun updateCompletedStateVisibility(view: ImageView, state: TaskState?) {
    state?.let { taskState ->
        view.visibility = if (taskState == TaskState.COMPLETED) View.VISIBLE else View.GONE
    }
}

@BindingAdapter ("strike_through")
fun strikeThroughCompletedText(view: TextView, state: TaskState?) {
    state?.let { taskState ->
        if (taskState == TaskState.COMPLETED) view.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            else view.paintFlags = Paint.ANTI_ALIAS_FLAG
    }
}