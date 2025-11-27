package ca.utoronto.utm.assignment2.paint;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * Represents a polyline in the Paint application.
 *
 * This class allows a Polyline object to contain vertices,
 * color, filled, and line thickness which is stored
 * in the PaintModel alongside other shapes to represent the
 * drawings on the current layer of the canvas.
 *
 * @author arnold
 *
 */
public class Polyline extends Shape{

    private ArrayList<Point> vertices;
    private Color color;
    private Integer lineThickness;
    public boolean completed = false;


    /**
     * Constructs a new Polyline object stored in the PaintModel based
     * on user input on the Paint Application.
     *
     * @param lineThickness the line thickness of the polyline
     */
    public Polyline (Integer lineThickness){
        this.vertices = new ArrayList<>();
        this.lineThickness = lineThickness + 1;
    }

    /**
     * Sets the color of the polyline object.
     *
     * @param color new color of the polyline object
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
     * Sets the vertices of this Polyline instance to the input
     *
     * @param vertices New vertices for this object
     */
    public void setVertices(ArrayList<Point> vertices) {
        this.vertices = vertices;
    }

    /**
     * Creates a copy of the Polyline with the same attributes as the instance that this method is called on.
     *
     * @return A cloned version of the Polyline that is not placed on the panel, nor is it added to the model.
     */
    @Override
    public Polyline clone() {
        Polyline a = new Polyline(this.lineThickness);
        ArrayList<Point> b = new ArrayList<>();
        for (Point p: this.vertices){
            b.add(p);

        }
        a.setVertices(b);
        a.setColor(this.realColor);
        return a;
    }

    /**
     * Moves the Polyline instance to the old position that it was on prior to a paste action.
     *
     * @param x The old x position that the Polyline was on prior to the paste.
     * @param y The old y position that the Polyline was on prior to the paste.
     */
    @Override
    void undoPaste(double x, double y) {
        this.moveNewLocation(x, y);
    }

    /**
     * Moves the Polyline to a new location, called often with the paste methods.
     *
     * @param x The new x position that the Polyline instance is being moved to.
     * @param y The new y position that the Polyline instance is being moved to.
     */
    @Override
    void moveNewLocation(double x, double y) {
        if (!this.vertices.isEmpty()){
            Point first = this.vertices.getFirst();
            double changeX = x - first.x;
            double changey = y - first.y;
            for (Point p : this.vertices){
                p.setX(p.x + changeX);
                p.setY(p.y + changey);
            }
        }
    }

    /**
     * Inverts the color of the polyline object displayed
     * on the Paint application.
     *
     */
    @Override
    public void invertColor(){
        this.color = invertedColor;
    }

    /**
     * Resets the color of the polyline object displayed
     * on the Paint application to its original color.
     *
     */
    public void revertColor(){
        if (this.realColor != null){
            this.color = realColor;
        }
    }

    /**
     * Returns the color of the polyline object.
     *
     * @return color of polyline
     */
    public javafx.scene.paint.Color getColor() {return this.color;}


    /**
     * Gets the vertices of the polyline.
     *
     * @return vertices of the polyline
     */
    public ArrayList<Point> getVertices(){return this.vertices;}

    /**
     * Prints the polyline onto the current layer of the
     * canvas displayed on the Paint Application.
     *
     * @param args array containing the GraphicsContext, polyline, and PaintModel
     */
    @Override
    void print(ArrayList<Object> args) {
        GraphicsContext g2d = (GraphicsContext) args.getFirst();
        g2d.setLineWidth(lineThickness);
        g2d.setStroke(color);
        Polyline p = (Polyline) args.get(1);

            ArrayList<Point> vertices = p.getVertices();

            if (vertices.size() > 1){

                for (int i = 0; i < vertices.size() - 1; i++){
                    Point point1 = vertices.get(i);
                    Point point2 = vertices.get(i + 1);
                    g2d.strokeLine(point1.x, point1.y, point2.x, point2.y);
                }
            }

    }

    /**
     * Returns whether a point given is within the polyline objects
     * area on the Paint application.
     *
     * @param p point that is being checked
     * @return whether the polyline contains the point (true or false)
     */
    @Override
    public boolean contains(Point p) {
        for (int i = 0; i < vertices.size() - 1; i++) {

            double value = Math.pow(vertices.get(i).x - vertices.get(i+1).x, 2);
            value += Math.pow(vertices.get(i).y - vertices.get(i+1).y, 2);
            if (value == 0.0) {
                double distance = Math.sqrt(Math.pow(p.x - vertices.get(i).x, 2) + Math.pow(p.y - vertices.get(i).y, 2));
                if (distance <= 5) return true;
                continue;
            }
            double value2 = (p.x - vertices.get(i).x) * (vertices.get(i+1).x - vertices.get(i).x);
            value2 += (p.y - vertices.get(i).y) * (vertices.get(i+1).y - vertices.get(i).y);
            value2 = value2 / value;
            if (value2 > 1){
                value2 = 1;
            }
            value2 = Math.max(0.0, value2);
            double s = vertices.get(i).x + value2 * (vertices.get(i+1).x - vertices.get(i).x);
            double t = vertices.get(i).y + value2 * (vertices.get(i+1).y - vertices.get(i).y);
            double distance = Math.pow(p.x - s, 2);
            distance += Math.pow(p.y - t, 2);
            distance = Math.sqrt(distance);

            if (distance <= 5) {
                return true;
            }
        }
        return false;
    }

    /**
     * Moves the polyline on the Paint application 5 units
     * to the left.
     */
    @Override
    void moveLeft() {
        for (Point point: this.vertices){
            point.x -= 5;
        }
    }

    /**
     * Moves the polyline on the Paint application 5 units
     * to the right.
     */
    @Override
    void moveRight() {
        for (Point point: this.vertices){
            point.x += 5;
        }
    }

    /**
     * Moves the polyline on the Paint application 5 units
     * up.
     */
    @Override
    void moveUp() {
        for (Point point: this.vertices){
            point.y -= 5;
        }
    }

    /**
     * Moves the polyline on the Paint application 5 units
     * down.
     */
    @Override
    void moveDown() {
        for (Point point : this.vertices) {
            point.y += 5;
        }
    }

    /**
     * Sets a new vertex for the polyline
     *
     * @param p point of new vertex of polyline
     */
    public void newVertex(Point p){
        this.vertices.add(p);
    }

}
