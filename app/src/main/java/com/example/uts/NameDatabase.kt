package com.example.uts

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Name::class], version = 1) // Mendefinisikan database dengan entity Name
abstract class NameDatabase : RoomDatabase() {
    abstract fun nameDao(): NameDao // Mendeklarasikan DAO

    companion object {
        @Volatile private var INSTANCE: NameDatabase? = null

        // Singleton pattern untuk mendapatkan instance database
        fun getDatabase(context: Context): NameDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NameDatabase::class.java,
                    "name_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}