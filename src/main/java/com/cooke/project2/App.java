package com.cooke.project2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * JavaFX entry point for CMSC335 Project 2.
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        Label label = new Label("CMSC335 Project 2");
        Scene scene = new Scene(new StackPane(label), 640, 480);
        stage.setTitle("CMSC335 Project 2");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
