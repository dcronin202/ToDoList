package com.example.todolist.model

import androidx.annotation.StringRes
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import com.example.todolist.R
import com.example.todolist.databinding.TaskItemHeaderBinding

class Header(@StringRes private val content: Int) : DataListItem {

    private var binding: TaskItemHeaderBinding? = null

    override fun bind(binding: ViewBinding) {
        this.binding = binding as TaskItemHeaderBinding
        binding.headerTextView.text = binding.root.context.getString(content)
        binding.lifecycleOwner = binding.root.context as LifecycleOwner
    }

    override val layoutId: Int = R.layout.task_item_header

}
