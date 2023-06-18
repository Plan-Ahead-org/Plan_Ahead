package com.palone.planahead.screens.taskEdit.data

import com.palone.planahead.data.database.alert.properties.AlertType
import java.time.temporal.ChronoUnit

data class AlertProperty(
    val type: AlertType = AlertType.NOTIFICATION,
    val offsetValue: Int = 1,
    val offsetUnit: ChronoUnit = ChronoUnit.HOURS
)
