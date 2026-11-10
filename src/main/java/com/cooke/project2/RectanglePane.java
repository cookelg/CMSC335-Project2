package com.cooke.project2;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
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
            double width = Double.parseDouble(tfWidth.getText());
            double height = Double.parseDouble(tfHeight.getText());
            String color = cboColors.getValue();
            Rectangle r = new Rectangle(width, height);

            if (color != null) {
                r.setFill(colorFactories.get(color).get());
            }

            group.getChildren().clear();
            group.getChildren().add(r);
        });
    }

}
