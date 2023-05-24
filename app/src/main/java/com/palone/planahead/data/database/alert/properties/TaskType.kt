package com.palone.planahead.data.database.alert.properties

enum class TaskType {
    ONE_TIME, // one time event
    CRON,  // repeated event by schedule
    CHORE,  // repeated event by time
}

