/*
 * File name: RectanglePane.java
 * Date: 10 November 2026
 * Author: Lawrence Cooke
 * Purpose: A Pane with the draw button initialized to create a Rectangle. When the user presses 
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
import javafx.scene.shape.Rectangle;

public class RectanglePane extends FieldPane {
    private TextField tfWidth = new TextField();
    private TextField tfHeight = new TextField();

    public RectanglePane() {
        super();
        add(tfWidth, 0, 1);
        add(tfHeight, 1, 1);
        add(new Label("Width"), 0, 2);
        add(new Label("Height"), 1, 2);
    }

    @Override
    public void initializeDrawButton(Group group) {
        tfWidth.setTextFormatter(new TextFormatter<String>(change -> {
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
                double width = Double.parseDouble(tfWidth.getText());
                double height = Double.parseDouble(tfHeight.getText());
                if (Double.isInfinite(width) || Double.isNaN(width) || Double.isInfinite(height)
                        || Double.isNaN(height))
                    throw new IllegalArgumentException("Inputs are out of range");
                if (width == 0 || height == 0)
                    throw new IllegalArgumentException("Inputs cannot be 0");
                String color = cboColors.getValue();
                Rectangle r = new Rectangle(width, height);

                if (color != null) {
                    r.setFill(colorFactories.get(color).get());
                }

                group.getChildren().clear();
                group.getChildren().add(r);
            } catch (NumberFormatException e) {
                tfHeight.clear();
                tfWidth.clear();
                Alert alert = new Alert(AlertType.ERROR, "Invalid Inputs");
                alert.showAndWait();
            } catch (IllegalArgumentException e) {
                tfHeight.clear();
                tfWidth.clear();
                Alert alert = new Alert(AlertType.ERROR, e.getMessage());
                alert.showAndWait();
            }
        });
    }

}
