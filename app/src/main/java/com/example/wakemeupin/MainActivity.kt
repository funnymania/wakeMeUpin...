package com.example.wakemeupin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Read in alarm from memory
        // val = readfromMemoryAlarmList
        // if (!val.isEmpty)
        //  create / show alarm view which is
        // a checkmark, "You are getting %f hours of sleep"
        // checkmark has a click lsitener that disables the background process
    }


    fun startAlarmDialog(view: View) {
        val startAlarmIntent = Intent(this, AlarmDialog::class.java)

        startActivity(startAlarmIntent)
    }

//    fun toastMe(view: View) {
//        // val myToast = Toast.makeText(this, message, duration)
//        val myToast = Toast.makeText(this, "Hello Toast!", Toast.LENGTH_SHORT)
//        myToast.show()
//    }

    fun countMe(view: View) {
        // get the text view
        val showCountTextView = findViewById<TextView>(R.id.textView)

        // Get the value of the text view.
        val countString = showCountTextView.text.toString()

        // Convert value to a number and increment it
        var count: Int = Integer.parseInt(countString)
        count += 1

        showCountTextView.text = count.toString()
    }

    fun randomMe(view: View) {
        // Create an Instant
        val randomIntent = Intent(this, SecondActivity::class.java)

        // textView imported through avtivity_main.*
        val countStr = textView.text.toString()

        val count = Integer.parseInt(countStr)

        // Add the count to the extras for the Intent.
        randomIntent.putExtra(SecondActivity.TOTAL_COUNT, count)

        // Start the new activity
        startActivity(randomIntent)
    }
}
