package com.example.wakemeupin

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

@Dao
interface AlarmDAO {
  @Query("SELECT * from Alarm ORDER BY id ASC")
  fun getAllAlarms(): LiveData<List<Alarm>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insert(Alarm: alarm)

  @Delete
  fun delete(Alarm: alarm)

  @Update
  fun update(Alarm: alarm)
}