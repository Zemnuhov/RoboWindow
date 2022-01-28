package com.robo.window.domain.usecase

import androidx.lifecycle.LiveData
import com.robo.window.domain.repository.BaseRepository

class GetGassy(private val repository: BaseRepository) {
    fun invoke(): LiveData<Int>{
        return repository.getGassy()
    }
}