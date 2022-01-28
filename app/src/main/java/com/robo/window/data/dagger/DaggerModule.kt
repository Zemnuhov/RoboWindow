package com.robo.window.data.dagger

import com.robo.window.data.ble.BleConnection
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class DaggerModule{
    @Provides
    @Singleton
    fun provideBleConnection(): BleConnection {
        return BleConnection()
    }

}

