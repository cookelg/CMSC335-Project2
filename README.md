# CMSC 335 Project 2: JavaFX Shapes GUI

A JavaFX desktop program that lets the user choose a 2D or 3D shape, enter its dimensions and a color, and draw it in the window.

## Requirements

- **Java Development Kit (JDK) 17 or newer**. Check with `java -version` and `javac -version`.
- **Apache Maven 3.6 or newer**. Check with `mvn -v`. Maven downloads JavaFX 21 automatically, so JavaFX does not need to be installed separately.

## Project Layout

```
src/main/java/com/cooke/project2/   Program source files (package com.cooke.project2)
src/main/java/module-info.java      Java module definition (module com.cooke.project2)
src/test/java/com/cooke/project2/   JUnit 5 unit tests
pom.xml                             Maven build configuration
```

## Compiling and Running

Run all commands from the project root, which is the folder that contains `pom.xml`.

### Option 1: Maven JavaFX plugin

```bash
mvn compile
mvn javafx:run
```

### Option 2: Plain `java` with the module path

```bash
mvn compile
mvn dependency:build-classpath -Dmdep.outputFile=target/classpath.txt -Dmdep.includeScope=runtime
java --enable-native-access=javafx.graphics \
     --module-path "target/classes:$(cat target/classpath.txt)" \
     -m com.cooke.project2/com.cooke.project2.App
```

The second command saves the paths of the JavaFX jars to `target/classpath.txt`. It only needs to be run again after `mvn clean` or a change to `pom.xml`. The `--enable-native-access` flag hides harmless JavaFX warnings on newer JDKs.

## Running the Tests

```bash
mvn test                             # run all tests
mvn test -Dtest=AppTest              # run a single test class
mvn test -Dtest=AppTest#methodName   # run a single test method
```

## Using the Program

When the app is started, a dropdown in the top left lets the user choose one of these shapes:

```
Right Triangle
Isosoles Triangle
Equilateral Triangle
Circle
Square
Rectangle
Sphere
Cube
Cylinder
Cone
Torus
```

After a shape is selected, text fields for that shape's dimensions appear below the dropdown, along with a color dropdown and a **Draw** button. For example, selecting Torus shows fields for the major radius and the minor radius. Clicking **Draw** displays the shape in the gray area on the right. The color is optional.

3D shapes (Sphere, Cube, Cylinder, Cone, Torus) can be rotated by clicking and dragging in the drawing area, and moved closer or farther away with the mouse scroll wheel.

If one of the inputs cannot be used to make a valid shape, an error message pops up and the dimension fields are cleared.

General Rules for inputs:
- The dimension fields only accept digits and a decimal point, so negative numbers cannot be entered
- Fields cannot be empty or 0
- The major radius of a torus must be greater than the minor radius

## Author

Lawrence Cooke

## Lessons learned

<!-- TODO: Write your lessons learned for Project 2 here. -->

## AI Use Disclosure

This project used Anthropic's AI coding assistant, Claude Code (the command-line tool), in the limited ways listed below.

### Claude Code (model Claude Opus 5.5)

- **Created `CLAUDE.md`.** This is a configuration file that gives the AI assistant context about the project. It is not part of the program. At Lawrence's request, it includes the instruction from Project 1 to explain and guide rather than write the solution, and a summary of the Project 2 spec and rubric.
- **Compared the rubric with the code.** The AI pointed out that the source files were missing the required header comments, questioned whether the GUI-based class hierarchy would satisfy the rubric's shape hierarchy requirement, and noted that the `docs/` folder was not gitignored. It did not change any files.
- **Explained how to build a cone mesh.** The AI explained the cone's geometry (apex, base center, and rim points), a vertex index layout, how `TriangleMesh` stores points, texture coordinates, and faces, and how to calculate the size of the faces array. It gave a worked example of the face indices for 4 segments. Lawrence wrote the `Cone` class.
- **Answered questions about `ObservableFloatArray`.** The AI explained that a `TriangleMesh` already contains its arrays, that `addAll` needs primitive `float` values rather than `Float` objects, and provided a short loop that copies a `List<Float>` into a `float[]`. It also pointed out an off-by-one error (`<=` instead of `<`) that added a duplicate rim point.
- **Explained two rendering problems with the cone.** The AI explained that the cone disappeared when viewed from below because of back-face culling and the order of the base triangles' vertices, and suggested reversing that order. It then explained that the cone looked shadowed because JavaFX averages normals across the base and sides, and suggested using face smoothing groups. Lawrence reversed the vertex order.
- **Explained how to build a torus mesh.** The AI explained the torus's parametric equations, how to sample them with two nested loops, the vertex index formula, and how to split each grid cell into two triangles with wrap-around in both directions. It gave tables of expected vertex positions and face indices for checking the output. Lawrence wrote the `Torus` class.
- **Helped debug the torus.** The AI pointed out that the inner loop used `i` instead of `j`, the same off-by-one error as in the cone, and that nothing appeared because the mesh had no faces yet. When the torus was drawn inside-out, it explained that the triangles' vertex order needed to be reversed. Lawrence made the fixes.
- **Showed how to run a single class from the command line.** The AI compiled the project, ran the `main` method in `Torus`, and gave the commands for running it with the JavaFX module path.
- **Suggested input validation.** The AI suggested checking that the major radius is greater than the minor radius in the Draw button's handler, compared showing errors in an `Alert` or a `Label`, and pointed out that empty fields, a lone `.`, and `0` would crash the program or draw nothing. Lawrence wrote the validation code.
- **Appended notes to Lawrence's personal notes file.** At Lawrence's request, the AI added its explanation of the cone's face data and three Mermaid diagrams to a notes file outside the project.
- **Wrote this README.** The AI wrote the README in the same format as Project 1's README, including this disclosure. It checked the compile and run commands against the project.
- **Reviewed two fixes and found a cone rendering bug.** The AI reviewed Lawrence's fixes to the torus index formula (multiplying by the number of points per ring) and the cone's off-by-one loop, and confirmed both were correct. It then found that the cone's side triangles were facing inward. It confirmed this by rendering test images of the `Cone` class from four angles with a separate test program outside the project. It also said that its earlier explanation of the shading problem (averaged normals) was probably wrong, or at most a smaller part of it. Lawrence fixed the side triangle's vertex order.
- **Added blank header comment templates.** At Lawrence's request, the AI added the same header comment block to the top of all 19 `.java` files in `src/`, with the file name and author filled in and the date and purpose left blank for Lawrence to fill in. It did not change any code.

### Summary

Apart from the header comment templates described above, the AI did not edit any Java source files. Lawrence wrote the class design, the Java code, and the comments. The only code the AI provided directly was a short loop for converting a `List<Float>` to a `float[]`, and small examples of `TriangleMesh` method calls.

<!-- TODO: Keep this section up to date. If you use AI for anything else later (code review,
     debugging help, explanations, test ideas, documentation), add it here, and say which
     parts you wrote yourself. -->
