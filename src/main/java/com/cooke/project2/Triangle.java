package com.cooke.project2;

import javafx.scene.shape.Polygon;

public class Triangle extends Polygon {

    public void setIsosceles(double base, double height) {

        double half = base / 2.0;

        Double[] ptsXY = new Double[] {
                0.0, 0.0, // vertex 1
                base, 0.0, // vertex 2
                half, height }; // vertex 3

        this.getPoints().setAll(ptsXY);
    }

    public void setRight(double side1, double side2) {

        Double[] ptsXY = new Double[] {
                0.0, 0.0, // vertex 1
                side1, 0.0, // vertex 2
                0.0, side2 }; // vertex 3

        this.getPoints().setAll(ptsXY);
    }

    public void setEquilateral(double side) {

        double half = side / 2.0;

        Double[] ptsXY = new Double[] {
                0.0, 0.0, // vertex 1
                side, 0.0, // vertex 2
                half, Math.sqrt((side * side) - (half * half)) }; // vertex 3

        this.getPoints().setAll(ptsXY);
    }
}
