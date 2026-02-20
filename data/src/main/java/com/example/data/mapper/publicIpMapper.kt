package com.example.data.mapper

import com.example.data.modules.IpResponseDto
import com.example.domain.dataClasses.IpResponse

fun IpResponseDto.toIpResponse(): IpResponse{
    return IpResponse(ip = ip)
}

fun IpResponse.toIpResponseDto(): IpResponseDto{
    return IpResponseDto(ip = ip)
}

