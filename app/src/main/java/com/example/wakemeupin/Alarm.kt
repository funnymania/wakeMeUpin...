package com.example.wakemeupin

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "alarm_table")
data class Alarm (
  @PrimaryKey @ColumnInfo(name = "id") val id: Int,
  @ColumnInfo(name = "alarm_length") val alarmLength: Long
)