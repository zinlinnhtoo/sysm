package com.zlh.sysm.model;

public abstract class HardwareMetric {
    protected String name;

    public String getName() {
        return name;
    }

    public abstract double getUsage();
}
