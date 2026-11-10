/*
 * File name: TorusPane.java
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

        btDraw.setOnAction(event -> {
            try {
                double majorRadius = Double.parseDouble(tfMajorRadius.getText());
                double minorRadius = Double.parseDouble(tfMinorRadius.getText());
                if (Double.isInfinite(majorRadius) || Double.isNaN(majorRadius) || Double.isInfinite(minorRadius)
                        || Double.isNaN(minorRadius))
                    throw new IllegalArgumentException("Inputs are out of range");
                if (majorRadius == 0 || minorRadius == 0)
                    throw new IllegalArgumentException("Inputs cannot be 0");
                if (majorRadius < minorRadius)
                    throw new IllegalArgumentException("Major Radius must be greater then Minor Radius");

                String color = cboColors.getValue();

                Torus t = new Torus();
                t.buildTorus(majorRadius, minorRadius);

                if (color != null) {
                    // s.setFill();
                    Material m = new PhongMaterial(colorFactories.get(color).get());
                    t.setMaterial(m);
                }

                group.getChildren().clear();
                group.getChildren().add(t);

            } catch (NumberFormatException e) {
                tfMajorRadius.clear();
                tfMinorRadius.clear();
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
// try {
//
// } catch (NumberFormatException e) {
// tfMajorRadius.clear();
// tfMinorRadius.clear();
// Alert alert = new Alert(AlertType.ERROR, "Invalid Inputs");
// alert.showAndWait();
// } catch (IllegalArgumentException e) {
// tfMajorRadius.clear();
// tfMinorRadius.clear();
// Alert alert = new Alert(AlertType.ERROR, e.getMessage());
// alert.showAndWait();
// }
