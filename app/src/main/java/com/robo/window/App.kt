package com.robo.window

import android.app.Application
import com.robo.window.data.dagger.DaggerComponent
import com.robo.window.data.dagger.DaggerDaggerComponent


class App: Application() {
    lateinit var component: DaggerComponent

    override fun onCreate() {
        super.onCreate()
        Singleton.context = applicationContext
        component = DaggerDaggerComponent.create()
        Singleton.daggerComponent = component
    }
}