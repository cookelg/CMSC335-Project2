/*
 * File name: RightTriPane.java
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

public class RightTriPane extends FieldPane {
    private TextField tfSide1 = new TextField();
    private TextField tfSide2 = new TextField();

    public RightTriPane() {
        super();
        add(tfSide1, 0, 1);
        add(tfSide2, 1, 1);
        add(new Label("Side 1"), 0, 2);
        add(new Label("Side 2"), 1, 2);
    }

    @Override
    public void initializeDrawButton(Group group) {
        tfSide1.setTextFormatter(new TextFormatter<String>(change -> {
            if (change.getControlNewText().matches("\\d*(\\.\\d*)?")) {
                return change;
            }
            return null;
        }));

        tfSide2.setTextFormatter(new TextFormatter<String>(change -> {
            if (change.getControlNewText().matches("\\d*(\\.\\d*)?")) {
                return change;
            }
            return null;
        }));

        btDraw.setOnAction(event -> {
            try {
                double side1 = Double.parseDouble(tfSide1.getText());
                double side2 = Double.parseDouble(tfSide2.getText());
                if (Double.isInfinite(side1) || Double.isNaN(side1) || Double.isInfinite(side2)
                        || Double.isNaN(side2))
                    throw new IllegalArgumentException("Inputs are out of range");
                if (side1 == 0 || side2 == 0)
                    throw new IllegalArgumentException("Inputs cannot be 0");
                String color = cboColors.getValue();
                Triangle t = new Triangle();
                t.setRight(side1, side2);
                if (color != null) {
                    t.setFill(colorFactories.get(color).get());
                }
                group.getChildren().clear();
                group.getChildren().add(t);
            } catch (NumberFormatException e) {
                tfSide1.clear();
                tfSide2.clear();
                Alert alert = new Alert(AlertType.ERROR, "Invalid Inputs");
                alert.showAndWait();
            } catch (IllegalArgumentException e) {
                tfSide1.clear();
                tfSide2.clear();
                Alert alert = new Alert(AlertType.ERROR, e.getMessage());
                alert.showAndWait();
            }
        });
    }

}
