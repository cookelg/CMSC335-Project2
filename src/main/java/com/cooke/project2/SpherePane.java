package com.cooke.project2;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;

public class SpherePane extends FieldPane {
    private TextField tfRadius = new TextField();

    public SpherePane() {
        super();
        add(tfRadius, 0, 1);
        add(new Label("Radius"), 0, 2);
    }

    @Override
    public void initializeDrawButton(Group group) {
        tfRadius.setTextFormatter(new TextFormatter<String>(change -> {
            if (change.getControlNewText().matches("\\d*(\\.\\d*)?")) {
                return change;
            }
            return null;
        }));

        btDraw.setOnAction(event -> {
            double radius = Double.parseDouble(tfRadius.getText());
            String color = cboColors.getValue();

            Sphere s = new Sphere(radius);

            if (color != null) {
                // s.setFill();
                Material m = new PhongMaterial(colorFactories.get(color).get());
                s.setMaterial(m);
            }

            group.getChildren().clear();
            group.getChildren().add(s);
        });
    }

}
