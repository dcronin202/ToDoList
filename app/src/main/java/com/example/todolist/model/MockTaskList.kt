package com.example.todolist.model

object MockTaskList {

    fun getCurrentTaskList(): List<DataListItem> {
        return listOf(
                Task(id = 0, state = 0, content = "Task 1"),
                Task(id = 0, state = 0, content = "Task 2"),
                Task(id = 0, state = 0, content = "Task 3"),
                Task(id = 0, state = 0, content = "Task 4"),
                Task(id = 0, state = 0, content = "Task 5"),
                Task(id = 0, state = 0, content = "Task 11"),
                Task(id = 0, state = 0, content = "Task 12"),
                Task(id = 0, state = 0, content = "Task 13"),
                Task(id = 0, state = 0, content = "Task 14")
        )
    }

    fun getCompletedTaskList(): List<DataListItem> {
        return listOf(
            Task(id = 0, state = 1, content = "Task 6"),
            Task(id = 0, state = 1, content = "Task 7"),
            Task(id = 0, state = 1, content = "Task 8"),
            Task(id = 0, state = 1, content = "Task 9"),
            Task(id = 0, state = 1, content = "Task 10"),

        )
    }
}