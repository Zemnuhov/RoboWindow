package com.robo.window.domain.usecase.basefunctions

import com.robo.window.domain.repository.BaseRepository

class OpenWindow(private val repository: BaseRepository) {
    operator fun invoke(){
        repository.openWindow()
    }
}