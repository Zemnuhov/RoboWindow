package com.robo.window.domain.usecase.connection

import androidx.lifecycle.LiveData
import com.robo.window.domain.repository.ConnectionRepository
import com.robo.window.domain.usecase.Device

class GetListDeviceUseCase(private val connectionRepository: ConnectionRepository) {
    fun getListDevice(): LiveData<List<Device>>{
        return connectionRepository.getListDevice()
    }
}