package com.example.weatherapp

import com.example.weatherapp.dashboard.views.DashBoardActivity
import com.example.weatherapp.utils.AppInitializer
import org.junit.Assert
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times

class DashboardTest {
    //make DashboardActivity open before execution, else mock
    @Test
    fun test() {
        Mockito.mock(DashBoardActivity::class.java)
        DashBoardActivity().setDefaultLocation()
        val expected = "London"
        Assert.assertEquals(expected,AppInitializer.location)
    }


}