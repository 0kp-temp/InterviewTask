package com.company.interviewtask.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface DAO {
    @Transaction
    @Query("SELECT * FROM SearchQuery")
    fun getAllSearches(): Flow<List<SearchQueryResults>>

    @Insert
    suspend fun insert(query: SearchQuery): Long

    @Insert
    suspend fun insert(searchResults: List<SearchResult>)
}