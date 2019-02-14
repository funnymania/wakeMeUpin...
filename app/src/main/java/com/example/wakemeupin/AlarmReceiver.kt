package com.example.wakemeupin

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat
import android.media.RingtoneManager
import android.net.Uri
import android.os.Environment
import java.io.File


class AlarmReceiver : BroadcastReceiver() {
  override fun onReceive(context: Context?, intent: Intent?) {
    val v = longArrayOf(0, 500, 110, 500, 110, 450, 110, 200, 110, 170, 40, 450, 110, 200, 110, 170, 40, 500)

    //TODO (MM) Read in file from user.
    val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
//    val uri = Uri
//          .fromFile(
//            File(
//              Environment.getExternalStorageDirectory().path +
//                      "/yeah/03.Oioi.flac"
//            )
//          )

    // Create notification
    val mBuilder = NotificationCompat.Builder(
      context!!,
      Notifications.getChannelID().toString()
    )
      .setSmallIcon(R.mipmap.ic_launcher)
      .setContentTitle("Alarm Manager")
      .setContentText("WAKE UP MY BITCHES")
      .setPriority(NotificationCompat.PRIORITY_DEFAULT)
      .setVibrate(v)
      .setSound(uri)

    //Get Notification Manager
//    val am = context.getSystemService(Context.NOTIFICATION_SERVICE)
//        as NotificationManager

    // Generate ID for notification
    val id = System.currentTimeMillis() / 1000

    // Show notification
    Notifications.getNotificationManager().notify(id.toInt(), mBuilder.build())
  }
}
