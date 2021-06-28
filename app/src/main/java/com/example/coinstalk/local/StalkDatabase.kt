package com.example.coinstalk.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [StalkCache::class], version = 1, exportSchema = false)
abstract class StalkDatabase : RoomDatabase() {
    abstract fun stalkDao() : StalkDao

}