package com.company.interviewtask.data

import com.company.interviewtask.database.DAO
import com.company.interviewtask.database.SearchQuery
import com.company.interviewtask.database.SearchResult
import com.company.interviewtask.network.EmployersService
import javax.inject.Inject

class SearchEmployerRepository @Inject constructor(
    private val dao: DAO,
    private val employersService: EmployersService
) {

    class SearchNetworkException : Exception()
    class SearchAlreadyDoneException : Exception()

    suspend fun searchEmployer(query: String) {
        if (dao.isSearchPresent(query)) {
            throw SearchAlreadyDoneException()
        }
        val result = employersService.searchEmployers(query)
        if (result.isSuccessful.not()) {
            throw SearchNetworkException()
        }
        val searchId = dao.insert(SearchQuery(0, query))
        val searchResults = result.body() ?: emptyList()
        val resultsToSave = searchResults.map {
            SearchResult(
                0,
                searchId,
                it.EmployerID,
                it.DiscountPercentage,
                it.Name,
                it.Place
            )
        }
        dao.insert(resultsToSave)
    }

}