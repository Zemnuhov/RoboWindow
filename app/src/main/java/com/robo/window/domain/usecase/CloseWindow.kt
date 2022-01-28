package com.robo.window.domain.usecase

import com.robo.window.domain.repository.BaseRepository

class CloseWindow(private val repository: BaseRepository) {
    fun invoke(){
        repository.closeWindow()
    }
}