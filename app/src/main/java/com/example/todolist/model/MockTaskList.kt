package com.example.todolist.model

object MockTaskList {

    fun getCurrentTaskList(): List<DataListItem> {
        return listOf(
                Task(state = 0, content = "Task 1"),
                Task(state = 0, content = "Task 2"),
                Task(state = 0, content = "Task 3"),
                Task(state = 0, content = "Task 4"),
                Task(state = 0, content = "Task 5"),
                Task(state = 0, content = "Task 11"),
                Task(state = 0, content = "Task 12"),
                Task(state = 0, content = "Task 13"),
                Task(state = 0, content = "Task 14")
        )
    }

    fun getCompletedTaskList(): List<DataListItem> {
        return listOf(
            Task(state = 1, content = "Task 6"),
            Task(state = 1, content = "Task 7"),
            Task(state = 1, content = "Task 8"),
            Task(state = 1, content = "Task 9"),
            Task(state = 1, content = "Task 10"),

        )
    }
}