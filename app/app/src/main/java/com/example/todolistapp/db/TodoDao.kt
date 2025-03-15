package com.example.todolistapp.db

import androidx.compose.ui.input.pointer.PointerId
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.*
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.todolistapp.Todo

@Dao
interface TodoDao{

    @Query("SELECT * FROM Todo")
    fun getAllTodo():LiveData<List<Todo>>

    @Insert
    fun addTodo(todo:Todo)

    @Query("DELETE FROM todo WHERE id = :id")
    fun deleteTodo(id: Int)

}