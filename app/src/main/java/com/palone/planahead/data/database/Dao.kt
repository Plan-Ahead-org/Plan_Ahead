package com.palone.planahead.data.database

import androidx.room.Delete
import androidx.room.Upsert

interface Dao<T> {
    @Upsert
    suspend fun upsert(data: T): Long

    @Delete
    suspend fun delete(data: T)
}