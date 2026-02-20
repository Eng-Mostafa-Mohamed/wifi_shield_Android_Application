package com.example.data.mapper

import com.example.data.modules.DeviceDto
import com.example.domain.dataClasses.DeviceResponseItem

fun DeviceDto.toDeviceEntity(): DeviceResponseItem {
    return DeviceResponseItem(
        hostname = hostname,
        os = os,
        usageMb = usage_mb,
        vendor = vendor,
        ip = ip,
        deviceType = device_type,
        id = id,
        mac = mac,
        status = status,
        username = username
    )
}



fun DeviceResponseItem.toDeviceDto(): DeviceDto{
    return DeviceDto(
        hostname=hostname,
        os=os,
        usage_mb=usageMb,
        vendor=vendor,
        ip=ip,
        device_type=deviceType,
        id=id,
        mac=mac,
        status=status,
        username=username
    )
}