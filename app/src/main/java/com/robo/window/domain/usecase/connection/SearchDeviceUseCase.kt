package com.robo.window.domain.usecase.connection

import com.robo.window.domain.repository.ConnectionRepository

class SearchDeviceUseCase(private val connectionRepository: ConnectionRepository) {
    fun searchDevice(){
        connectionRepository.searchDevice()
    }
}