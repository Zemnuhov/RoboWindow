package com.robo.window.data.ble

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.robo.window.App
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class BleService : Service() {

    @Inject
    lateinit var bleConnection: BleConnection


    private val compositeDisposable = CompositeDisposable()
    private val binder = LocalBinder()


    override fun onCreate() {
        (applicationContext as App).component.inject(this)
        super.onCreate()
        setBackgroundListeners()
    }

    private fun setBackgroundListeners() {
        setPhaseListeners()
        setTimeListener()
    }

    private fun setPhaseListeners() {
        compositeDisposable.add(bleConnection.phaseValueObservable
            .subscribeOn(Schedulers.computation())
            .subscribe {

            }
        )
    }


    private fun setTimeListener() {
        compositeDisposable.add(Observable.interval(30, TimeUnit.SECONDS)
            .subscribe {
                if(Calendar.getInstance()[Calendar.MINUTE] % 10 == 0){
                    TODO("Запись результата")
                }
            }
        )
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    inner class LocalBinder : Binder() {
        fun getService(): BleService = this@BleService
    }

}