package com.robo.window.domain.usecase

import com.robo.window.domain.repository.BaseRepository

class OpenWindow(private val repository: BaseRepository) {
    fun invoke(){
        repository.openWindow()
    }
}