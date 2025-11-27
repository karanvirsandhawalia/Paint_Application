package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * Class representing the oval existing within
 * the paint application.
 *
 * Each oval can be set be moved, visible, set the color
 * and determine if a point exists within it.
 *
 * @author arnold
 *
 */
public class Oval extends Shape {
    private Point pinCorner;
    private double height;
    private double width;
    private javafx.scene.paint.Color color;
    private boolean filled;
    private Integer lineThickness;

    /**
     * Constructor for oval which takes the pinned corner, height, thickness
     * and width of the oval.
     *
     * @param pinCorner the corner of oval pinned to the paint application.
     * @param height the height of the oval
     * @param width the width of the oval
     * @param thickness the thickness of the oval outline
     */
    public Oval(Point pinCorner, double height, double width, Integer thickness){
        this.pinCorner = pinCorner;
        this.height = height;
        this.width = width;
        lineThickness = thickness + 1;
    }

    /**
     * Returns whether the oval is filled or just an outline
     *
     * @return true or false
     */
    public boolean isFilled() {return filled;}

    /**
     * Sets the oval filled instance field to true or false
     * depending on if the oval is filled or just an outline
     *
     * @param filled oval filled -> true, oval unfilled -> false
     */
    public void setFilled(boolean filled) {
        this.filled = filled;
    }

    /**
     * Returns the pinned corner of the oval
     *
     * @return point of pinned corner
     */
    public Point getPinCorner() {
        return this.pinCorner;
    }

    /**
     * Sets the pinned corner of the oval
     *
     * @param pinCorner point of the new pinned corner
     */
    public void setPinCorner(Point pinCorner){
        this.pinCorner = pinCorner;
    }

    /**
     * Returns the width of the oval
     *
     * @return width of the oval
     */
    public double getWidth(){
        return width;
    }

    /**
     * Sets the width of the oval
     *
     * @param width the new width of the oval
     */
    public void setWidth(double width){
        this.width = width;
    }

    /**
     * Returns the height of the oval
     *
     * @return height of the oval
     */
    public double getHeight(){
        return this.height;
    }

    /**
     * Sets the height of the oval
     *
     * @param height the new height of the oval
     */
    public void setHeight(double height){
        this.height = height;
    }

    /**
     * Gets the center of the oval
     *
     * @return the center of the oval
     */
    public Point getCenter(){
        double xCenter = this.pinCorner.x + (this.getWidth() / 2.0);
        double yCenter = this.pinCorner.y + (this.getHeight() / 2.0);
        return new Point(xCenter, yCenter);
    }

    /**
     * Sets the color of the oval to the given color argument.
     *
     * @param color the new color of the oval
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
     * Inverts the color of the oval on the paint application. This
     * represents that the oval is currently being selected.
     */

    public void invertColor(){
        this.color = invertedColor;
    }

    /**
     * Reverts the color of the oval back to the original color.
     */
    public void revertColor(){
        if (this.realColor != null){
            this.color = realColor;
        }
    }

    /**
     * Creates a copy of the Oval with the same attributes as the instance that this method is called on.
     *
     * @return A cloned version of the Oval that is not placed on the panel, nor is it added to the model.
     */
    @Override
    public Oval clone() {
        Oval o = new Oval(new Point(99999999, 99999999), this.height, this.width, this.lineThickness);
        o.setFilled(this.isFilled());
        o.setColor(this.realColor);
        return o;
    }

    /**
     * Moves the Oval instance to the old position that it was on prior to a paste action.
     *
     * @param x The old x position that the Oval was on prior to the paste.
     * @param y The old y position that the Oval was on prior to the paste.
     */
    @Override
    void undoPaste(double x, double y) {
        this.setPinCorner(new Point(x + this.width / 2, y + this.height/2));
    }

    /**
     * Moves the Oval instance to the old position that it was on prior to a paste action.
     *
     * @param x The old x position that the Oval was on prior to the paste.
     * @param y The old y position that the Oval was on prior to the paste.
     */
    @Override
    void moveNewLocation(double x, double y) {
        this.setPinCorner(new Point(x - this.width / 2, y - this.height/2));
    }

    /**
     * Returns the color of the oval
     *
     * @return color
     */
    public javafx.scene.paint.Color getColor() {return this.color;}

    /**
     * Returns whether a point given is within the ovals
     * area on the Paint application.
     *
     * @param P point that is being checked
     * @return whether the oval contains the point (true or false)
     */
    public boolean contains (Point P){
        Point center = this.getCenter();
        double px = (P.x - center.x) / (this.width / 2);
        double py = (P.y - center.y) / (this.height / 2);
        return (Math.pow(px, 2) + Math.pow(py, 2)) <= 1.00;
    }

    /**
     * Moves the oval on the Paint application 5 units
     * to the left.
     */
    @Override
    void moveLeft() {
        pinCorner.x -= 5;
    }

    /**
     * Moves the oval on the Paint application 5 units
     * to the right.
     */
    @Override
    void moveRight() {
        pinCorner.x += 5;
    }

    /**
     * Moves the oval on the Paint application 5 units
     * up.
     */
    @Override
    void moveUp() {
        pinCorner.y -= 5;
    }

    /**
     * Moves the oval on the Paint application 5 units
     * down.
     */
    @Override
    void moveDown() {pinCorner.y += 5;}

    /**
     * Prints the oval to the paint application on the correct
     * layer of the canvas.
     *
     * @param args list containing GraphicsContext, PaintModel, and Oval
     */
    public void print(ArrayList<Object> args) {
        GraphicsContext graphicsContext = (GraphicsContext) args.get(0);
        Oval o1 = (Oval) args.get(1);
        graphicsContext.setFill(o1.getColor());
        graphicsContext.setStroke(o1.getColor());
        double x = o1.getPinCorner().x;
        double y = o1.getPinCorner().y;

        if (o1.isFilled()) {
            graphicsContext.fillOval(x, y, o1.getWidth(), o1.getHeight());
        } else {
            graphicsContext.setLineWidth(lineThickness);
            graphicsContext.strokeOval(x, y, o1.getWidth(), o1.getHeight());
        }
    }
}
