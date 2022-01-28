package com.robo.window.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.robo.window.App
import com.robo.window.domain.repository.ConnectionRepository
import com.robo.window.domain.usecase.Device
import com.robo.window.domain.usecase.GetDeviceStateUseCase
import com.robo.window.domain.usecase.connection.ConnectionToDeviceUseCase
import com.robo.window.domain.usecase.connection.GetListDeviceUseCase
import com.robo.window.domain.usecase.connection.GetSearchStateUseCase
import com.robo.window.domain.usecase.connection.SearchDeviceUseCase
import javax.inject.Inject

class SearchFragmentViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var repository: ConnectionRepository
    val deviceList:LiveData<List<Device>>
    val deviceState: LiveData<String>
    val searchState: LiveData<Boolean>

    init {
        (application as App).component.inject(this)
        deviceList = GetListDeviceUseCase(repository).getListDevice()
        deviceState = GetDeviceStateUseCase(repository).getDeviceState()
        searchState = GetSearchStateUseCase(repository).getSearchState()
    }

    fun connectionToDevice(MAC: String){
        ConnectionToDeviceUseCase(repository).connectionToDevice(MAC)
    }

    fun searchDevice(){
        SearchDeviceUseCase(repository).searchDevice()
    }
}