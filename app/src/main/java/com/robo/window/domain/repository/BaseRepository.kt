package com.robo.window.domain.repository

import androidx.lifecycle.LiveData

interface BaseRepository {
    fun getTemperature(): LiveData<Int>
    fun getHumidity(): LiveData<Int>
    fun getGassy(): LiveData<Int>
    fun openWindow()
    fun closeWindow()
}