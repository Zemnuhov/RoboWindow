package com.robo.window.data

import androidx.lifecycle.LiveData
import com.robo.window.domain.repository.BaseRepository

class BaseRepositoryImpl: BaseRepository {
    override fun getTemperature(): LiveData<Int> {
        TODO("Not yet implemented")
    }

    override fun getHumidity(): LiveData<Int> {
        TODO("Not yet implemented")
    }

    override fun getGassy(): LiveData<Int> {
        TODO("Not yet implemented")
    }

    override fun openWindow() {
        TODO("Not yet implemented")
    }

    override fun closeWindow() {
        TODO("Not yet implemented")
    }
}