/*
 * File name: TorusPane.java
 * Date: 10 November 2026
 * Author: Lawrence Cooke
 * Purpose: A Pane with the draw button initialized to create a Torus. When the user presses 
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

public class TorusPane extends FieldPane {
    private TextField tfMajorRadius = new TextField();
    private TextField tfMinorRadius = new TextField();

    public TorusPane() {
        super();
        add(tfMajorRadius, 0, 1);
        add(tfMinorRadius, 1, 1);
        add(new Label("Major Radius"), 0, 2);
        add(new Label("Minor Radius"), 1, 2);
    }

    @Override
    public void initializeDrawButton(Group group) {
        // Initialize a Text Formatter for each field that prevents user from inputting
        // invalid characters
        tfMajorRadius.setTextFormatter(new TextFormatter<String>(change -> {
            if (change.getControlNewText().matches("\\d*(\\.\\d*)?")) {
                return change;
            }
            return null;
        }));

        tfMinorRadius.setTextFormatter(new TextFormatter<String>(change -> {
            if (change.getControlNewText().matches("\\d*(\\.\\d*)?")) {
                return change;
            }
            return null;
        }));

        // When the draw button is clicked by the user:
        btDraw.setOnAction(event -> {
            try {
                double majorRadius = Double.parseDouble(tfMajorRadius.getText());
                double minorRadius = Double.parseDouble(tfMinorRadius.getText());
                // check if the input is out of range
                if (Double.isInfinite(majorRadius) || Double.isNaN(majorRadius) || Double.isInfinite(minorRadius)
                        || Double.isNaN(minorRadius))
                    throw new IllegalArgumentException("Inputs are out of range");
                // check if the input is equal to zero
                if (majorRadius == 0 || minorRadius == 0)
                    throw new IllegalArgumentException("Inputs cannot be 0");
                // check if the majorRadius is less than the minor radius
                if (majorRadius < minorRadius)
                    throw new IllegalArgumentException("Major Radius must be greater then Minor Radius");

                String color = cboColors.getValue();

                Torus t = new Torus();
                t.buildTorus(majorRadius, minorRadius);

                // the default color is black
                if (color != null) {
                    Material m = new PhongMaterial(colorFactories.get(color).get());
                    t.setMaterial(m);
                }

                // clears out any existing shapes
                group.getChildren().clear();
                group.getChildren().add(t);

            } catch (NumberFormatException e) {
                // if invalid inputs are detected, each text field is cleared out
                tfMajorRadius.clear();
                tfMinorRadius.clear();
                // An alert is shown to the user in a new window
                Alert alert = new Alert(AlertType.ERROR, "Invalid Inputs");
                alert.showAndWait();
            } catch (IllegalArgumentException e) {
                tfMajorRadius.clear();
                tfMinorRadius.clear();
                Alert alert = new Alert(AlertType.ERROR, e.getMessage());
                alert.showAndWait();
            }
        });
    }

}
