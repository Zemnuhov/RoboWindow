package com.robo.window.data.dagger


import com.robo.window.data.ble.BleService
import com.robo.window.data.implementations.ConnectionRepositoryImpl

import com.robo.window.ui.viewmodel.SearchFragmentViewModel
import com.robo.window.data.dagger.DaggerModule
import com.robo.window.data.dagger.DaggerRepositoryModule
import com.robo.window.ui.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DaggerModule::class, DaggerRepositoryModule::class])
interface DaggerComponent {
    fun inject(bleService: BleService)
    fun inject(viewModel: SearchFragmentViewModel)
    fun inject(connectionRepositoryImpl: ConnectionRepositoryImpl)
    fun inject(mainActivity: MainActivity)
}
