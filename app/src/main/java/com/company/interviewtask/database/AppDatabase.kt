package com.company.interviewtask.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [SearchResult::class, SearchQuery::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun DAO(): DAO
}