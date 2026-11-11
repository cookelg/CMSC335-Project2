/*
 * File name: ButtonInitializer.java
 * Date: 10 November 2026
 * Author: Lawrence Cooke
 * Purpose: Interface used to guaruntee each pane will initilize the draw button
 * according to its corresponding shape
 */

package com.cooke.project2;

import javafx.scene.Group;

public interface ButtonInitializer {
    void initializeDrawButton(Group group);
}
