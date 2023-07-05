package com.palone.planahead.screens.taskEdit.data

import com.palone.planahead.data.database.alert.properties.AlertType
import java.time.LocalTime

data class AlertProperty(
    val type: AlertType = AlertType.NOTIFICATION,
    val offsetTime: LocalTime
)
