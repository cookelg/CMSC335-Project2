/*
 * File name: Torus.java
 * Date: 10 November 2026
 * Author: Lawrence Cooke
 * Purpose: Configures a Triangle Mesh to render a Torus 3D shape with a given major and minor radius
 */

package com.cooke.project2;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;

public class Torus extends MeshView {
    public void buildTorus(double majorRadius, double minorRadius) {
        buildTorus(majorRadius, minorRadius, 20, 20);
    }

    public void buildTorus(double majorRadius, double minorRadius, int outerSegments, int innerSegments) {
        List<Float> coords = new ArrayList<>();
        // outerSegments is the number of rotations around the origin to calculate the
        // points along the
        // surface. innerSegments is the number of points calculated at each iteration
        // of outerSegments.
        for (int i = 0; i < outerSegments; i++) {
            // u = 2 * PI * i / outerSegments; the current angle of rotation around origin
            double u = (2 * Math.PI * i) / outerSegments;
            for (int j = 0; j < innerSegments; j++) {
                // v = 2 * PI * j / innerSegments ; the current angle of rotation around the
                // "tube"
                double v = (2 * Math.PI * j) / innerSegments;
                double ringDistance = (majorRadius + (minorRadius * Math.cos(v)));
                coords.addAll(List.of(
                        (float) (ringDistance * Math.cos(u)), // x = (R + r * cos(v)) * cos(u)
                        (float) (minorRadius * Math.sin(v)), // y = r * sin(v)
                        (float) (ringDistance * Math.sin(u)))); // z = (R + r * cos(v)) * sin(u)

            }
        }
        float[] points = new float[coords.size()];
        // Convert the ArrayList into a regular array of floats. This is the only input
        // accepted by TriangleMesh
        for (int i = 0; i < points.length; i++) {
            points[i] = coords.get(i);
        }
        List<Integer> facesRaw = new ArrayList<>();
        // Associate points with triangles.
        for (int i = 0; i < outerSegments; i++) {
            for (int j = 0; j < innerSegments; j++) {
                int nextI = (i + 1) % outerSegments;
                int nextJ = (j + 1) % innerSegments;
                int a = i * innerSegments + j;
                int b = nextI * innerSegments + j;
                int c = nextI * innerSegments + nextJ;
                int d = i * innerSegments + nextJ;

                facesRaw.addAll(List.of(
                        a, 0, c, 0, b, 0, // side triangle
                        a, 0, d, 0, c, 0)); // base triangle
            }
        }

        int[] faceLst = new int[facesRaw.size()];
        // Convert the ArrayList into a regular array of ints.
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
