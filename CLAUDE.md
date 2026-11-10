# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project

CMSC335 Project 2: a JavaFX (21, Java 17, Maven) GUI where the user picks a 2D or 3D shape, enters its dimensions and a color, and clicks Draw to render it. 3D shapes can be rotated by dragging and zoomed with the scroll wheel.

This is a graded course assignment and the student's own work. Prefer explaining, reviewing, and guiding over writing large portions of the solution unless asked directly. The spec says work not originating from the student is referred for academic integrity review. The full spec and grading rubric are in `docs/CMSC 335 Project 2.pdf`.

## Requirements (from the spec and rubric)

- Shapes: Circle, Square, Triangle, Rectangle, Sphere, Cube, Cone, Cylinder, Torus. The user selects a shape, enters its dimensions, and the shape is displayed in the GUI frame (a drawing, or an image file as the representative for 3D shapes).
- **Design:** a Java class inheritance hierarchy that expresses the is-a and has-a relationships among those shapes.
- **Functionality:** no coding errors and **no compile warnings** (check `mvn compile` output; add `-Xlint:all` via compiler args if needed). The GUI must use event handlers and listeners.
- **Code conventions:** each class in its own `.java` file. Every source file needs a header comment block with file name, date, author, and purpose. Use meaningful names, in-code comments, and correct indentation.
- **Documentation deliverables (written by the student):** UML class diagram, developer's/user's guide (how to compile, set up, and run), a test plan table (#, Description, Screenshot, PASS/FAIL) with screenshots covering each menu component, and lessons learned. Formatting: 12 pt font, 1-inch margins, double spaced, APA-style labels for figures and tables.

## Commands

- Run the app: `mvn javafx:run`
- Compile: `mvn compile`
- Run tests: `mvn test`
- Run a single test: `mvn test -Dtest=AppTest#shouldAnswerWithTrue`

The project is a JPMS module (`src/main/java/module-info.java`, module `com.cooke.project2`). The javafx plugin's `mainClass` is `com.cooke.project2/com.cooke.project2.App`, so it must stay in module/class form.

## Architecture

- **`App`** builds the window: a shape `ComboBox` on the left, and a 600x600 `SubScene` (depth buffer, `PerspectiveCamera` at z=-1500) on the right whose root `Group` holds the drawn shape. `initMouseControl` binds X/Y `Rotate` transforms on that group to mouse drag and moves its `translateZ` on scroll.
- **Shape registry**: `App.fieldFactories` is a `LinkedHashMap<String, Supplier<FieldPane>>` mapping the combo-box label to a pane factory. When a shape is selected, a new pane is built, `initializeDrawButton(group)` is called on it, and it is placed in the left `BorderPane`'s bottom slot.
- **`FieldPane`** (abstract `GridPane`, implements `ButtonInitializer`) supplies the shared Draw button (at 0,0) and a color `ComboBox` (at 2,1) backed by its own `colorFactories` map. Subclasses add their dimension `TextField`s and labels to the grid.
- **One `*Pane` per shape** (`CirclePane`, `CubePane`, `RightTriPane`, …). Each `initializeDrawButton` applies a numeric-only `TextFormatter` (`\d*(\.\d*)?`) to its fields, then sets the Draw action, which parses the fields, builds the JavaFX node, applies the color (`setFill` for 2D shapes, `PhongMaterial` for 3D shapes), and replaces the group's children with it.
- **`Triangle`** extends `Polygon` with `setRight`, `setIsosceles`, and `setEquilateral` helpers that set the vertex points.

### Adding a shape

Create a `FieldPane` subclass following the pattern above and register it in `App.start` with `fieldFactories.put("Label", () -> new XPane())`. `TorusPane.java` and `ConePane.java` are currently empty placeholders and are not registered.
