package com.zlh.sysm.model;

import oshi.hardware.CentralProcessor;

public class CpuMetric extends HardwareMetric {
    private final CentralProcessor processor;
    private long[] previousTicks;

    public CpuMetric(CentralProcessor processor) {
        this.name = processor.getProcessorIdentifier().getName();
        this.processor = processor;
        this.previousTicks = processor.getSystemCpuLoadTicks();
    }

    @Override
    public double getUsage() {
        double usage = processor.getSystemCpuLoadBetweenTicks(previousTicks);
        previousTicks = processor.getSystemCpuLoadTicks();
        return usage * 100;
    }
}
