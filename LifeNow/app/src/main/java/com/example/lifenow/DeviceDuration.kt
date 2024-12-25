package com.example.lifenow

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "visits",
    foreignKeys = [
        ForeignKey(
            entity = DeviceData::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("id")
        )
    ]
)
data class DeviceDuration(val deviceId: Long, val deviceSignalStrength: Int, val deviceStartTime: Date){

    @PrimaryKey(autoGenerate = true)
    var pkId: Int? = null

    @ColumnInfo(name = "id")
    var id: Int? = null

    @ColumnInfo(name = "signal_strenth")
    var signalStrength: Int = 0

    @ColumnInfo(name = "start_time")
    var startTime: Date? = null

    @ColumnInfo(name = "end_time")
    var endTime: Date? = null
    init {
        id = deviceId
        signalStrength = deviceSignalStrength
        startTime = deviceStartTime
    }
}
