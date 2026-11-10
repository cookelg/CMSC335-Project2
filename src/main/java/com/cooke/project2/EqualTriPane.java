package com.cooke.project2;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

public class EqualTriPane extends FieldPane {
    private TextField tfSide = new TextField();

    public EqualTriPane() {
        super();
        add(tfSide, 0, 1);
        add(new Label("Side"), 0, 2);
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
            Triangle t = new Triangle();

            if (color != null) {
                t.setFill(colorFactories.get(color).get());
            }

            t.setEquilateral(side);
            group.getChildren().clear();
            group.getChildren().add(t);
        });
    }

}
