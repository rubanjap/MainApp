package com.ruban.mainapp

import com.example.findmyip.data.remote.FindMyIpApi
import com.example.findmyip.data.remote.IPInfo
import com.example.findmyip.domain.repository.FindMyIPRepository
import com.example.findmyip.presentation.FindMyIpViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.Response


@ExperimentalCoroutinesApi
class FindMyIpViewModelTest {

    // private val testDispatcher = StandardTestDispatcher()
    private val testDispatcher = TestCoroutineDispatcher()

    @Mock
    private lateinit var repository: FindMyIPRepository

    @Mock
    private lateinit var api: FindMyIpApi

    private lateinit var viewModel: FindMyIpViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = FindMyIpViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // Reset main dispatcher after the tests
    }

    @Test
    fun `fetchIpInfo success`(): Unit = testDispatcher.runBlockingTest {
        // Arrange
        val response = Response.success(
            IPInfo(
                ip = "49.47.241.206",
                network = "49.47.241.0/24",
                version = "IPv4",
                city = "Coimbatore",
                region = "Tamil Nadu",
                region_code = "TN",
                country = "IN",
                country_name = "India",
                country_code = "IN",
                country_code_iso3 = "IND",
                country_capital = "New Delhi",
                country_tld = ".in",
                continent_code = "AS",
                in_eu = false,
                postal = "641018",
                latitude = 11.0142,
                longitude = 76.9941,
                timezone = "Asia/Kolkata",
                utc_offset = "+0530",
                country_calling_code = "+91",
                currency = "INR",
                currency_name = "Rupee",
                languages = "en-IN,hi,bn,te,mr,ta,ur,gu,kn,ml,or,pa,as,bh,sat,ks,ne,sd,kok,doi,mni,sit,sa,fr,lu,inc",
                country_area = 3287590.0,
                country_population = 1352617328,
                asn = "AS55836",
                org = "Reliance Jio Infocomm Limited"
            )
        )
        `when`(repository.getIpInfo()).thenReturn(response)

        // Act
        viewModel.fetchIpInfo()

        // Assert
        assert(response.body()!=null)
        assertEquals(false, viewModel.loading.value)
        assertEquals(null, viewModel.error.value)
    }

    @Test
    fun `fetchIpInfo failure`() = testDispatcher.runBlockingTest {
        // Arrange

        val expectedMessage = "Error: 404"
        `when`(runBlocking { repository.getIpInfo() }).thenAnswer { throw Exception("404") }

        // Act
        viewModel.fetchIpInfo()

        // Assert
        assertEquals(null, viewModel.ipInfo.value)
        assertEquals(false, viewModel.loading.value)
        assertEquals(expectedMessage, viewModel.error.value)
    }

    @Test
    fun `save Ip Items`() {
        // Arrange
        val ipInfo = IPInfo(
            ip = "49.47.241.206",
            network = "49.47.241.0/24",
            version = "IPv4",
            city = "Coimbatore",
            region = "Tamil Nadu",
            region_code = "TN",
            country = "IN",
            country_name = "India",
            country_code = "IN",
            country_code_iso3 = "IND",
            country_capital = "New Delhi",
            country_tld = ".in",
            continent_code = "AS",
            in_eu = false,
            postal = "641018",
            latitude = 11.0142,
            longitude = 76.9941,
            timezone = "Asia/Kolkata",
            utc_offset = "+0530",
            country_calling_code = "+91",
            currency = "INR",
            currency_name = "Rupee",
            languages = "en-IN,hi,bn,te,mr,ta,ur,gu,kn,ml,or,pa,as,bh,sat,ks,ne,sd,kok,doi,mni,sit,sa,fr,lu,inc",
            country_area = 3287590.0,
            country_population = 1352617328,
            asn = "AS55836",
            org = "Reliance Jio Infocomm Limited"
        )

        // Act
        viewModel.saveIpItems(ipInfo)
        val value = viewModel.ipInfo.value

        assertEquals("No", value?.get(12)?.value)
        assertEquals(24,value?.size)

        // Assert
        // Add assertions based on your logic for saving IP items
    }
}