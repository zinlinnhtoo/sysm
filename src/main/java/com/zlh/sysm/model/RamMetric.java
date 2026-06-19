package com.zlh.sysm.model;

import oshi.hardware.GlobalMemory;

public class RamMetric extends HardwareMetric {
    private final GlobalMemory memory;

    public RamMetric(GlobalMemory memory) {
        this.name = "System RAM";
        this.memory = memory;
    }

    @Override
    public double getUsage() {
        long total = memory.getTotal();
        long available = memory.getAvailable();
        return ((double) (total - available) / total) * 100;
    }
}