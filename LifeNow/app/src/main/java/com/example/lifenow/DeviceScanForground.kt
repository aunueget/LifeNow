package com.example.lifenow

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.bluetooth.BluetoothManager
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.wifi.WifiManager
import android.os.Binder
import android.os.Handler
import android.os.IBinder
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class DeviceScanForground : Service() {
    private var bluetoothLeScanner: BluetoothLeScanner? = null
    private var scanning = false
    private var handler: Handler? = null
    private val scope = CoroutineScope(Dispatchers.IO)

    var deviceList: DeviceList? = null
        private set

    /**
     * Class for clients to access.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with
     * IPC.
     */
    inner class LocalBinder : Binder() {
        val service: DeviceScanForground
            get() = this@DeviceScanForground
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        deviceList = DeviceList()
        scanning = false
        handler = Handler()
        val bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        val bluetoothAdaptor = bluetoothManager.adapter
        bluetoothLeScanner = bluetoothAdaptor.bluetoothLeScanner

        Thread {
            while (true) {
                scanWifiDevices()
                Log.e("Service", "Scanning Wifi...")
                scanBLEDevices()
                Log.e("Service", "Scanning BLE...")
                try {
                    Thread.sleep(60000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }.start()
        val CHANNELID = "Device Scan for LifeNow Service ID"
        val channel = NotificationChannel(
            CHANNELID,
            CHANNELID,
            NotificationManager.IMPORTANCE_LOW
        )

        getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
        val notification = Notification.Builder(this, CHANNELID)
            .setContentText("Service is running")
            .setContentTitle("Service enabled")
            .setSmallIcon(R.drawable.ic_launcher_background)

        startForeground(ID, notification.build())
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    private fun scanBLEDevices() {
        if (!scanning) {
            // Stops scanning after a predefined scan period.
            handler!!.postDelayed(Runnable {
                scanning = false
                if (ActivityCompat.checkSelfPermission(
                        baseContext,
                        Manifest.permission.BLUETOOTH_SCAN
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return@Runnable
                }
                bluetoothLeScanner!!.stopScan(leScanCallback)
            }, SCAN_PERIOD)

            scanning = true
            bluetoothLeScanner!!.startScan(leScanCallback)
        } else {
            scanning = false
            bluetoothLeScanner!!.stopScan(leScanCallback)
        }
    }

    // Device scan callback.
    private val leScanCallback: ScanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            var deviceName: String? = ""
            super.onScanResult(callbackType, result)
            if (ActivityCompat.checkSelfPermission(
                    baseContext,
                    Manifest.permission.BLUETOOTH_CONNECT
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                deviceName = result.device.name
            }
            deviceList!!.addBLEDevice(result, deviceName,scope,baseContext)
            println(result.toString())
        }
    }

    private fun scanWifiDevices() {
        val wifiManager = getSystemService(WIFI_SERVICE) as WifiManager

        val wifiScanReceiver: BroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(c: Context, intent: Intent) {
                val success = intent.getBooleanExtra(
                    WifiManager.EXTRA_RESULTS_UPDATED, false
                )
                if (success) {
                    scanSuccess(wifiManager)
                } else {
                    // scan failure handling
                    scanFailure(wifiManager)
                }
            }
        }

        val intentFilter = IntentFilter()
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)
        this.registerReceiver(wifiScanReceiver, intentFilter)

        val success = wifiManager.startScan()
        if (!success) {
            // scan failure handling
            scanFailure(wifiManager)
        }
    }

    private fun scanSuccess(wifiManager: WifiManager) {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        val results = wifiManager.scanResults
        deviceList!!.addWifiDevices(results,scope,baseContext)
        println((results.toString()))
        //TODO: Store scan results
    }

    private fun scanFailure(wifiManager: WifiManager) {
        // handle failure: new scan did NOT succeed
        // consider using old scan results: these are the OLD results!
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        val results = wifiManager.scanResults
        //TODO: Do something when scan failes
    }

    companion object {
        private val NOTIFICATION: Notification? = null
        private const val ID = 314

        // Stops scanning after 10 seconds.
        private const val SCAN_PERIOD: Long = 2000
    }
}
