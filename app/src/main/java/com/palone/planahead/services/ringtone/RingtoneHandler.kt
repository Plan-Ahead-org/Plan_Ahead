package com.palone.planahead.services.ringtone

import android.content.Context

interface RingtoneHandler {
    fun start(context: Context)
    fun stop()
}