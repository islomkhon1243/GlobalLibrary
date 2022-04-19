module com.libraryproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.libraryproject to javafx.fxml;
    exports com.libraryproject;
}