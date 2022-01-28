package com.robo.window.domain.usecase.connection

import androidx.lifecycle.LiveData
import com.robo.window.domain.repository.ConnectionRepository

class GetSearchStateUseCase(private val connectionRepository: ConnectionRepository) {
    fun getSearchState(): LiveData<Boolean>{
        return connectionRepository.searchState()
    }
}