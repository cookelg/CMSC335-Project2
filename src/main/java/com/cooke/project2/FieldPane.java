/*
 * File name: FieldPane.java
 * Date: 10 November 2026
 * Author: Lawrence Cooke
 * Purpose: Abstract class that initializes the Draw button and the Color picker 
 * Combo Box
 */

package com.cooke.project2;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

abstract class FieldPane extends GridPane implements ButtonInitializer {

    Button btDraw = new Button("Draw");
    // The supplier will initialize the Color Enum only once it is retrieved from
    // the
    // event handler after the button is pressed. A map with String keys are used to
    // Display the options in the combo box and to retrieve the appropriate
    // initializer.
    Map<String, Supplier<Color>> colorFactories = new LinkedHashMap<>();
    ComboBox<String> cboColors = new ComboBox<>();

    public FieldPane() {
        super();
        colorFactories.put("Red", () -> Color.RED);
        colorFactories.put("Blue", () -> Color.BLUE);
        colorFactories.put("Green", () -> Color.GREEN);
        colorFactories.put("Black", () -> Color.BLACK);
        colorFactories.put("Aqua", () -> Color.AQUA);
        colorFactories.put("Alice Blue", () -> Color.ALICEBLUE);
        cboColors.getItems().addAll(colorFactories.keySet());
        cboColors.setPromptText("Choose a Color");
        cboColors.setPrefWidth(300);
        setHgap(5);
        setVgap(5);
        add(btDraw, 0, 0);
        add(cboColors, 2, 1);
    }
}
