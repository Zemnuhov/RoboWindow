package com.robo.window.domain.usecase

import androidx.lifecycle.LiveData
import com.robo.window.domain.repository.ConnectionRepository

class GetDeviceStateUseCase(private val connectionRepository: ConnectionRepository) {
    fun getDeviceState(): LiveData<String> {
        return connectionRepository.getDeviceState()
    }
}