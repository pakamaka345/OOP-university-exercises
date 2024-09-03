module com.umcs.imageblur {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.datatransfer;
    requires java.desktop;
    requires java.sql;


    opens com.umcs.imageblur to javafx.fxml;
    exports com.umcs.imageblur;
    exports com.umcs.imageblur.server to javafx.graphics;

}