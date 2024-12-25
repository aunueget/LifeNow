package com.example.lifenow;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.util.LinkedList;

@Entity(tableName = "devices")
public class DeviceData {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String SSID;
    private String deviceName;
    private String macAddress;
    private DeviceType deviceType;
    private int signalStrength;
    public DeviceData(){
        SSID = null;
        deviceName = null;
        macAddress = null;
        deviceType = DeviceType.UNKNOWN_DEVICE_TYPE;
        signalStrength = 0;
    }

    public String getSSID() {
        return SSID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSSID(String SSID) {
        this.SSID = SSID;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }

    public int getSignalStrength() {
        return signalStrength;
    }

    public void setSignalStrength(int signalStrength) {
        this.signalStrength = signalStrength;
    }
}
