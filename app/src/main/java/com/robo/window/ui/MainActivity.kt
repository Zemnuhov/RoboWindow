package com.robo.window.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import com.robo.window.data.ble.BleConnection
import com.robo.window.App
import com.robo.window.R
import com.robo.window.Singleton
import com.robo.window.ui.fragment.BaseFragment
import com.robo.window.ui.fragment.SearchFragment
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    lateinit var appMenu: Menu

    @Inject
    lateinit var bleConnection: BleConnection


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (applicationContext as App).component.inject(this)
        Singleton.fragmentManager = supportFragmentManager
        startApplication()

    }

    private fun startApplication() {
        if (bleConnection.connectionState == BleConnection.CONNECTED) {
            Singleton.showFragment(BaseFragment(), "base")
        } else {
            Singleton.showFragment(BaseFragment(), "base")
            //Singleton.showFragment(SearchFragment(), "connection")
        }
    }
}
