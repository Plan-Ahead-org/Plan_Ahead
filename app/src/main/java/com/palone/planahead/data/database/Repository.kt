package com.palone.planahead.data.database

open class Repository<T>(private val dao: Dao<T>) {
    suspend fun upsert(data: T): Long {
        return dao.upsert(data)
    }

    suspend fun delete(data: T) {
        dao.delete(data)
    }
}