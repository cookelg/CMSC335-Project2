package com.cooke.project2;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
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
            double radius = Double.parseDouble(tfRadius.getText());
            String color = cboColors.getValue();

            Circle c = new Circle(radius);

            if (color != null) {
                c.setFill(colorFactories.get(color).get());
            }

            group.getChildren().clear();
            group.getChildren().add(c);
        });
    }

}
