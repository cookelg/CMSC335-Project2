/*
 * File name: App.java
 * Date: 10 November 2026
 * Author: Lawrence Cooke
 * Purpose: Contains the JavaFX application. The input fields will change according
 * to the shape selected from the ComboBox at the top of the window. Each shape 
 * initializes a custom pane for each shape type. All shapes will be drawn in a 3D 
 * space with a perspective camera. The user can zoom in and out with the mouse wheel, 
 * and can rotate the object by clicking-and-dragging. The perspective camera will 
 * remain in the same orientation when switching to another shape. 
 */

package com.cooke.project2;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.control.ComboBox;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

/**
 * JavaFX entry point for CMSC335 Project 2.
 */
public class App extends Application {

    private double anchorX, anchorY;
    private double anchorAngleX = 0;
    private double anchorAngleY = 0;
    private final DoubleProperty angleX = new SimpleDoubleProperty(0);
    private final DoubleProperty angleY = new SimpleDoubleProperty(0);

    // HashMap: keys are strings that describe the shape, values are Field Nodes
    // that correspond to shape constructor, initializes Draw button
    private Map<String, Supplier<FieldPane>> fieldFactories = new LinkedHashMap<>();
    private ComboBox<String> cboShapes = new ComboBox<>();

    @Override
    public void start(Stage primaryStage) {

        fieldFactories.put("Right Triangle", () -> new RightTriPane());
        fieldFactories.put("Isosoles Triangle", () -> new IsoTriPane());
        fieldFactories.put("Equilateral Triangle", () -> new EqualTriPane());
        fieldFactories.put("Circle", () -> new CirclePane());
        fieldFactories.put("Square", () -> new SquarePane());
        fieldFactories.put("Rectangle", () -> new RectanglePane());
        fieldFactories.put("Sphere", () -> new SpherePane());
        fieldFactories.put("Cube", () -> new CubePane());
        fieldFactories.put("Cylinder", () -> new CylinderPane());
        fieldFactories.put("Cone", () -> new ConePane());
        fieldFactories.put("Torus", () -> new TorusPane());

        cboShapes.getItems().addAll(fieldFactories.keySet());
        cboShapes.setPromptText("Choose a shape");
        cboShapes.setPrefWidth(300);

        // Pane that displays the shapes, layer 3
        Group group = new Group();

        // Subscene contains group, used to control camera, layer 2
        SubScene subScene = new SubScene(group, 600, 600, true, SceneAntialiasing.BALANCED);
        subScene.setFill(Color.LIGHTGRAY);
        // Add PerspectiveCamera to the subScene
        Camera camera = new PerspectiveCamera(true);
        camera.translateYProperty().set(0);
        camera.translateXProperty().set(0);
        camera.translateZProperty().set(-1500);
        camera.setNearClip(1);
        camera.setFarClip(5000);
        subScene.setCamera(camera);

        // Pane that holds the initial ComboBox, and the pane with interchanging fields,
        // layer 1
        // StackPane stackLeft = new StackPane(cboShapes, fieldPane);
        BorderPane bp2 = new BorderPane();
        bp2.setTop(cboShapes);

        // Initialize Mouse Event controls
        initMouseControl(group, subScene, primaryStage);

        // Pane that inevitably displays the shapes, layer 1
        Pane shapeDisplayPane = new Pane(subScene);

        // Pane that holds everything else, layer 0
        BorderPane borderPane = new BorderPane();
        // borderPane.setLeft(stackLeft);
        borderPane.setLeft(bp2);
        borderPane.setRight(shapeDisplayPane);

        cboShapes.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                FieldPane p = fieldFactories.get(newVal).get();
                p.initializeDrawButton(group);
                bp2.setBottom(p);
            }
        });

        Scene scene = new Scene(borderPane, 640, 480);
        scene.setFill(Color.SILVER);
        primaryStage.setTitle("CMSC335 Project 2");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    // Initialize all mouse controls to zoom and rotate the camera.
    // source: Genuine Coder (YouTube Channel) JavaFX 3D Tutorial #5
    // https://youtu.be/yinIKzg7duc?si=ZcgmwWQJxbzTJ4mn
    private void initMouseControl(Group group, SubScene subScene, Stage stage) {
        Rotate xRotate;
        Rotate yRotate;

        group.getTransforms().addAll(
                xRotate = new Rotate(0, Rotate.X_AXIS),
                yRotate = new Rotate(0, Rotate.Y_AXIS));
        xRotate.angleProperty().bind(angleX);
        yRotate.angleProperty().bind(angleY);

        subScene.setOnMousePressed(event -> {
            anchorX = event.getSceneX();
            anchorY = event.getSceneY();
            anchorAngleX = angleX.get();
            anchorAngleY = angleY.get();
        });
        subScene.setOnMouseDragged(event -> {
            angleX.set(anchorAngleX - (anchorY - event.getSceneY()));
            angleY.set(anchorAngleY + anchorX - event.getSceneX());
        });

        stage.addEventHandler(ScrollEvent.SCROLL, event -> {
            double delta = group.getTranslateZ() + (-event.getDeltaY() * 3);
            group.translateZProperty().set(delta);
        });

    }
}
