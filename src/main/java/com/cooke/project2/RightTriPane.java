package com.cooke.project2;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

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
            if (change.getControlNewText().matches("-?\\d*(\\.\\d*)?")) {
                return change;
            }
            return null;
        }));

        tfSide2.setTextFormatter(new TextFormatter<String>(change -> {
            if (change.getControlNewText().matches("-?\\d*(\\.\\d*)?")) {
                return change;
            }
            return null;
        }));

        btDraw.setOnAction(event -> {
            double side1 = Double.parseDouble(tfSide1.getText());
            double side2 = Double.parseDouble(tfSide2.getText());
            String color = cboColors.getValue();
            Triangle t = new Triangle();
            t.setRight(side1, side2);
            if (color != null) {
                t.setFill(colorFactories.get(color).get());
            }
            group.getChildren().clear();
            group.getChildren().add(t);
        });
    }

}
