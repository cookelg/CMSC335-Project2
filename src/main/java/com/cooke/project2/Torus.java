/*
 * File name: Torus.java
 * Date:
 * Author: Lawrence Cooke
 * Purpose:
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
        for (int i = 0; i < outerSegments; i++) {
            // u = 2 * PI * i / outerSegments
            double u = (2 * Math.PI * i) / outerSegments;
            // x = (R + r * cos(v)) * cos(u)
            // y = r * sin(v)
            // z = (R + r * cos(v)) * sin(u)
            for (int j = 0; j < innerSegments; j++) {
                // v = 2 * PI * j / innerSegments
                double v = (2 * Math.PI * j) / innerSegments;
                double ringDistance = (majorRadius + (minorRadius * Math.cos(v)));
                coords.addAll(List.of(
                        (float) (ringDistance * Math.cos(u)),
                        (float) (minorRadius * Math.sin(v)),
                        (float) (ringDistance * Math.sin(u))));

            }
        }
        float[] points = new float[coords.size()];
        for (int i = 0; i < points.length; i++) {
            points[i] = coords.get(i);
        }
        List<Integer> facesRaw = new ArrayList<>();
        for (int i = 0; i < outerSegments; i++) {
            for (int j = 0; j < innerSegments; j++) {
                int nextI = (i + 1) % outerSegments;
                int nextJ = (j + 1) % innerSegments;
                int a = i * innerSegments + j;
                int b = nextI * innerSegments + j;
                int c = nextI * innerSegments + nextJ;
                int d = i * innerSegments + nextJ;

                facesRaw.addAll(List.of(
                        a, 0, c, 0, b, 0,
                        a, 0, d, 0, c, 0));
            }
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
        // this.drawModeProperty().set(DrawMode.LINE);

    }
}
