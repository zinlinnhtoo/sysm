package com.zlh.sysm.model;

import oshi.hardware.PowerSource;
import java.util.List;

public class BatteryMetric extends HardwareMetric {
    private PowerSource battery;

    public BatteryMetric(List<PowerSource> powerSources) {
        this.name = "System Battery";
        if (!powerSources.isEmpty()) {
            this.battery = powerSources.getFirst();
        }
    }

    @Override
    public double getUsage() {
        if (battery == null) return 0.0;
        battery.updateAttributes();
        return battery.getRemainingCapacityPercent() * 100;
    }
}
