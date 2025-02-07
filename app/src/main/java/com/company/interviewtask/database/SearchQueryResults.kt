package com.company.interviewtask.database

import androidx.room.Embedded
import androidx.room.Relation

data class SearchQueryResults(
    @Embedded val searchQuery: SearchQuery,
    @Relation(
        parentColumn = "searchId",
        entityColumn = "performedSearchId"
    )
    val results: List<SearchResult>
)