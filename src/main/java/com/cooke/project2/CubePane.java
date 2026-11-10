/*
 * File name: CubePane.java
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
import javafx.scene.shape.Box;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;

public class CubePane extends FieldPane {
    private TextField tfSide = new TextField();

    public CubePane() {
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

                Box c = new Box(side, side, side);

                if (color != null) {
                    Material m = new PhongMaterial(colorFactories.get(color).get());
                    c.setMaterial(m);
                }

                group.getChildren().clear();
                group.getChildren().add(c);
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
