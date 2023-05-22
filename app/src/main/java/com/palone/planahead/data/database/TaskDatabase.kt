package com.palone.planahead.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.palone.planahead.data.task.Task

@Database(entities = [Task::class], version = 1)
abstract class TaskDatabase : RoomDatabase() {
    abstract val dao: TaskDao
}