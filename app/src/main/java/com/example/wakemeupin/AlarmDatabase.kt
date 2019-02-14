package com.example.wakemeupin

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.migration.Migration
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.IO
import kotlinx.coroutines.experimental.launch

@Database(entities = [Alarm::class], version = 1)
abstract class AlarmDatabase : RoomDatabase() {
  abstract fun alarmDao() : AlarmDAO

  companion object {
    @Volatile
    private var INSTANCE: AlarmDatabase? = null

    private val MIGRATION_1_2: Migration = object : Migration(1, 2) {
      override fun migrate (database: SupportSQLiteDatabase) {
        // Since we didn't alter the table, there's nothing else to do here.
        // Example code follows.
        /* database.execSQL("ALTER TABLE users "
                + " ADD COLUMN last_update INTEGER");
        * */
      }
    }

    fun getDatabase (context: Context, scope: CoroutineScope): AlarmDatabase {
      return INSTANCE ?: synchronized(this) {
        // Create database here
        val instance = Room.databaseBuilder(
            context.applicationContext,
            AlarmDatabase::class.java,
            "alarm_database")
          .addMigrations(MIGRATION_1_2)
          .addCallback(AlarmDatabaseCallback(scope))
          .build()
        INSTANCE = instance
        return instance
      }
    }

    private class AlarmDatabaseCallback(
      private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
      override fun onOpen (db: SupportSQLiteDatabase) {
        super.onOpen(db)
        INSTANCE?.let { database ->
          scope.launch(Dispatchers.IO) {
            helloDatabase(database.alarmDao())
          }
        }
      }
    }

    fun helloDatabase (alarmDao: AlarmDAO) {
      alarmDao.deleteAll()
//      val alarm = Alarm(0)
//      alarmDao.insert(alarm)
    }
  }
}