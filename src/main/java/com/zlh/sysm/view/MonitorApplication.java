package com.zlh.sysm.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MonitorApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/zlh/sysm/view/dashboard.fxml"));
        Scene scene = new Scene(loader.load(), 600, 400);
        stage.setTitle("System Monitor");
        stage.setScene(scene);
        stage.show();
    }
}