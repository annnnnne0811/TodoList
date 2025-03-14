package com.example.todolistapp

import java.time.Instant
import java.util.Date

data class Todo(
    var id : Int,
    var title : String,
    var made: Date
)



fun getFakeTodo(): List<Todo>{
    return listOf<Todo>(
        Todo(id = 1, title = "First todo ",Date.from(Instant.now())),
        Todo(id = 2, title = "second todo ",Date.from(Instant.now())),
        Todo(id = 3, title = "Third todo ",Date.from(Instant.now())),
        Todo(id = 4, title = "Fourth todo ",Date.from(Instant.now()))

    )
}