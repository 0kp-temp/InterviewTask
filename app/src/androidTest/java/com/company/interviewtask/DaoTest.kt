package com.company.interviewtask

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.company.interviewtask.database.AppDatabase
import com.company.interviewtask.database.DAO
import com.company.interviewtask.database.SearchQuery
import com.company.interviewtask.database.SearchResult
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import kotlinx.coroutines.test.runTest

@RunWith(AndroidJUnit4::class)
class DaoTest {

    private lateinit var dao: DAO
    private lateinit var appDatabase: AppDatabase

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = appDatabase.DAO()
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        appDatabase.close()
    }

    @Test
    fun testGetAllSearches() = runTest {
        val search1Id = dao.insert(SearchQuery(1, "query1"))
        val result1 = dao.getAllSearches().first()
        assertEquals(1, result1.size)
        assertEquals(search1Id, result1.first().searchQuery.searchId)
        assertEquals("query1", result1.first().searchQuery.searchQuery)
        assertEquals(0, result1.first().results.size)

        val search1Results = mutableListOf<SearchResult>()
        repeat(3) {
            search1Results += getMockedSearchResult(search1Id)
        }
        dao.insert(search1Results)
        val result2 = dao.getAllSearches().first()
        assertEquals(1, result2.size)
        assertEquals(search1Id, result2.first().searchQuery.searchId)
        assertEquals("query1", result2.first().searchQuery.searchQuery)
        assertEquals(3, result2.first().results.size)
        search1Results.sortedBy { it.name }.let { mockedResults ->
            val actualResults = result2.first().results.sortedBy { it.name }
            actualResults.forEachIndexed { index, actualResult ->
                assertEquals(search1Id, actualResult.performedSearchId)
                assertEquals(mockedResults[index].name, actualResult.name)
                assertEquals(mockedResults[index].place, actualResult.place)
                assertEquals(mockedResults[index].discountPercentage, actualResult.discountPercentage)
            }
        }

        val search2Id = dao.insert(SearchQuery(2, "query2"))
        val search2Results = mutableListOf<SearchResult>()
        repeat(2) {
            search2Results += getMockedSearchResult(search2Id)
        }
        dao.insert(search2Results)
        val result3 = dao.getAllSearches().first()
        assertEquals(2, result3.size)
        assertEquals(search1Id, result2.first().searchQuery.searchId)
        assertEquals("query1", result2.first().searchQuery.searchQuery)
        assertEquals(3, result2.first().results.size)
        search1Results.sortedBy { it.name }.let { mockedResults ->
            val actualResults = result2.first().results.sortedBy { it.name }
            actualResults.forEachIndexed { index, actualResult ->
                assertEquals(search1Id, actualResult.performedSearchId)
                assertEquals(mockedResults[index].name, actualResult.name)
                assertEquals(mockedResults[index].place, actualResult.place)
                assertEquals(mockedResults[index].discountPercentage, actualResult.discountPercentage)
            }
        }
        assertEquals(search2Id, result3.last().searchQuery.searchId)
        assertEquals("query2", result3.last().searchQuery.searchQuery)
        assertEquals(2, result3.last().results.size)
        search2Results.sortedBy { it.name }.let { mockedResults ->
            val actualResults = result3.last().results.sortedBy { it.name }
            actualResults.forEachIndexed { index, actualResult ->
                assertEquals(search2Id, actualResult.performedSearchId)
                assertEquals(mockedResults[index].name, actualResult.name)
                assertEquals(mockedResults[index].place, actualResult.place)
                assertEquals(mockedResults[index].discountPercentage, actualResult.discountPercentage)
            }
        }

    }


}