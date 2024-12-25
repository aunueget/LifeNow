package com.example.lifenow

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [DeviceData::class,DeviceDuration::class],
    version = 1
)
@TypeConverters(MyConverters::class)
abstract class DeviceDB : RoomDatabase(){
    abstract val dao: DeviceDurationDao
    companion object{
        private var INSTANCE: DeviceDB? =null
        fun getInstance(context: Context): DeviceDB {
            if(INSTANCE == null){
                INSTANCE = Room.databaseBuilder(context,DeviceDB::class.java, "devices.db").build()
            }
            return INSTANCE!!
        }
        fun destroyInstance(){
            INSTANCE = null
        }
    }
}