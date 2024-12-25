package com.example.lifenow;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.LinkedList;

public class DeviceListModelFactory implements ViewModelProvider.Factory {

    private LinkedList<DeviceData> deviceList;
    private LinkedList<String> allMacAddress;
    public  DeviceListModelFactory(){
        this.deviceList = new LinkedList<DeviceData>();
        this.allMacAddress = new LinkedList<String >();
        System.out.println(deviceList.toString());
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(DeviceListModel.class)) {
            return (T) new DeviceListModel();
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
