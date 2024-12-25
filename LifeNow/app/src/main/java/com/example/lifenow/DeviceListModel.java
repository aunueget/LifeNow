package com.example.lifenow;



import android.content.Context;

import androidx.annotation.RestrictTo;
import androidx.lifecycle.ViewModel;

import java.util.LinkedList;
import java.util.List;

import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.internal.ContextScope;

public class DeviceListModel extends ViewModel {
    private DeviceList deviceList;
    public DeviceListModel(){
        deviceList= new DeviceList();
        System.out.println(deviceList.toString());
    }
    public DeviceListModel(DeviceList devices){
        deviceList= devices;
        System.out.println(deviceList.toString());
    }

    public DeviceList getDeviceList() {
        return deviceList;
    }

    public void setDeviceList(DeviceList deviceList) {
        this.deviceList = deviceList;
    }
}
