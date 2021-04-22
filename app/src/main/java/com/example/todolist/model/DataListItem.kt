package com.example.todolist.model

import androidx.annotation.LayoutRes
import androidx.viewbinding.ViewBinding

interface DataListItem {

    val layoutId: Int
        @LayoutRes get

    fun bind(binding: ViewBinding)

}