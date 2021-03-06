package com.robo.window

import android.annotation.SuppressLint
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.robo.window.data.dagger.DaggerComponent


@SuppressLint("StaticFieldLeak")
object Singleton {
    const val DEBUG = false
    lateinit var context: Context
    lateinit var daggerComponent: DaggerComponent
    lateinit var fragmentManager:FragmentManager


    fun showFragment(fragment: Fragment, backStack: String) {
        fragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(backStack)
            .commit()
    }
}