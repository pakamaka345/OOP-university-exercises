module com.umcs.second_kolokwium1 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires static lombok;

    opens com.umcs.second_kolokwium1 to javafx.fxml;
    exports com.umcs.second_kolokwium1;
}