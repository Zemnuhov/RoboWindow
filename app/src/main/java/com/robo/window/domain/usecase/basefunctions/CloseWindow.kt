package com.robo.window.domain.usecase.basefunctions

import com.robo.window.domain.repository.BaseRepository

class CloseWindow(private val repository: BaseRepository) {
    operator fun invoke(){
        repository.closeWindow()
    }
}