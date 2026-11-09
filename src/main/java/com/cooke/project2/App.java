package com.cooke.project2;

import javafx.scene.paint.PhongMaterial;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.shape.Circle;
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

    private Map<String, Supplier<Node>> shapeFactories = new LinkedHashMap<>();
    private ComboBox<String> cboShapes = new ComboBox<>();
    private Button btDraw = new Button("Draw Shape");

    @Override
    public void start(Stage primaryStage) {

        shapeFactories.put("Circle", () -> new Circle(60, Color.AQUA));
        shapeFactories.put("Cube", () -> {
            Box b = new Box(100, 100, 100);
            b.setMaterial(new PhongMaterial(Color.ALICEBLUE));
            return b;
        });

        cboShapes.getItems().addAll(shapeFactories.keySet());
        cboShapes.setPromptText("Choose a shape");
        cboShapes.setPrefWidth(300);

        BorderPane borderPane = new BorderPane();

        Box box = new Box(50, 50, 50);
        box.setMaterial(new PhongMaterial(Color.BLUEVIOLET));

        Circle circle = new Circle(75, Color.AQUAMARINE);

        Camera camera = new PerspectiveCamera(true);
        camera.translateYProperty().set(0);
        camera.translateXProperty().set(0);
        camera.translateZProperty().set(-1500);

        StackPane stackLeft = new StackPane(cboShapes);
        Group group = new Group();
        group.getChildren().add(box);
        group.getChildren().addAll(circle);

        SubScene subScene = new SubScene(group, 600, 600, true, SceneAntialiasing.BALANCED);
        subScene.setFill(Color.LIGHTGRAY);

        cboShapes.valueProperty().addListener((obs, oldVal, newVal) -> {
            group.getChildren().clear();
            if (newVal != null) {
                group.getChildren().add(shapeFactories.get(newVal).get());
            }
        });
        initMouseControl(group, subScene, primaryStage);

        camera.setNearClip(1);
        camera.setFarClip(5000);
        subScene.setCamera(camera);

        Pane centerPane = new Pane(subScene);

        borderPane.setLeft(stackLeft);
        borderPane.setRight(centerPane);

        Scene scene = new Scene(borderPane, 640, 480);
        scene.setFill(Color.SILVER);
        primaryStage.setTitle("CMSC335 Project 2");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

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

    // class Group3D extends Group {
    // Rotate r;
    // Transform t = new Rotate();
    //
    // void rotateByX(int angle) {
    // r = new Rotate(angle, Rotate.X_AXIS);
    // t = t.createConcatenation(r);
    // this.getTransforms().clear();
    // this.getTransforms().addAll(t);
    // }
    //
    // void rotateByY(int angle) {
    // r = new Rotate(angle, Rotate.Y_AXIS);
    // t = t.createConcatenation(r);
    // this.getTransforms().clear();
    // this.getTransforms().addAll(t);
    // }
    // }
}
