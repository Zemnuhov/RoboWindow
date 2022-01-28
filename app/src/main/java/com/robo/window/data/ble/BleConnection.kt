package com.robo.window.data.ble

import android.util.Log
import com.jakewharton.rx.ReplayingShare
import com.polidea.rxandroidble2.RxBleClient
import com.polidea.rxandroidble2.RxBleConnection
import com.polidea.rxandroidble2.RxBleDevice
import com.robo.window.Singleton
import com.robo.window.data.spsettings.SettingsApi
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.nio.ByteBuffer
import java.util.*
import kotlin.collections.HashMap


class BleConnection {
    var client: RxBleClient = RxBleClient.create(Singleton.context)
    private var device: RxBleDevice? = null
    private var connection: Observable<RxBleConnection>? = null
    var connectionState = DISCONNECTED
    val connectionStateObservable: PublishSubject<String> = PublishSubject.create()
    val tonicValueObservable: PublishSubject<HashMap<String, Any>> = PublishSubject.create()
    val phaseValueObservable: PublishSubject<HashMap<String, Any>> = PublishSubject.create()
    private val disconnectObservable: PublishSubject<Boolean> = PublishSubject.create()
    private var compositeDisposable = CompositeDisposable()


    private val notificationDataUUID: UUID = UUID.fromString("0000ffe1-0000-1000-8000-00805f9b34fb")
    private val writePeaksUUID: UUID = UUID.fromString("0000ffe2-0000-1000-8000-00805f9b34fb")
    private val writeTonicUUID: UUID = UUID.fromString("0000ffe3-0000-1000-8000-00805f9b34fb")
    private val writeTimeUUID: UUID = UUID.fromString("0000ffe4-0000-1000-8000-00805f9b34fb")


    companion object {
        const val DISCONNECTED = "DISCONNECTED"
        const val DISCONNECTING = "DISCONNECTING"
        const val CONNECTED = "CONNECTED"
        const val CONNECTING = "CONNECTING"
    }

    init {
        updateBleDevice(SettingsApi().getDevice()!!)
        connectionStateObserver()
    }

    private fun connectionStateObserver() {
        compositeDisposable.add(device!!
            .observeConnectionStateChanges()
            .subscribe(
                {
                    Log.i("DeviceState", "State:${it.name} Name:${device?.name}")
                    when (it.name) {
                        "DISCONNECTED" -> {
                            connectionStateObservable.onNext(DISCONNECTED)
                            connectionState = DISCONNECTED
                            val settingsApi = SettingsApi()
                            if (settingsApi.getDevice() != settingsApi.defaultMAC) {
                                updateBleConnection()
                            }
                        }
                        "CONNECTED" -> {
                            connectionStateObservable.onNext(CONNECTED)
                            connectionState = CONNECTED
                        }

                        "CONNECTING" -> {
                            connectionStateObservable.onNext(CONNECTING)
                        }
                        "DISCONNECTING" -> {
                            connectionStateObservable.onNext(DISCONNECTING)
                        }
                        else -> Log.e("DeviceState", "Not enum State")
                    }
                },
                {
                    Log.e("BleConnectionObserver", it.message.toString())
                }
            )
        )

    }

    fun updateBleDevice(MAC: String) {
        disconnectDevice()
        device = client.getBleDevice(MAC)
        connectionStateObserver()
        updateBleConnection()
    }

    private fun updateBleConnection() {
        connection = device!!
            .establishConnection(true)
            .takeUntil(disconnectObservable)
            .compose(ReplayingShare.instance())

        tonicValue()
        phaseValue()

    }

    fun disconnectDevice() {
        disconnectObservable.onNext(true)
        connectionStateObservable.onNext(DISCONNECTED)
        compositeDisposable.clear()
        connection = null
        device = null
    }

    private fun tonicValue() {
        compositeDisposable.add(connection!!
            .subscribeOn(Schedulers.computation())
            .flatMap { rxBleConnection -> rxBleConnection.setupNotification(notificationDataUUID) }
            .flatMap { it }
            .map { ByteBuffer.wrap(it).int }
            .subscribe(
                { tonicValueObservable.onNext(hashMapOf("value" to it, "time" to Date())) },
                { Log.e("BleConTonic", it.toString()) }
            )
        )

    }

    private fun phaseValue() {
        compositeDisposable.add(connection!!
            .flatMap { rxBleConnection -> rxBleConnection.setupNotification(notificationDataUUID) }
            .flatMap { it }
            .map { ByteBuffer.wrap(it).int }

            .subscribe(
                { phaseValueObservable.onNext(hashMapOf("value" to it, "time" to Date())) },
                { Log.e("BleConPhase", it.toString()) }
            )
        )
    }
}