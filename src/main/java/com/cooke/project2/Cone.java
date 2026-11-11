/*
 * File name: Cone.java
 * Date: 10 November 2026
 * Author: Lawrence Cooke
 * Purpose: Configures a Triangle Mesh to render a Cone 3D shape with a given height and radius
 */

package com.cooke.project2;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;

public class Cone extends MeshView {
    public void buildCone(double radius, double height) {
        buildCone(radius, height, 20);
    }

    public void buildCone(double radius, double height, int segments) {
        List<Float> coords = new ArrayList<>(List.of(
                0.0f, (float) (-height / 2.0f), 0.0f, // point 1 is the apex of the cone
                0.0f, (float) (height / 2.0f), 0.0f)); // point 2 is the base center
        // Calculates the x, y, and z coordinates for each segments along the base
        // of the cone
        // cone base point k: (r * cos(theta), h / 2, r * sin(theta))
        // where theta = 2 * PI * k / N; k = 0, 1, ... N-1; N = segments
        for (int i = 0; i < segments; i++) {
            double theta = (2 * Math.PI * i) / segments;
            double cosTheta = Math.cos(theta);
            double sinTheta = Math.sin(theta);
            coords.addAll(List.of(
                    (float) (radius * cosTheta),
                    (float) (height / 2.0f),
                    (float) (radius * sinTheta)));
        }
        float[] points = new float[coords.size()];
        // Convert the ArrayList into a regular array of floats. This is the only input
        // accepted by TriangleMesh
        for (int i = 0; i < points.length; i++) {
            points[i] = coords.get(i);
        }

        List<Integer> facesRaw = new ArrayList<>();

        // Associate points with triangles.
        for (int i = 0; i < segments; i++) {
            int cur = i + 2;
            int next = ((i + 1) % segments) + 2;
            facesRaw.addAll(List.of(
                    0, 0, cur, 0, next, 0, // side triangle
                    1, 0, next, 0, cur, 0)); // base triangle

        }

        int[] faceLst = new int[facesRaw.size()];
        for (int i = 0; i < faceLst.length; i++) {
            faceLst[i] = facesRaw.get(i);
        }

        TriangleMesh mesh = new TriangleMesh();
        mesh.getPoints().addAll(points);
        mesh.getTexCoords().addAll(0, 0);
        mesh.getFaces().addAll(faceLst);

        this.setMesh(mesh);
    }

}
