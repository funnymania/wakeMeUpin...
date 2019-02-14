package com.example.wakemeupin

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.TextView
import java.util.*

class SecondActivity : AppCompatActivity() {
  companion object {
    const val TOTAL_COUNT = "total_count"
    const val EXTRA_REPLY = "com.example.android.wordlistsql.REPLY"
    private val LOG_TAG = SecondActivity::class.java.name
  }

  private lateinit var editAlarmView: EditText

  override fun onStart() {
    super.onStart()
    Log.d(SecondActivity.LOG_TAG, "onStart")
  }

  override fun onPause() {
    super.onPause()
    Log.d(SecondActivity.LOG_TAG, "onPause")
  }

  override fun onDestroy() {
    super.onDestroy()
    Log.d(SecondActivity.LOG_TAG, "onDestroy")
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    Log.d(SecondActivity.LOG_TAG, "-------")
    Log.d(SecondActivity.LOG_TAG, "onCreate")
    setContentView(R.layout.activity_alarm_dialog)
    editAlarmView = findViewById(R.id.edit_alarm)

    val hourPicker = findViewById<NumberPicker>(R.id.numberPickerHours)
    hourPicker.minValue = 0
    hourPicker.maxValue = 23
    hourPicker.value = 9

    val minPicker = findViewById<NumberPicker>(R.id.numberPickerMinutes)
    minPicker.minValue = 0
    minPicker.maxValue = 59
    minPicker.value = 0

    val button = findViewById<Button>(R.id.schedule_alarm)
    button.setOnClickListener {
      val replyIntent = Intent()
      if (TextUtils.isEmpty(editAlarmView.text)) {
        setResult(Activity.RESULT_CANCELED, replyIntent)
      } else {
        val alarm = editAlarmView.text.toString()
        replyIntent.putExtra(EXTRA_REPLY, alarm)
        setResult(Activity.RESULT_OK, replyIntent)
      }
      finish()
    }

    showRandomNumber()
  }

    private fun showRandomNumber() {
        // Get count from the intent (secondactivity rendered via intent
        // extras are accessible
        val count = intent.getIntExtra(TOTAL_COUNT, 0)

        val random = Random()
        var randomInt = 0

        if (count > 0) {
            randomInt = random.nextInt(count + 1)
        }

        // Display the random number.
        findViewById<TextView>(R.id.textview_random).text =
                Integer.toString(randomInt)

        // Substitute the max value into the string resource
        // for the heading, update the heading
        findViewById<TextView>(R.id.textview_label).text =
                getString(R.string.random_heading, count)
    }
}
