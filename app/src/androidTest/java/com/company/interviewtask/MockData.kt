package com.company.interviewtask

import com.company.interviewtask.database.SearchResult
import kotlin.random.Random

fun getMockedSearchResult(forSearchId: Long) = SearchResult(
    performedSearchId = forSearchId,
    employerId = Random.nextLong(),
    discountPercentage = Random.nextLong(),
    name = "Company " + Random.nextLong(),
    place = "City " + Random.nextLong()
)