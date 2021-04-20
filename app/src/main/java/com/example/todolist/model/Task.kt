package com.example.todolist.model

data class Task(private var state: Int, val content: String) {

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
