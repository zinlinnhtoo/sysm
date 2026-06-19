package com.zlh.sysm.dto;

public record SystemSnapshot(
        String cpuName,
        double cpuUsage,
        double ramUsage,
        double batteryUsage,
        String diskName,
        double diskUsage
) {}