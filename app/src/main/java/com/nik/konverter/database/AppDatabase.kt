package com.nik.konverter.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.nik.konverter.utilities.DATABASE_NAME
import java.security.AccessControlContext

@Database(entities = [Valuta::class], version = 1)
abstract class AppDatabase: RoomDatabase(){
    abstract fun valutaDao(): ValutaDao
    companion object {
        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .build()
        }
    }


}