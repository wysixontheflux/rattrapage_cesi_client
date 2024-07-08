module com.enzo.fxapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.kordamp.bootstrapfx.core;
    requires java.net.http;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;


    opens com.enzo.fxapp to javafx.fxml;
    opens com.enzo.fxapp.Controllers to javafx.fxml;
    opens com.enzo.fxapp.Controllers.Client to javafx.fxml; // Ouvre le package Client
    opens com.enzo.fxapp.Controllers.Admin to javafx.fxml; // Ouvre le package Admin

    exports com.enzo.fxapp;
    exports com.enzo.fxapp.Controllers;
    exports com.enzo.fxapp.Controllers.Client; // Exporte le package Client
    exports com.enzo.fxapp.Controllers.Admin; // Exporte le package Admin
}
