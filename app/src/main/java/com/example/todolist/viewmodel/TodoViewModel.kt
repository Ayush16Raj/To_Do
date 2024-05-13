package com.example.todolist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.ApplicationClass
import com.example.todolist.data.Todo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Instant
import java.util.Date

class TodoViewModel: ViewModel() {
    val todoDao = ApplicationClass.todoDatabase.getTodoDao()
    val todoList: LiveData<List<Todo>> = todoDao.getAllTodo()

    fun addTodo(title: String) {
        viewModelScope.launch(Dispatchers.IO) {
            todoDao.addTodo(Todo(title = title, date = Date.from(Instant.now())))

        }
    }

    fun deleteTodo(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            todoDao.deleteTodo(id)

        }

    }

    fun updateTodo(todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            todoDao.updateTodo(todo)
        }

    }
}