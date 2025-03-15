package com.example.todolistapp

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todolistapp.db.ToDoDatabase

class MainApplication : Application() {


    companion object{
        lateinit var toDoDatabase: ToDoDatabase
    }

    override fun onCreate() {
        super.onCreate()
        Room.databaseBuilder(
            applicationContext,
            ToDoDatabase::class.java,
            ToDoDatabase.NAME
        ).build()
    }
}