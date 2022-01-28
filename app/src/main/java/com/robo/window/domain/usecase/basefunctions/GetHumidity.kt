package com.robo.window.domain.usecase.basefunctions

import androidx.lifecycle.LiveData
import com.robo.window.domain.repository.BaseRepository

class GetHumidity(private val repository: BaseRepository) {
    operator fun invoke(): LiveData<Int>{
        return repository.getHumidity()
    }
}