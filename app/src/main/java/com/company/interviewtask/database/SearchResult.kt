package com.company.interviewtask.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SearchResult(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val performedSearchId: Long,
    val employerId: Long,
    val discountPercentage: Long,
    val name: String,
    val place: String
)
