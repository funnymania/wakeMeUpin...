package com.example.wakemeupin

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.Main
import kotlin.coroutines.experimental.CoroutineContext

class AlarmViewModel(application: Application) : AndroidViewModel(application) {
  private var parentJob = Job()
  private val coroutineContext: CoroutineContext
    get() = parentJob + Dispatchers.Main
  private val scope = CoroutineScope(coroutineContext)

  private val repository: AlarmRepository
  val allAlarms: LiveData<List<Alarm>>

  init {
    val alarmDao = AlarmDatabase.getDatabase(application, scope).alarmDao()
    repository = AlarmRepository(alarmDao)
    allAlarms = repository.allAlarms
  }

  fun insert(alarm: Alarm) = scope.launch(Dispatchers.IO) {
    repository.insert(alarm)
  }

  override fun onCleared() {
    super.onCleared()
    parentJob.cancel()
  }
}