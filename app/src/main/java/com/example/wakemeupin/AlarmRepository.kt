package com.example.wakemeupin

import android.arch.lifecycle.LiveData
import android.support.annotation.WorkerThread

class AlarmRepository(private val alarmDAO: AlarmDAO) {
  val allAlarms: LiveData<List<Alarm>> = alarmDAO.getAllAlarms()

  @WorkerThread
  suspend fun insert(alarm: Alarm) {
    alarmDAO.insert(alarm)
  }
}