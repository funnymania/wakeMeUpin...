package com.example.wakemeupin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.NumberPicker

class AlarmDialog : AppCompatActivity() {

    companion object {
        const val ALARM_HOURS = "alarm_hours"
        const val ALARM_MINS = "alarm_mins"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm_dialog)

        val hourPicker = findViewById<NumberPicker>(R.id.numberPickerHours)
        hourPicker.minValue = 0
        hourPicker.maxValue = 23
        hourPicker.value = 9

        val minPicker = findViewById<NumberPicker>(R.id.numberPickerMinutes)
        minPicker.minValue = 0
        minPicker.maxValue = 59
        minPicker.value = 0
    }

    fun setAlarm(view: View) {
        // grab from both number pickers hours and minutes
        val hourPicker = findViewById<NumberPicker>(R.id.numberPickerHours)
        val minPicker = findViewById<NumberPicker>(R.id.numberPickerMinutes)


    }
}
