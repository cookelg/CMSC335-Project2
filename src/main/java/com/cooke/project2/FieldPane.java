/*
 * File name: FieldPane.java
 * Date:
 * Author: Lawrence Cooke
 * Purpose:
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
