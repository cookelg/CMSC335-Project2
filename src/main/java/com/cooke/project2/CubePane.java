package com.cooke.project2;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
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
            double side = Double.parseDouble(tfSide.getText());
            String color = cboColors.getValue();

            Box c = new Box(side, side, side);

            if (color != null) {
                Material m = new PhongMaterial(colorFactories.get(color).get());
                c.setMaterial(m);
            }

            group.getChildren().clear();
            group.getChildren().add(c);
        });
    }

}
