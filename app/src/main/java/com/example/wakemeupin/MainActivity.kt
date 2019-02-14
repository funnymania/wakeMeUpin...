package com.example.wakemeupin

import android.app.*
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.widget.Button
import android.widget.Toast
import android.content.Context
import android.os.Build
import java.util.*


class MainActivity : AppCompatActivity() {
  companion object {
    const val newAlarmActivityRequestCode = 1
    private val LOG_TAG = MainActivity::class.java.name
    private var alarmMgr: AlarmManager? = null
    private lateinit var alarmIntent: PendingIntent
  }

  private lateinit var alarmViewModel: AlarmViewModel

  override fun onStart() {
    super.onStart()
    Log.d(LOG_TAG, "onStart")
  }

  override fun onPause() {
    super.onPause()
    Log.d(LOG_TAG, "onPause")
  }

  override fun onDestroy() {
    super.onDestroy()
    Log.d(LOG_TAG, "onDestroy")
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    Log.d(LOG_TAG, "-------")
    Log.d(LOG_TAG, "onCreate")
    setContentView(R.layout.activity_main)

    alarmMgr = getSystemService(Context.ALARM_SERVICE) as AlarmManager
    alarmIntent = Intent(this, AlarmReceiver::class.java).let { intent ->
      PendingIntent.getBroadcast(this, 0, intent, 0)
    }
    Log.d(LOG_TAG, "alarmIntent")

    val toolbar = findViewById<Toolbar>(R.id.toolbar)
    setSupportActionBar(toolbar)

    val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
    val adapter = AlarmListAdapter(this)

    recyclerView.adapter = adapter
    recyclerView.layoutManager = LinearLayoutManager(this)

    // Read in alarm from memory
    // val = readfromMemoryAlarmList
    // if (!val.isEmpty)
    //  create / show alarm view which is
    // a checkmark, "You are getting %f hours of sleep"
    // checkmark has a click lsitener that disables the background process

    alarmViewModel = ViewModelProviders.of(this).get(AlarmViewModel::class.java)
    alarmViewModel.allAlarms.observe(this, Observer { alarms ->
        // Update the cached copy of the words in the adapter.
        alarms?.let { adapter.setAlarms(it) }
    })
    Log.d(LOG_TAG, "viewmodel")
    val wakeUpInButton = findViewById<Button>(R.id.open_alarm_dialog)
    wakeUpInButton.setOnClickListener {
      val intent = Intent(this@MainActivity, AlarmDialog::class.java)
      startActivityForResult(intent, newAlarmActivityRequestCode)
    }

    Notifications.createChannel(this)

    // If alarm set, cancel it.
    // alarmMgr?.cancel(alarmIntent)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)

    if (requestCode == newAlarmActivityRequestCode && resultCode == Activity.RESULT_OK) {
      data?.let {
        val hoursMins = it.getIntArrayExtra(AlarmDialog.EXTRA_REPLY)

        val rightNow = Calendar.getInstance(TimeZone.getTimeZone("UTC"))

        var hours = rightNow.get(Calendar.HOUR_OF_DAY) + hoursMins[0]
        var minutes = rightNow.get(Calendar.MINUTE) + hoursMins[1]

        if (minutes > 59) {
          minutes -= 60
          hours += 1
        }

        if (hours > 23) {
          hours -= 24
        }

        val calendar = Calendar.getInstance().apply {
          timeInMillis = System.currentTimeMillis()
          timeZone = TimeZone.getTimeZone("UTC")
          set(Calendar.HOUR_OF_DAY, hours)
          set(Calendar.MINUTE, minutes)
          set(Calendar.SECOND, 0)
        }

        val whenToSet = calendar.timeInMillis
//          (hoursMins[0] * 3600000) + (hoursMins[1] * 60000).toLong()

        val alarm = Alarm(0, whenToSet)

        alarmViewModel.insert(alarm)
        Log.d("alarm", "Alarm saved for " + whenToSet.toString() + "ms from now")
        when {
          Build.VERSION.SDK_INT >= 23 ->
            alarmMgr?.setExactAndAllowWhileIdle(
              AlarmManager.RTC_WAKEUP,
              whenToSet,
              alarmIntent
            )
          Build.VERSION.SDK_INT >= 19 ->
            alarmMgr?.setExact(
              AlarmManager.RTC_WAKEUP,
              whenToSet,
              alarmIntent
            )
          else -> alarmMgr?.set(
            AlarmManager.RTC_WAKEUP,
            whenToSet,
            alarmIntent
          )
        }
        Log.d("alarm", "Alarm has been scheduled for " + whenToSet.toString() + "ms from now")
      }
    } else {
      Toast.makeText(
          applicationContext,
          R.string.empty_not_saved,
          Toast.LENGTH_LONG
      ).show()
    }
  }
}
