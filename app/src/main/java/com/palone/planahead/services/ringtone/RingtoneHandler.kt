package com.palone.planahead.services.ringtone

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.provider.Settings

class RingtoneHandler(private val ringtonePlayer: MediaPlayer = MediaPlayer()) {


    fun start(context: Context) {
        val ringtoneSoundUri: Uri = Settings.System.DEFAULT_RINGTONE_URI
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_ALARM)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
        ringtonePlayer.setAudioAttributes(audioAttributes)
        ringtonePlayer.isLooping = true
        ringtonePlayer.setDataSource(context, ringtoneSoundUri)
        ringtonePlayer.prepare()
        ringtonePlayer.start()

    }

    fun stop() {
        ringtonePlayer.stop()
    }

}