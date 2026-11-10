/*
 * File name: CylinderPane.java
 * Date:
 * Author: Lawrence Cooke
 * Purpose:
 */

package com.cooke.project2;

import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;

public class CylinderPane extends FieldPane {
    private TextField tfRadius = new TextField();
    private TextField tfHeight = new TextField();

    public CylinderPane() {
        super();
        add(tfRadius, 0, 1);
        add(tfHeight, 1, 1);
        add(new Label("Radius"), 0, 2);
        add(new Label("Height"), 1, 2);
    }

    @Override
    public void initializeDrawButton(Group group) {
        tfRadius.setTextFormatter(new TextFormatter<String>(change -> {
            if (change.getControlNewText().matches("\\d*(\\.\\d*)?")) {
                return change;
            }
            return null;
        }));

        tfHeight.setTextFormatter(new TextFormatter<String>(change -> {
            if (change.getControlNewText().matches("\\d*(\\.\\d*)?")) {
                return change;
            }
            return null;
        }));

        btDraw.setOnAction(event -> {
            try {
                double radius = Double.parseDouble(tfRadius.getText());
                double height = Double.parseDouble(tfHeight.getText());
                if (Double.isInfinite(radius) || Double.isNaN(radius) || Double.isInfinite(height)
                        || Double.isNaN(height))
                    throw new IllegalArgumentException("Inputs are out of range");
                if (radius == 0 || height == 0)
                    throw new IllegalArgumentException("Inputs cannot be 0");
                String color = cboColors.getValue();

                Cylinder c = new Cylinder(radius, height);

                if (color != null) {
                    // s.setFill();
                    Material m = new PhongMaterial(colorFactories.get(color).get());
                    c.setMaterial(m);
                }

                group.getChildren().clear();
                group.getChildren().add(c);
            } catch (NumberFormatException e) {
                tfHeight.clear();
                tfRadius.clear();
                Alert alert = new Alert(AlertType.ERROR, "Invalid Inputs");
                alert.showAndWait();
            } catch (IllegalArgumentException e) {
                tfHeight.clear();
                tfRadius.clear();
                Alert alert = new Alert(AlertType.ERROR, e.getMessage());
                alert.showAndWait();
            }
        });
    }

}
