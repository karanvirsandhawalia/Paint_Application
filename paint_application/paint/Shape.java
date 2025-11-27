package ca.utoronto.utm.assignment2.paint;

//import java.awt.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * Abstract class representing the shapes existing within
 * the paint application.
 * <p>
 * Each shape can be set be moved, visible, set the color
 * and determine if a point exists within it.
 *
 * @author arnold
 *
 */
abstract class Shape {
     private boolean visible = true;
     Color realColor;
     Color invertedColor;

    /**
     * Sets the visibility status of the Shape. Trye means the
     * shape is visible and False means the shape is hidden.
     *
     * @param visible the visibility of the shape, true or false
     */
    public void setVisible(boolean visible) {this.visible = visible;}

    /**
     * Returns if the shape is visible or not
     *
     * @return true or false
     */
    public boolean isVisible(){return visible;}

    /**
     * Sets the visibility of the shape as opposite. True
     * to False and False to True.
     */
    public void setOpp(){visible=!visible;};

    /**
     * Sets the visibility of the shape as true.
     */
    public void setVisible(){visible = true;}

    /**
     * Sets the visibility of the shape as false.
     */
    public void setVisibleFalse(){visible = false;}

    /**
     * Gets the original color of the shape when drawn
     * on the paint application.
     */
    public Color getRealColor(){return realColor;};

    /**
     * Inverts the color of the shape on the paint application. This
     * represents that the shape is currently being selected.
     */
    abstract void invertColor();

    /**
     * Returns the color of the shape
     *
     * @return color
     */
    abstract Color getColor();

    /**
     * Reverts the color of the shape back to the original color.
     */
    abstract void revertColor();

    /**
     * Sets the color of the shape to the given color argument.
     *
     * @param color the new color of the shape
     */
    abstract void setColor(Color color);

    /**
     * Prints the shape to the paint application on the correct
     * layer of the canvas.
     *
     * @param args list containing GraphicsContext, PaintModel, and Shape
     */
    abstract void print(ArrayList<Object> args);

    /**
     * Returns whether a point given is within the shape objects
     * area on the Paint application.
     *
     * @param P point that is being checked
     * @return whether the shape contains the point (true or false)
     */
    abstract boolean contains (Point P);

    /**
     * Moves the shape on the Paint application 5 units
     * to the left.
     */
    abstract void moveLeft();

    /**
     * Moves the shape on the Paint application 5 units
     * to the right.
     */
    abstract void moveRight();

    /**
     * Moves the shape on the Paint application 5 units
     * up.
     */
    abstract void moveUp();

    /**
     * Moves the shape on the Paint application 5 units
     * down.
     */
    abstract void moveDown();

    /**
     * Creates a copy of the shape with the same attributes as the instance that this method is called on.
     *
     * @return A cloned version of the Shape that is not placed on the panel, nor is it added to the model.
     */
    public abstract Shape clone();

    /**
     * Moves the shape instance to the old position that it was on prior to a paste action.
     *
     * @param x The old x position that the shape was on prior to the paste.
     * @param y The old y position that the shape was on prior to the paste.
     */
    abstract void undoPaste(double x, double y);

    /**
     * Moves the shape to a new location, called often with the paste methods.
     *
     * @param x The new x position that the shape instance is being moved to.
     * @param y The new y position that the shape instance is being moved to.
     */
    abstract void moveNewLocation(double x, double y);

    }
