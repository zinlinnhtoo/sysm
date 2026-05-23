package com.zlh.sysm.presenter;

import com.zlh.sysm.model.SystemModel;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.util.Duration;

public class MonitorPresenter {

    @FXML private Label cpuNameLabel;
    @FXML private Label cpuUsageLabel;
    @FXML private ProgressBar cpuBar;
    private SystemModel model;
    @FXML
    public void initialize() {
        model = new SystemModel();
        cpuNameLabel.setText(model.getCpuName());
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> updateDashboard()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void updateDashboard() {
        double usage = model.getCpuUsage();
        cpuBar.setProgress(usage);
        cpuUsageLabel.setText(String.format("CPU Usage: %.1f%%", usage * 100));
    }
}