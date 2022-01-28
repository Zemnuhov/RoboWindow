package com.robo.window.domain.repository

import androidx.lifecycle.LiveData
import com.robo.window.domain.usecase.Device

interface ConnectionRepository {
    fun getDeviceState(): LiveData<String>
    fun getListDevice(): LiveData<List<Device>>
    fun connectionToDevice(MAC: String)
    fun searchState(): LiveData<Boolean>
    fun searchDevice()
}