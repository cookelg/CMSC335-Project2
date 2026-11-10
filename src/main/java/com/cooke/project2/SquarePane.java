package com.cooke.project2;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
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
            double side = Double.parseDouble(tfSide.getText());
            String color = cboColors.getValue();

            Rectangle s = new Rectangle(side, side);

            if (color != null) {
                s.setFill(colorFactories.get(color).get());
            }

            group.getChildren().clear();
            group.getChildren().add(s);
        });
    }

}
