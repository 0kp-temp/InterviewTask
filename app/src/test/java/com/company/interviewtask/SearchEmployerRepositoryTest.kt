package com.company.interviewtask

import android.net.http.NetworkException
import com.company.interviewtask.data.SearchEmployerRepository
import com.company.interviewtask.database.DAO
import com.company.interviewtask.network.EmployersService
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

class SearchEmployerRepositoryTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @RelaxedMockK
    lateinit var dao: DAO

    @RelaxedMockK
    lateinit var employersService: EmployersService

    lateinit var searchEmployerRepository: SearchEmployerRepository

    @Before
    fun setUp() {
        searchEmployerRepository = SearchEmployerRepository(dao, employersService)
    }


    @Test(expected = SearchEmployerRepository.SearchAlreadyDoneException::class)
    fun testSearchAlreadyDoneException() = runTest {
        coEvery { dao.isSearchPresent(any()) } answers { true }
        searchEmployerRepository.searchEmployer("query")
    }

    @Test(expected = SearchEmployerRepository.SearchNetworkException::class)
    fun testSearchNetworkException() = runTest {
        coEvery { dao.isSearchPresent(any()) } answers { false }
        coEvery { employersService.searchEmployers(any()) } answers {
            Response.error(
                404,
                ResponseBody.create(null, "")
            )
        }
        searchEmployerRepository.searchEmployer("query")
    }

}