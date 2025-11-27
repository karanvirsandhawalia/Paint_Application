package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * This class represents the squiggle existing within
 * the paint application.
 *
 * Each squiggle can be set be moved, visible, set the color
 * and determine if a point exists within it.
 *
 * @author arnold
 *
 */

public class Squiggle extends Shape{
    private ArrayList<Point> points;
    private Integer lineThickness = 1;
    private Color color;

    /**
     * A Squiggle shape is created at the given point
     * with the given line thickness and color
     *
     * @param point The point where the squiggle is created
     * @param lineThickness The line thickness of the squiggle
     * @param color the color of the squiggle
     */
    public Squiggle(Point point, Integer lineThickness, Color color) {
        this.points = new ArrayList<>();
        points.add(point);
        this.lineThickness = lineThickness+1;
        this.color = color;
    }

    /**
     * Add another "point" to the squiggle's shape.
     * @param point The point being added to the squiggles creation
     */
    public void addPoint(Point point) {points.add(point);}

    /**
     * @return the points of the squiggle
     */
    public ArrayList<Point> getPoint(){return points;}

    /**
     * Sets the color of the squiggle to the given color argument.
     *
     * @param color the new color of the squiggle
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
    public void setPoints(ArrayList<Point> a){
        this.points = a;
    }

    /**
     * Creates a copy of Squiggle with the same attributes as the instance that this method is called on.
     *
     * @return A cloned verion of the Shape that is not placed on the panel, nor is it added to the model.
     */
    @Override
    public Squiggle clone() {
        ArrayList<Point> a = new ArrayList<>();
        Point b = new Point(this.points.getFirst().x, this.points.getFirst().x);
        Squiggle squiggle = new Squiggle(b, this.lineThickness - 1, this.color);
        for (int i = 1; i < this.points.size(); i++){
            a.add(new Point(this.points.get(i).x + 999999, this.points.get(i).y + 999999));
        }
        squiggle.setPoints(a);
        squiggle.setColor(this.realColor);
        return squiggle;
    }
    /**
     * Moves the shape instance to the old position that it was on prior to a paste action.
     *
     * @param x The old x position that the shape was on prior to the paste.
     * @param y The old y position that the shape was on prior to the paste.
     */
    @Override
    void undoPaste(double x, double y) {
        if (!this.points.isEmpty()){
            Point first = this.points.getFirst();
            double changeX = x - first.x;
            double changey = y - first.y;
            for (Point p : this.points){
                p.setX(p.x + changeX);
                p.setY(p.y + changey);
            }
        }
    }
    /**
     * Moves the Squiggle to a new location, called often with the paste methods.
     *
     * @param x The new x position that the shape instance is being moved to.
     * @param y The new y position that the shape instance is being moved to.
     */
    @Override
    void moveNewLocation(double x, double y) {
        if (!this.points.isEmpty()){
            Point first = this.points.getFirst();
            double changeX = x - first.x;
            double changey = y - first.y;
            for (Point p : this.points){
                p.setX(p.x + changeX);
                p.setY(p.y + changey);
            }
        }
    }

    /**
     * Inverts the color of the squiggle on the paint application. This
     * represents that the shape is currently being selected.
     *
     */
    public void invertColor(){
        this.color = invertedColor;
    }

    /**
     * Reverts the color of the squiggle back to the original color.
     *
     */
    public void revertColor(){
        if (this.realColor != null){
            this.color = realColor;
        }
    }

    /**
     * Returns the color of the squiggle
     *
     * @return color
     */
    public Color getColor() {return color;}

    /**
     * Prints the squiggle to the paint application on the correct
     * layer of the canvas.
     *
     * @param args list containing GraphicsContext, PaintModel, and squiggle
     */
    @Override
    public void print(ArrayList<Object> args) {
        GraphicsContext g2d = (GraphicsContext) args.getFirst();
        PaintModel model = (PaintModel) args.get(2);
        g2d.setLineWidth(lineThickness);

        g2d.setStroke(color);

        for(int k=0;k<points.size()-1; k++) {
            Point p1 = points.get(k);
            Point p2 = points.get(k + 1);
            g2d.strokeLine(p1.x, p1.y, p2.x, p2.y);
        }
    }

    /**
     * Returns whether a point given is within the squiggle objects
     * area on the Paint application.
     *
     * @param P point that is being checked
     * @return whether the squiggle contains the point (true or false)
     */
    @Override
    public boolean contains(Point P) {
        for (Point point : points) {
                    if ((Math.abs(P.x - point.x) < 12.0) && (Math.abs(P.y - point.y) < 12.0)) {
                        return true;
                    }
                }
        return false;
        }

    /**
     * Moves the squiggle on the Paint application 5 units
     * to the left.
     */
    @Override
    void moveLeft() {
        for (Point point : points) {
            point.x -= 5;
        }
    }

    /**
     * Moves the squiggle on the Paint application 5 units
     * to the right.
     */
    @Override
    void moveRight() {
        for (Point point : points) {
            point.x += 5;
        }
    }

    /**
     * Moves the squiggle on the Paint application 5 units
     * up.
     */
    @Override
    void moveUp() {
        for (Point point : points) {
            point.y -= 5;
        }
    }

    /**
     * Moves the squiggle on the Paint application 5 units
     * down.
     */
    @Override
    void moveDown() {
        for (Point point : points) {
            point.y += 5;
        }
    }
}

