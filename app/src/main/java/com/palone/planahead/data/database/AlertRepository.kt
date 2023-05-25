package com.palone.planahead.data.database

import com.palone.planahead.data.database.alert.Alert

class AlertRepository(private val alertDao: AlertDao) {
    suspend fun upsert(alert: Alert) {
        alertDao.upsert(alert)
    }

    suspend fun delete(alert: Alert) {
        alertDao.delete(alert)
    }
}