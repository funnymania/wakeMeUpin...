package com.example.wakemeupin

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.migration.Migration

@Database(entities = [Alarm::class], version = 1)
abstract class AlarmDatabase : RoomDatabase() {
  abstract fun alarmDao() : AlarmDAO

  companion object {
    @Volatile
    private var INSTANCE: AlarmDatabase? = null

    private val MIGRATION_1_2: Migration = object : Migration(1, 2) {
      override fun migrate(database: SupportSQLiteDatabase) {
        // Since we didn't alter the table, there's nothing else to do here.
        // Example code follows.
        /* database.execSQL("ALTER TABLE users "
                + " ADD COLUMN last_update INTEGER");
        * */
      }
    }

    fun getDatabase(context: Context): AlarmDatabase {
      return INSTANCE ?: synchronized(this) {
        // Create database here
        val instance = Room.databaseBuilder(
            context.applicationContext,
            AlarmDatabase::class.java,
            "alarm_database")
          .addMigrations(MIGRATION_1_2)
          .build()
        INSTANCE = instance
        return instance
      }
    }
  }
}