/*
 * File name: CirclePane.java
 * Date: 10 November 2026
 * Author: Lawrence Cooke
 * Purpose: A Pane with the draw button initialized to create a Circle. When the user presses 
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
import javafx.scene.shape.Circle;

public class CirclePane extends FieldPane {
    private TextField tfRadius = new TextField();

    public CirclePane() {
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
            try {
                double radius = Double.parseDouble(tfRadius.getText());
                if (Double.isInfinite(radius) || Double.isNaN(radius))
                    throw new IllegalArgumentException("Inputs are out of range");
                if (radius == 0)
                    throw new IllegalArgumentException("Inputs cannot be zero");
                String color = cboColors.getValue();

                Circle c = new Circle(radius);

                if (color != null) {
                    c.setFill(colorFactories.get(color).get());
                }

                group.getChildren().clear();
                group.getChildren().add(c);
            } catch (NumberFormatException e) {
                tfRadius.clear();
                Alert alert = new Alert(AlertType.ERROR, "Invalid Inputs");
                alert.showAndWait();
            } catch (IllegalArgumentException e) {
                tfRadius.clear();
                Alert alert = new Alert(AlertType.ERROR, e.getMessage());
                alert.showAndWait();
            }
        });
    }

}
