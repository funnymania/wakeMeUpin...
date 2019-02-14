package com.example.wakemeupin

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

@Dao
interface AlarmDAO {
  @Query("SELECT * from alarm_table ORDER BY id ASC")
  fun getAllAlarms(): LiveData<List<Alarm>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insert(alarm: Alarm)

  @Delete
  fun delete(alarm: Alarm)

  @Update
  fun update(alarm: Alarm)

  @Query("DELETE FROM alarm_table")
  fun deleteAll()
}