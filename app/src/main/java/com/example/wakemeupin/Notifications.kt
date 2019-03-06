package com.example.wakemeupin

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import android.support.v7.app.AppCompatActivity

class Notifications {
  companion object {
    private lateinit var notificationManager: NotificationManager
    private var channelID : Long = 0

    fun getNotificationManager() : NotificationManager {
      return notificationManager
    }

    fun getChannelID() : Long {
      return channelID
    }

    fun createChannel(context: Context?) {
      notificationManager = context!!.getSystemService(AppCompatActivity.NOTIFICATION_SERVICE)
              as NotificationManager
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val v = longArrayOf(0, 500, 110, 500, 110, 450, 110, 200, 110, 170, 40, 450, 110, 200, 110, 170, 40, 500)
        //TODO (SL) Read in file from user.
        val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        // Create the NotificationChannel
        val name = context.getString(R.string.channel_name)
        val descriptionText = context.getString(R.string.channel_description)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        channelID = System.currentTimeMillis() / 1000
        val mChannel = NotificationChannel(channelID.toString(), name, importance)
        mChannel.vibrationPattern = v
        mChannel.description = descriptionText
        mChannel.setSound(
            uri,
            AudioAttributes.Builder()
              .setUsage(AudioAttributes.USAGE_ALARM)
              .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
              .build()
        )
        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        notificationManager.createNotificationChannel(mChannel)
      }
    }
  }
}
