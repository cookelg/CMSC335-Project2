module com.cooke.project2 {
    requires transitive javafx.controls;
    requires javafx.fxml;

    opens com.cooke.project2 to javafx.fxml;
    exports com.cooke.project2;
}
