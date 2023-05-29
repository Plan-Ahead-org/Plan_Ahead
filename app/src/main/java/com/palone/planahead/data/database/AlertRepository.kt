package com.palone.planahead.data.database

import com.palone.planahead.data.database.alert.Alert

class AlertRepository(alertDao: AlertDao) : Repository<Alert>(alertDao)