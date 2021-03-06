package com.robo.window.data.implementations

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.robo.window.data.ble.BleConnection
import com.robo.window.data.ble.BleService
import com.robo.window.data.spsettings.SettingsApi
import com.polidea.rxandroidble2.scan.ScanSettings
import com.robo.window.Singleton
import com.robo.window.Singleton.context
import com.robo.window.domain.repository.ConnectionRepository
import com.robo.window.domain.usecase.Device
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import kotlin.concurrent.thread

class ConnectionRepositoryImpl : ConnectionRepository {

    @Inject
    lateinit var bleConnection: BleConnection
    private var deviceSet = hashSetOf<Device>()
    private var deviceSetLiveData = MutableLiveData<List<Device>>()
    lateinit var bleService: BleService
    private lateinit var searchDisposable: Disposable
    private var searchState = MutableLiveData<Boolean>()
    private var connectionState = MutableLiveData<String>()
    private var isConnectedService = false
    private val compositeDisposable = CompositeDisposable()
    private var previousDevice = ""
    private val serviceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as BleService.LocalBinder
            bleService = binder.getService()
            isConnectedService = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isConnectedService = false
        }
    }


    init {
        previousDevice = SettingsApi().getDevice()!!
        SettingsApi().setDefaultMAC()
        Singleton.daggerComponent.inject(this)
        searchState.value = false
        bleConnection.updateBleDevice(SettingsApi().getDevice()!!)

    }

    private fun updateStateObserver() {
        val disposable = bleConnection.connectionStateObservable
            .subscribeOn(Schedulers.computation())
            .subscribe {
                if (SettingsApi().getDevice() != SettingsApi().defaultMAC) {
                    connectionState.postValue(it)
                }
            }
        compositeDisposable.add(disposable)
    }


    override fun getDeviceState(): LiveData<String> {
        updateStateObserver()
        return connectionState
    }

    override fun getListDevice(): LiveData<List<Device>> {
        deviceSetLiveData.value = listOf()
        return deviceSetLiveData
    }

    override fun connectionToDevice(MAC: String) {
        searchDisposable.dispose()
        searchState.postValue(false)
        SettingsApi().saveDevice(MAC)
        bleConnection.updateBleDevice(SettingsApi().getDevice()!!)
        val intent = Intent(context, BleService::class.java)
        context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)

    }

    override fun searchState(): LiveData<Boolean> {
        return searchState
    }

    override fun searchDevice() {
        searchState.postValue(true)
        deviceSetLiveData.postValue(listOf())
        searchDisposable = bleConnection.client
            .scanBleDevices(ScanSettings.Builder().build())
            .subscribeOn(Schedulers.computation())
            .subscribe(
                {
                    if(it.bleDevice.name!=null) {
                        deviceSet.add(
                            Device(
                                it.bleDevice.name!!,
                                it.bleDevice.macAddress
                            )
                        )
                    }
                    if (deviceSet.size > deviceSetLiveData.value!!.size) {
                        deviceSetLiveData.postValue(deviceSet.toList())
                    }
                },
                {
                    Log.e("ScanError",it.toString())
                }
            )

        thread {
            Thread.sleep(10000)
            searchDisposable.dispose()
            searchState.postValue(false)
        }
    }

}