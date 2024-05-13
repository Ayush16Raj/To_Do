package com.example.todolist.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.todolist.DateConverter
import java.util.Date
@Entity
@TypeConverters(DateConverter::class)
data class Todo(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var title: String,
    var date: Date
)




