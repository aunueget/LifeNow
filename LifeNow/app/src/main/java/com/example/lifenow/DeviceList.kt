package com.example.lifenow

import android.content.Context
import android.net.wifi.ScanResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.LinkedList


data class DeviceList(
    var deviceList: LinkedList<DeviceData> = LinkedList<DeviceData>(),
    var allMacAddress: LinkedList<String> = LinkedList<String>()
) {

    fun addWifiDevices(results: List<ScanResult>, scope: CoroutineScope?, appContext: Context) {
        var device: DeviceData
        for (result in results) {
            device = DeviceData()
            device.ssid = result.SSID
            device.macAddress = result.BSSID
            device.signalStrength = result.level
            device.deviceType = DeviceType.WIFI_DEVICE
            if (!(allMacAddress.contains(device.macAddress))) {
                allMacAddress.add(device.macAddress)
                scope?.launch {
                    val db = DeviceDB.getInstance(appContext)
                    val id = db.dao.upsertDeviceData(device)
                    val duration =
                        DeviceDuration(id, device.signalStrength, Calendar.getInstance().time)
                    db.dao.upsertDuration(duration)
                }
                deviceList.add(device)
            }
        }
    }

    fun addBLEDevice(
        result: android.bluetooth.le.ScanResult,
        name: String?,
        scope: CoroutineScope?, appContext: Context
    ) {
        val device = DeviceData()
        device.deviceName = name
        device.macAddress = result.device.address
        device.signalStrength = result.rssi
        device.deviceType = DeviceType.BLE_DEVICE
        if (!(allMacAddress.contains(device.macAddress))) {
            allMacAddress.add(device.macAddress)
            scope?.launch {
                val db = DeviceDB.getInstance(appContext)
                val id = db.dao.upsertDeviceData(device)
                val duration = DeviceDuration(id,device.signalStrength, Calendar.getInstance().time)
                db.dao.upsertDuration(duration)
            }
            deviceList.add(device)
        }
    }
}
