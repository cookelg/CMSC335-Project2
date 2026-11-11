/*
 * File name: SquarePane.java
 * Date: 10 November 2026
 * Author: Lawrence Cooke
 * Purpose: A Pane with the draw button initialized to create a Square. When the user presses 
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

public class SquarePane extends FieldPane {
    private TextField tfSide = new TextField();

    public SquarePane() {
        super();
        add(tfSide, 0, 1);
        add(new Label("Side Length"), 0, 2);
    }

    @Override
    public void initializeDrawButton(Group group) {
        tfSide.setTextFormatter(new TextFormatter<String>(change -> {
            if (change.getControlNewText().matches("\\d*(\\.\\d*)?")) {
                return change;
            }
            return null;
        }));

        btDraw.setOnAction(event -> {
            try {
                double side = Double.parseDouble(tfSide.getText());
                if (Double.isInfinite(side) || Double.isNaN(side))
                    throw new IllegalArgumentException("Inputs are out of range");
                if (side == 0)
                    throw new IllegalArgumentException("Inputs cannot be zero");
                String color = cboColors.getValue();

                Rectangle s = new Rectangle(side, side);

                if (color != null) {
                    s.setFill(colorFactories.get(color).get());
                }

                group.getChildren().clear();
                group.getChildren().add(s);
            } catch (NumberFormatException e) {
                tfSide.clear();
                Alert alert = new Alert(AlertType.ERROR, "Invalid Inputs");
                alert.showAndWait();
            } catch (IllegalArgumentException e) {
                tfSide.clear();
                Alert alert = new Alert(AlertType.ERROR, e.getMessage());
                alert.showAndWait();
            }
        });
    }

}
