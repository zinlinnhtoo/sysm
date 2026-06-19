package com.zlh.sysm.service;

import com.zlh.sysm.dto.SystemSnapshot;
import com.zlh.sysm.model.BatteryMetric;
import com.zlh.sysm.model.CpuMetric;
import com.zlh.sysm.model.DiskMetric;
import com.zlh.sysm.model.RamMetric;
import oshi.SystemInfo;

public class HardwareService {
    private final CpuMetric cpu;
    private final RamMetric ram;
    private final BatteryMetric battery;
    private final DiskMetric disk;

    public HardwareService() {
        SystemInfo si = new SystemInfo();
        this.cpu = new CpuMetric(si.getHardware().getProcessor());
        this.ram = new RamMetric(si.getHardware().getMemory());
        this.battery = new BatteryMetric(si.getHardware().getPowerSources());
        this.disk = new DiskMetric(si.getOperatingSystem().getFileSystem().getFileStores());
    }
    public SystemSnapshot fetchCurrentStats() {
        return new SystemSnapshot(
                cpu.getName(),
                cpu.getUsage(),
                ram.getUsage(),
                battery.getUsage(),
                disk.getName(),
                disk.getUsage()
        );
    }
}
