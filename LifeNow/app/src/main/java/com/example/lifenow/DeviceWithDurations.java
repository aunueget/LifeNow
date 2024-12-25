package com.example.lifenow;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class DeviceWithDurations {
    @Embedded
    public DeviceData deviceData;
    @Relation(
            parentColumn = "id",
            entityColumn = "id"
    )
    public List<DeviceDuration> visits;
}
