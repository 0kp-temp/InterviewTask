package com.company.interviewtask

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.company.interviewtask.network.EndpointServiceCreator
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EndpointServiceTest {

    @Test
    fun testSearchEmployers() = runTest {
        val service = EndpointServiceCreator.createEmployersService()
        val result = service.searchEmployers("Achmea")
        assertTrue(result.isSuccessful)
    }

}