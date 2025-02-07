package com.company.interviewtask.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SearchQuery(@PrimaryKey(autoGenerate = true) val searchId: Long, val searchQuery: String)
