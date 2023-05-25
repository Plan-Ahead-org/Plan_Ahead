package com.palone.planahead.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.palone.planahead.data.database.alert.Alert
import com.palone.planahead.data.database.task.Task

@Database(entities = [Task::class, Alert::class], version = 1)
abstract class TaskAndAlertDatabase : RoomDatabase() {
    abstract val dao: TaskAndAlertDao
}