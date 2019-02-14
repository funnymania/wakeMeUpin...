package com.example.wakemeupin

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.NumberPicker

class AlarmDialog : AppCompatActivity() {

    companion object {
      const val EXTRA_REPLY = "com.example.android.wordlistsql.REPLY"
      const val ALARM_HOURS = "alarm_hours"
      const val ALARM_MINS = "alarm_mins"
      private val LOG_TAG = AlarmDialog::class.java.name
    }

    override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      Log.d(LOG_TAG, "--------")
      Log.d(LOG_TAG, "onCreate")
      setContentView(R.layout.activity_alarm_dialog)

      val hourPicker = findViewById<NumberPicker>(R.id.numberPickerHours)
      hourPicker.minValue = 0
      hourPicker.maxValue = 23
      hourPicker.value = 9

      val minPicker = findViewById<NumberPicker>(R.id.numberPickerMinutes)
      minPicker.minValue = 0
      minPicker.maxValue = 59
      minPicker.value = 0

      val scheduleAlarm = findViewById<Button>(R.id.schedule_alarm)
      scheduleAlarm.setOnClickListener {
        val replyIntent = intent
        val alarmPackage = intArrayOf(hourPicker.value, minPicker.value)
        replyIntent.putExtra(EXTRA_REPLY, alarmPackage)

        setResult(Activity.RESULT_OK, replyIntent)
        finish()
      }
    }
}
