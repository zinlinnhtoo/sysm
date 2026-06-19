package com.zlh.sysm.presenter;

import com.zlh.sysm.dto.SystemSnapshot;
import com.zlh.sysm.service.HardwareService;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class MonitorPresenter {

    @FXML private Button navCpuBtn;
    @FXML private Button navRamBtn;
    @FXML private Button navBatteryBtn;
    @FXML private Button navDiskBtn;

    @FXML private Label activeTitleLabel;
    @FXML private Label activeSubtitleLabel;
    @FXML private Label activePercentageLabel;
    @FXML private AreaChart<Number, Number> activeChart;
    @FXML private NumberAxis xAxis;

    private final XYChart.Series<Number, Number> cpuSeries = new XYChart.Series<>();
    private final XYChart.Series<Number, Number> ramSeries = new XYChart.Series<>();
    private final XYChart.Series<Number, Number> batterySeries = new XYChart.Series<>();
    private final XYChart.Series<Number, Number> diskSeries = new XYChart.Series<>();

    private final HardwareService service = new HardwareService();
    private String currentView = "CPU";
    private int timeTick = 0;

    @FXML
    public void initialize() {
        activeChart.getData().add(cpuSeries);
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> updateDashboard()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        updateDashboard();
    }

    private void updateDashboard() {
        SystemSnapshot data = service.fetchCurrentStats();
        timeTick++;
        cpuSeries.getData().add(new XYChart.Data<>(timeTick, data.cpuUsage()));
        ramSeries.getData().add(new XYChart.Data<>(timeTick, data.ramUsage()));
        batterySeries.getData().add(new XYChart.Data<>(timeTick, data.batteryUsage()));
        diskSeries.getData().add(new XYChart.Data<>(timeTick, data.diskUsage()));

        int MAX_HISTORY = 60;
        if (cpuSeries.getData().size() > MAX_HISTORY) {
            cpuSeries.getData().remove(0);
            ramSeries.getData().remove(0);
            batterySeries.getData().remove(0);
            diskSeries.getData().remove(0);
        }

        xAxis.setLowerBound(timeTick > MAX_HISTORY ? timeTick - MAX_HISTORY : 0);
        xAxis.setUpperBound(Math.max(timeTick, MAX_HISTORY));
        navCpuBtn.setText(String.format("CPU: %.0f%%", data.cpuUsage()));
        navRamBtn.setText(String.format("Memory: %.0f%%", data.ramUsage()));
        navBatteryBtn.setText(String.format("Battery: %.0f%%", data.batteryUsage()));
        navDiskBtn.setText(String.format("Disk: %.0f%%", data.diskUsage()));
        boolean isMediaView = currentView.equals("MEDIA");
        activeChart.setVisible(!isMediaView);
        activePercentageLabel.setVisible(!isMediaView);

        switch (currentView) {
            case "CPU":
                activeSubtitleLabel.setText(data.cpuName());
                activePercentageLabel.setText(String.format("%.1f%%", data.cpuUsage()));
                break;
            case "RAM":
                activeSubtitleLabel.setText("System Physical Memory");
                activePercentageLabel.setText(String.format("%.1f%%", data.ramUsage()));
                break;
            case "BATTERY":
                activeSubtitleLabel.setText("Internal Power Source");
                activePercentageLabel.setText(String.format("%.1f%%", data.batteryUsage()));
                break;
            case "DISK":
                activeSubtitleLabel.setText("Drive: " + data.diskName());
                activePercentageLabel.setText(String.format("%.1f%% Used", data.diskUsage()));
                break;
        }
    }
    @FXML public void switchViewToCpu() { changeView("CPU", "Processor Overview", navCpuBtn, cpuSeries); }
    @FXML public void switchViewToRam() { changeView("RAM", "Memory Overview", navRamBtn, ramSeries); }
    @FXML public void switchViewToBattery() { changeView("BATTERY", "Battery Overview", navBatteryBtn, batterySeries); }
    @FXML public void switchViewToDisk() { changeView("DISK", "Storage Overview", navDiskBtn, diskSeries); }
    private void changeView(String viewName, String title, Button activeBtn, XYChart.Series<Number, Number> chartData) {
        currentView = viewName;
        activeTitleLabel.setText(title);
        String inactiveStyle = "-fx-alignment: center-left; -fx-background-color: transparent; -fx-padding: 10; -fx-text-fill: #6b7280; -fx-cursor: hand;";
        String activeStyle = "-fx-alignment: center-left; -fx-background-color: #e5e7eb; -fx-padding: 10; -fx-text-fill: #111827; -fx-font-weight: bold; -fx-cursor: default;";
        Button[] allButtons = {navCpuBtn, navRamBtn, navBatteryBtn, navDiskBtn};
        for (Button btn : allButtons) {
            if (btn != null) {
                if (btn == activeBtn) {
                    btn.setStyle(activeStyle);
                } else {
                    btn.setStyle(inactiveStyle);
                }
            }
        }
        if (chartData != null) {
            activeChart.getData().clear();
            activeChart.getData().add(chartData);
        }

        updateDashboard();
    }
}