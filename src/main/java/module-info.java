module com.zlh.sysm {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires com.github.oshi;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    exports com.zlh.sysm.view to javafx.graphics;
    opens com.zlh.sysm.presenter to javafx.fxml;

    opens com.zlh.sysm to javafx.fxml;
    exports com.zlh.sysm;
}