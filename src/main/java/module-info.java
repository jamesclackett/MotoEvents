module com.jimboidin.motoevents {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.net.http;

    opens com.jimboidin.motoevents to javafx.fxml;
    exports com.jimboidin.motoevents;
}