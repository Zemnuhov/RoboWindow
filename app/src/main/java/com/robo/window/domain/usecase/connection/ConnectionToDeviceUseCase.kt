package com.robo.window.domain.usecase.connection

import com.robo.window.domain.repository.ConnectionRepository

class ConnectionToDeviceUseCase(private val connectionRepository: ConnectionRepository) {
    fun connectionToDevice(MAC: String){
        connectionRepository.connectionToDevice(MAC)
    }
}