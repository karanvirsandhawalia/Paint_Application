package ca.utoronto.utm.assignment2.paint;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * Represents a rectangle shape in the Paint application.
 *
 * This class allows a rectangle object to contain two corners,
 * color, filled, and line thickness which is stored
 * in the PaintModel alongside other shapes to represent the
 * drawings on the current layer of the canvas.
 *
 * @author arnold
 *
 */
public class Rectangle extends Shape {
    private Point corner1;
    private Point corner2;
    private javafx.scene.paint.Color color;
    private boolean filled;
    private Integer lineThickness;

    /**
     * Constructs a new Rectangle object stored in the PaintModel based
     * on user input on the Paint Application.
     *
     * @param tl top-left corner of the rectangle
     * @param bt bottom-right corner of the rectangle
     * @param thickness the line thickness of the rectangle object drawn by the user
     */
    public Rectangle(Point tl, Point bt, Integer thickness) {
        this.corner1 = tl;
        this.corner2 = bt;
        lineThickness = thickness + 1;
    }

    /**
     * Returns whether the rectangle drawn is filled.
     *
     * @return true or false
     */
    public boolean isFilled() {
        return filled;
    }

    /**
     * Sets whether the current rectangle is filled in or not
     * on the current canvas.
     *
     * @param filled the status of the rectangles fill on the canvas
     */
    public void setFilled(boolean filled) {
        this.filled = filled;
    }


    /**
     * Returns corner 1 of the rectangle
     *
     * @return  point of corner 1 of the rectangle
     */
    public Point getCorner1() {
        return corner1;
    }

    /**
     * Returns corner 2 of the rectangle
     *
     * @return  point of corner 2 of the rectangle
     */
    public Point getCorner2() {
        return corner2;
    }

    /**
     * Sets corner 1 of the rectangle
     *
     * @param c1 Sets corner 1 of the rectangle
     */
    public void setCorner1(Point c1) {
        corner1 = c1;
    }

    /**
     * Sets corner 2 of the rectangle
     *
     * @param c2 Sets corner 2 of the rectangle
     */
    public void setCorner2(Point c2) {
        corner2 = c2;
    }

    /**
     * Sets the color of the rectangle object.
     *
     * @param color new color of the rectangle object
     */
    public void setColor(javafx.scene.paint.Color color) {
        if (this.realColor == null){
            this.realColor = color;
        }
        if (this.invertedColor == null){
            if (color.equals(Color.BLACK))
                this.invertedColor = Color.LIGHTGREY;
            else{this.invertedColor = color.invert();}
        }
        this.color = color;

    }

    /**
     * Inverts the color of the rectangle object displayed
     * on the Paint application.
     *
     */
    public void invertColor(){
        this.color = invertedColor;
    }

    /**
     * Resets the color of the rectangle object displayed
     * on the Paint application to its original color.
     *
     */
    public void revertColor(){
        if (this.realColor != null){
            this.color = realColor;
        }
    }

    /**
     * Returns whether a point given is within the rectangle objects
     * area on the Paint application.
     *
     * @param P point that is being checked
     * @return whether the rectangle contains the point (true or false)
     */
    public boolean contains(Point P) {
        double lowerx;
        double higherx;
        double lowery;
        double highery;
        if (corner1.x <= corner2.x) {
            lowerx = corner1.x;
            higherx = corner2.x;
        } else {
            lowerx = corner2.x;
            higherx = corner1.x;
        }
        if (corner1.y <= corner2.y) {
            lowery = corner1.y;
            highery = corner2.y;
        } else {
            lowery = corner2.y;
            highery = corner1.y;
        }
        boolean a = (P.x >= lowerx) && (P.x <= higherx) && (P.y >= lowery) && (P.y <= highery);
        return a;
    }
    /**
     * Creates a copy of the Rectangle with the same attributes as the instance that this method is called on.
     *
     * @return A cloned version of the Rectangle that is not placed on the panel, nor is it added to the model.
     */
    @Override
    public Rectangle clone() {
        Point a = new Point(this.getCorner1().x + 99999, this.getCorner1().y + 99999);
        Point b = new Point(this.getCorner2().x + 99999, this.getCorner2().y + 99999);
        Rectangle r = new Rectangle(a, b, this.lineThickness);
        r.setFilled(this.isFilled());
        r.setColor(this.realColor);
        return r;
    }

    /**
     * Moves the Rectangle instance to the old position that it was on prior to a paste action.
     *
     * @param x The old x position that the Rectangle was on prior to the paste.
     * @param y The old y position that the Rectangle was on prior to the paste.
     */
    @Override
    void undoPaste(double x, double y) {
        double absDistanceX = Math.abs(corner1.x - corner2.x);
        double absDistanceY = Math.abs(corner1.y -corner2.y);
        this.setCorner1(new Point(x, y));
        this.setCorner2(new Point(x+absDistanceX, y+absDistanceY));
    }

    /**
     * Moves the Rectangle to a new location, called often with the paste methods.
     *
     * @param x The new x Rectangle that the shape instance is being moved to.
     * @param y The new y Rectangle that the shape instance is being moved to.
     */
    @Override
    void moveNewLocation(double x, double y) {
        double absDistanceX = Math.abs(corner1.x - corner2.x);
        double absDistanceY = Math.abs(corner1.y -corner2.y);
        this.setCorner1(new Point(x, y));
        this.setCorner2(new Point(x+absDistanceX, y+absDistanceY));

    }

    /**
     * Moves the rectangle on the Paint application 5 units
     * to the left.
     */
    @Override
    void moveLeft() {
        corner1.x -= 5;
        corner2.x -= 5;
    }

    /**
     * Moves the rectangle on the Paint application 5 units
     * to the right.
     */
    @Override
    void moveRight() {
        corner1.x += 5;
        corner2.x += 5;
    }

    /**
     * Moves the rectangle on the Paint application 5 units
     * up.
     */
    @Override
    void moveUp() {
        corner1.y -= 5;
        corner2.y -= 5;
    }

    /**
     * Moves the rectangle on the Paint application 5 units
     * down.
     */
    @Override
    void moveDown() {
        corner1.y += 5;
        corner2.y += 5;
    }

    /**
     * Returns the color of the rectangle object.
     *
     * @return color of rectangle
     */
    public javafx.scene.paint.Color getColor() {
        return this.color;
    }

    /**
     * Prints the rectangle onto the current layer of the
     * canvas displayed on the Paint Application.
     *
     * @param args array containing the GraphicsContext, rectangle, and PaintModel
     */
    public void print(ArrayList<Object> args) {
        GraphicsContext graphicsContext = (GraphicsContext) args.get(0);
        Rectangle r = (Rectangle) args.get(1);
        graphicsContext.setFill(r.getColor());
        graphicsContext.setStroke(r.getColor());
        Point c1 = r.getCorner1();
        Point c2 = r.getCorner2();

        double x = Math.min(c1.x, c2.x);
        double y = Math.min(c1.y, c2.y);
        double width = Math.abs(c1.x - c2.x);
        double height = Math.abs(c1.y - c2.y);

        if (r.isFilled()) {
            graphicsContext.fillRect(x, y, width, height);
        } else {
            graphicsContext.setLineWidth(lineThickness);
            graphicsContext.strokeRect(x, y, width, height);
        }
    }
}