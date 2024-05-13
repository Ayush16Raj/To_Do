package com.example.todolist

import com.example.todolist.data.Todo
import java.time.Instant
import java.util.Date

object TodoManager {
    private val todolist = mutableListOf<Todo>()
    fun getAllTodo(): List<Todo> {
        return todolist

    }
    fun addTodo(title: String){
todolist.add(Todo(System.currentTimeMillis().toInt(),title, Date.from(Instant.now())))
    }
    fun deleteTodo(id: Int){
        todolist.removeIf{
            it.id == id
        }

    }
}