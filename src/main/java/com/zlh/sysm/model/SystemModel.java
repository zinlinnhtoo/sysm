package com.zlh.sysm.model;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;

public class SystemModel {
    private final CentralProcessor processor;
    private long[] previousTicks;

    public SystemModel() {
        SystemInfo systemInfo = new SystemInfo();
        this.processor = systemInfo.getHardware().getProcessor();
        this.previousTicks = processor.getSystemCpuLoadTicks();
    }

    public String getCpuName() {
        return processor.getProcessorIdentifier().getName();
    }

    public double getCpuUsage() {
        double usage = processor.getSystemCpuLoadBetweenTicks(previousTicks);
        previousTicks = processor.getSystemCpuLoadTicks();
        return usage;
    }
}