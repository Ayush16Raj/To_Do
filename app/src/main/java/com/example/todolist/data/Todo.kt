package com.example.todolist.data

import android.os.Build
import java.time.Instant
import java.util.Date

data class Todo(
    var id: Int,
    var title: String,
    var date: Date
)

fun dummyTodo(): List<Todo>{
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        listOf<Todo>(
            Todo(1,"DSA",Date.from(Instant.now())),
            Todo(2,"Android",Date.from(Instant.now())),
            Todo(3,"Movie",Date.from(Instant.now())),
            Todo(4,"Gym",Date.from(Instant.now())),
                    Todo(1,"DSA",Date.from(Instant.now())),
        Todo(2,"Android",Date.from(Instant.now())),
        Todo(3,"Movie",Date.from(Instant.now())),
        Todo(4,"Gym",Date.from(Instant.now())),
            Todo(1,"DSA",Date.from(Instant.now())),
            Todo(2,"Android",Date.from(Instant.now())),
            Todo(3,"Movie",Date.from(Instant.now())),
            Todo(4,"Gym",Date.from(Instant.now()))
        )
    } else {
        TODO("VERSION.SDK_INT < O")
    }
}

