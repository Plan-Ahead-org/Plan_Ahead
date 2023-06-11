package com.palone.planahead.data.database.alert.properties

enum class AlertType {
    ALARM,                   // wake up the device and show alarm screen
    NOTIFICATION,            // send a notification
    PERSISTENT_NOTIFICATION, // send a system notification that you can't get rid of
}