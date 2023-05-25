package com.palone.planahead.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Upsert
import com.palone.planahead.data.database.alert.Alert

@Dao
interface AlertDao {

    @Upsert
    suspend fun upsert(alert: Alert): Long

    @Delete
    suspend fun delete(alert: Alert)
}