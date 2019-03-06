package com.example.wakemeupin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class WakeUpActivity : AppCompatActivity() {
  override fun onStart() {
    super.onStart()
    System.out.println("I AM STARTING NOW")
  }

  override fun onDestroy() {
    super.onDestroy()
    System.out.println("fdsfsd")
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val title = "IF U DON'T GET UP THE WORLD GONNA IS ENDING OFF!!"

  }
}