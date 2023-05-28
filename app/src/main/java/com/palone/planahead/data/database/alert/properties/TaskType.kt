package com.palone.planahead.data.database.alert.properties

enum class TaskType {
    ONE_TIME, // task doesn't repeat
    CRON,  // task repeated by schedule (day of week, month, year ect.)
    CHORE,  // task repeated by specified time
}

