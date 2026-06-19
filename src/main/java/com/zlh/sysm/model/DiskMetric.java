package com.zlh.sysm.model;

import oshi.software.os.OSFileStore;
import java.util.List;

public class DiskMetric extends HardwareMetric {
    private OSFileStore mainDrive;

    public DiskMetric(List<OSFileStore> fileStores) {
        this.name = "Primary Disk";
        if (!fileStores.isEmpty()) {
            this.mainDrive = fileStores.get(0);
            this.name = mainDrive.getName();
        }
    }

    @Override
    public double getUsage() {
        if (mainDrive == null) return 0.0;
        mainDrive.updateAttributes();

        long total = mainDrive.getTotalSpace();
        long usable = mainDrive.getUsableSpace();
        long used = total - usable;

        if (total == 0) return 0.0;
        return ((double) used / total) * 100;
    }
}