/*
 * File name: CylinderPane.java
 * Date: 10 November 2026
 * Author: Lawrence Cooke
 * Purpose: A Pane with the draw button initialized to create a Cylinder. When the user presses 
 * the draw button, all inputs are checked. If errors exist, a popup window will display with
 * the appropriate error message
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
        // Initialize a Text Formatter for each field that prevents user from inputting
        // invalid characters
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

        // When the draw button is clicked by the user:
        btDraw.setOnAction(event -> {
            try {
                double radius = Double.parseDouble(tfRadius.getText());
                double height = Double.parseDouble(tfHeight.getText());
                // check if the input is out of range
                if (Double.isInfinite(radius) || Double.isNaN(radius) || Double.isInfinite(height)
                        || Double.isNaN(height))
                    throw new IllegalArgumentException("Inputs are out of range");
                // check if the majorRadius is less than the minor radius
                if (radius == 0 || height == 0)
                    throw new IllegalArgumentException("Inputs cannot be 0");
                String color = cboColors.getValue();

                Cylinder c = new Cylinder(radius, height);

                if (color != null) {
                    // s.setFill();
                    Material m = new PhongMaterial(colorFactories.get(color).get());
                    c.setMaterial(m);
                }

                // clears out any existing shapes
                group.getChildren().clear();
                group.getChildren().add(c);
            } catch (NumberFormatException e) {
                // if invalid inputs are detected, each text field is cleared out
                tfHeight.clear();
                tfRadius.clear();
                Alert alert = new Alert(AlertType.ERROR, "Invalid Inputs");
                alert.showAndWait();
            } catch (IllegalArgumentException e) {
                // An alert is shown to the user in a new window
                tfHeight.clear();
                tfRadius.clear();
                Alert alert = new Alert(AlertType.ERROR, e.getMessage());
                alert.showAndWait();
            }
        });
    }

}
