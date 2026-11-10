/*
 * File name: IsoTriPane.java
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

public class IsoTriPane extends FieldPane {
    private TextField tfBase = new TextField();
    private TextField tfHeight = new TextField();

    public IsoTriPane() {
        super();
        add(tfBase, 0, 1);
        add(tfHeight, 1, 1);
        add(new Label("Base"), 0, 2);
        add(new Label("Height"), 1, 2);
    }

    @Override
    public void initializeDrawButton(Group group) {
        tfBase.setTextFormatter(new TextFormatter<String>(change -> {
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
                double base = Double.parseDouble(tfBase.getText());
                double height = Double.parseDouble(tfHeight.getText());
                if (Double.isInfinite(base) || Double.isNaN(base) || Double.isInfinite(height)
                        || Double.isNaN(height))
                    throw new IllegalArgumentException("Inputs are out of range");
                if (base == 0 || height == 0)
                    throw new IllegalArgumentException("Inputs cannot be 0");
                String color = cboColors.getValue();
                Triangle t = new Triangle();

                if (color != null) {
                    t.setFill(colorFactories.get(color).get());
                }

                t.setIsosceles(base, height);
                group.getChildren().clear();
                group.getChildren().add(t);
            } catch (NumberFormatException e) {
                tfHeight.clear();
                tfBase.clear();
                Alert alert = new Alert(AlertType.ERROR, "Invalid Inputs");
                alert.showAndWait();
            } catch (IllegalArgumentException e) {
                tfHeight.clear();
                tfBase.clear();
                Alert alert = new Alert(AlertType.ERROR, e.getMessage());
                alert.showAndWait();
            }
        });
    }

}
