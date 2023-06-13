package com.palone.planahead.screens.taskEdit.data

import com.palone.planahead.data.database.alert.properties.AlertType
import java.time.temporal.ChronoUnit

data class AlertFieldProperty(
    val type: AlertType = AlertType.NOTIFICATION,
    val intervalValue: Int = 1,
    val intervalUnit: ChronoUnit = ChronoUnit.HOURS
)
