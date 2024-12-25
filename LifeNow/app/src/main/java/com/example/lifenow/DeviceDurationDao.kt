package com.example.lifenow

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import java.util.Date

@Dao
interface DeviceDurationDao {
    @Upsert
    suspend fun upsertDeviceData(deviceData: DeviceData) :Long
    @Delete
    suspend fun deleteDeviceData(deviceData: DeviceData)
    @Upsert
    suspend fun upsertDuration(deviceDuration: DeviceDuration):Long
    @Delete
    suspend fun deleteDuration(deviceDuration: DeviceDuration)
    @Transaction
    @Query("SELECT * FROM devices WHERE macAddress = :macAddress")
    suspend fun getDeviceDataByDevice(macAddress: String?): DeviceList?
    @Transaction
    @Query("SELECT * FROM devices,visits WHERE macAddress = :macAddress  AND startTime BETWEEN :start AND :end")
    suspend fun getDurationsByMacAddress(
        macAddress: Int,
        start: Date?,
        end: Date?
    ): List<DeviceWithDurations?>?
    @Transaction
    @Query("SELECT * FROM visits WHERE id = :id AND startTime BETWEEN :start AND :end")
    suspend fun getDurationsById(id: Int, start: Date?, end: Date?): List<DeviceWithDurations?>?
    @Transaction
    @Query("SELECT * FROM visits WHERE startTime BETWEEN :start AND :end")
    suspend fun getDevicesFromDates(start: Date?, end: Date?): List<DeviceWithDurations?>?
}