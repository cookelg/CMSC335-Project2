/*
 * File name: Triangle.java
 * Date: 10 November 2026
 * Author: Lawrence Cooke
 * Purpose: Triangle class used to compute the vertices of a triangle, then initialize 
 * the triangle according to the calulated points.
 */

package com.cooke.project2;

import javafx.scene.shape.Polygon;

public class Triangle extends Polygon {

    // method to calculate an Isosceles Triangle using a base and height argument
    public void setIsosceles(double base, double height) {

        double half = base / 2.0;

        Double[] ptsXY = new Double[] {
                0.0, 0.0, // vertex 1
                base, 0.0, // vertex 2
                half, height }; // vertex 3

        this.getPoints().setAll(ptsXY);
    }

    // method to calculate a Right Triangle using 'a' side and 'b' side of a right
    // triangle
    public void setRight(double side1, double side2) {

        Double[] ptsXY = new Double[] {
                0.0, 0.0, // vertex 1
                side1, 0.0, // vertex 2
                0.0, side2 }; // vertex 3

        this.getPoints().setAll(ptsXY);
    }

    // method to calculate an Equilateral Triangle of same length sides.
    public void setEquilateral(double side) {

        double half = side / 2.0;

        Double[] ptsXY = new Double[] {
                0.0, 0.0, // vertex 1
                side, 0.0, // vertex 2
                half, Math.sqrt((side * side) - (half * half)) }; // vertex 3

        this.getPoints().setAll(ptsXY);
    }
}
