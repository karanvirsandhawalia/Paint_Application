package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * This class represents the triangle existing within
 * the paint application.
 *
 * Each triangle can be set be moved, visible, set the color
 * and determine if a point exists within it.
 *
 * @author arnold
 *
 */
public class Triangle extends Shape {

    private Point topLeft;
    private Point bottomLeft;
    private Point topRight;
    private Point bottomRight;
    private javafx.scene.paint.Color color;
    private Point triangleTopPoint;
    private boolean filled;
    private Integer lineThickness;

    /**
     * A triangle shape begins creation at the following point
     * with the given line thickness
     *
     * @param topLeft the point at where the triangle creation starts
     * @param thickness the line thickness of the square
     */
    public Triangle(Point topLeft, Integer thickness) {
        this.topLeft = topLeft;
        lineThickness = thickness + 1;

    }

    /**
     * A triangle shape ends creation at the following point
     *
     * @param bottomRight the point at where the triangle creation ends
     */
    public void setBottomRight(Point bottomRight) {
        this.bottomRight = bottomRight;
        if (topLeft.y > bottomRight.y) {
            this.bottomRight = topLeft;
            topLeft = bottomRight;
        }

        findBottomLeft();
        findTopRight();
        findTriangleTopPoint();

    }

    /**
     * @return whether the triangle is currently filled or not
     */
    public boolean isFilled() {return filled;}

    /**
     * Set the triangle to fill
     * @param filled fill in the shape
     */
    public void setFilled(boolean filled) {
        this.filled = filled;
    }

    /**
     *  Calculate the top-right point of the triangle based on the current positions
     */
    public void findTopRight() {
        topRight = new Point(bottomRight.x, topLeft.y);
    }

    /**
     *  Calculate the bottom-left point of the triangle based on the current positions
     */
    public void findBottomLeft() {
        bottomLeft = new Point(topLeft.x, bottomRight.y);
    }

    /**
     *  Calculate the top point of the triangle
     */
    public void findTriangleTopPoint() {
        triangleTopPoint = new Point(
                ((float)(topRight.x - topLeft.x)/2) + topLeft.x, topLeft.y);
    }

    /**
     * @return return the top point of the triangle
     */
    public Point gettriangleTopPoint() {
        return triangleTopPoint;
    }

    /**
     * @return return the bottom-left point of the triangle
     */
    public Point getBottomLeft() {
        return bottomLeft;
    }

    /**
     * @return return the bottom-right point of the triangle
     */
    public Point getBottomRight() {
        return bottomRight;
    }

    /**
     * @return return the top-left point of the triangle
     */
    public Point getTopLeft() {
        return topLeft;
    }

    /**
     * Sets the color of the triangle to the given color argument.
     *
     * @param color the new color of the triangle
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
     * Inverts the color of the triangle on the paint application. This
     * represents that the shape is currently being selected.
     *
     */
    public void invertColor(){
        this.color = invertedColor;
    }

    /**
     * Reverts the color of the triangle back to the original color.
     *
     */
    public void revertColor(){
        if (this.realColor != null){
            this.color = realColor;
        }
    }

    /**
     * Creates a copy of Triangle with the same attributes as the instance that this method is called on.
     *
     * @return A cloned version of the Shape that is not placed on the panel, nor is it added to the model.
     */
    @Override
    public Triangle clone() {
        Triangle t = new Triangle(new Point(this.getTopLeft().x + 999999, this.getTopLeft().y + 999999),
                this.lineThickness);
        t.setBottomRight(new Point(this.bottomRight.x + 999999, this.bottomRight.y + 999999));
        t.setFilled(this.isFilled());
        t.setColor(this.realColor);
        return t;
    }

    /**
     * Moves the shape instance to the old position that it was on prior to a paste action.
     *
     * @param x The old x position that the shape was on prior to the paste.
     * @param y The old y position that the shape was on prior to the paste.
     */
    @Override
    void undoPaste(double x, double y) {
        this.moveNewLocation(x, y);
    }

    /**
     * Moves the shape to a new location, called often with the paste methods.
     *
     * @param x The new x position that the shape instance is being moved to.
     * @param y The new y position that the shape instance is being moved to.
     */
    @Override
    void moveNewLocation(double x, double y) {
        double changeX = x - this.topLeft.x;
        double changeY = y - this.topLeft.y;
        this.topLeft.x += changeX;
        this.topLeft.y += changeY;
        this.bottomLeft.x += changeX;
        this.bottomLeft.y += changeY;
        this.bottomRight.x += changeX;
        this.bottomRight.y += changeY;
        findTopRight();
        findTriangleTopPoint();
    }


    /**
     * Returns the color of the triangle
     *
     * @return color
     */
    public javafx.scene.paint.Color getColor() {return this.color;}

    /**
     * Returns whether a point given is within the triangle objects
     * area on the Paint application.
     *
     * @param P point that is being checked
     * @return whether the triangle contains the point (true or false)
     */
    public boolean contains (Point P) {
        double areaOfTriange = Math.abs((this.triangleTopPoint.x - this.bottomLeft.x) * (this.bottomRight.y - this.bottomLeft.y)
                - (this.bottomRight.x - this.bottomLeft.x) * (this.triangleTopPoint.y - this.bottomLeft.y)) / 2;
        double a1 = Math.abs(P.x * (this.bottomLeft.y - this.bottomRight.y) + this.bottomRight.x * (this.bottomLeft.y - P.y) + this.bottomLeft.x * (P.y - this.bottomRight.y)) / 2;
        double a2 = Math.abs(this.triangleTopPoint.x * (P.y - this.bottomRight.y) + P.x * (this.bottomRight.y - this.triangleTopPoint.y) + this.bottomRight.x * (this.triangleTopPoint.y - P.y)) / 2;
        double a3 = Math.abs(this.triangleTopPoint.x * (this.bottomLeft.y - P.y) + this.bottomLeft.x * (P.y - this.triangleTopPoint.y) + P.x * (this.triangleTopPoint.y - this.bottomLeft.y)) / 2;
        double sum = a1 + a2 + a3;
        return sum - areaOfTriange <= 1e-5;      // 1e-5 is used rather than 0 in this case for the sake of inaccuracy present when dealing with double values.
    }

    /**
     * Moves the triangle on the Paint application 5 units
     * to the left.
     */
    @Override
    void moveLeft() {
        Point p1 = this.getBottomLeft();
        Point p2 = this.gettriangleTopPoint();
        Point p3 = this.getBottomRight();

        p1.x -= 5;
        p2.x -= 5;
        p3.x -= 5;

    }

    /**
     * Moves the triangle on the Paint application 5 units
     * to the right.
     */
    @Override
    void moveRight() {
        Point p1 = this.getBottomLeft();
        Point p2 = this.gettriangleTopPoint();
        Point p3 = this.getBottomRight();

        p1.x += 5;
        p2.x += 5;
        p3.x += 5;
    }

    /**
     * Moves the triangle on the Paint application 5 units
     * up.
     */
    @Override
    void moveUp() {
        Point p1 = this.getBottomLeft();
        Point p2 = this.gettriangleTopPoint();
        Point p3 = this.getBottomRight();

        p1.y -= 5;
        p2.y -= 5;
        p3.y -= 5;
    }

    /**
     * Moves the triangle on the Paint application 5 units
     * down.
     */
    @Override
    void moveDown() {
        Point p1 = this.getBottomLeft();
        Point p2 = this.gettriangleTopPoint();
        Point p3 = this.getBottomRight();

        p1.y += 5;
        p2.y += 5;
        p3.y += 5;
    }

    /**
     * Prints the triangle to the paint application on the correct
     * layer of the canvas.
     *
     * @param args list containing GraphicsContext, PaintModel, and triangle
     */
    public void print(ArrayList<Object> args) {
        GraphicsContext graphicsContext = (GraphicsContext) args.get(0);
        Triangle t = (Triangle) args.get(1);
        graphicsContext.setFill(t.getColor());
        graphicsContext.setStroke(t.getColor());
        Point p1 = t.getBottomLeft();
        Point p2 = t.gettriangleTopPoint();
        Point p3 = t.getBottomRight();

        // Fill the triangle using the vertices
        double[] xPoints = {p1.x, p2.x, p3.x};
        double[] yPoints = {p1.y, p2.y, p3.y};
        if (t.isFilled()){graphicsContext.fillPolygon(xPoints, yPoints, 3);}
        else{
            graphicsContext.setLineWidth(lineThickness);
            graphicsContext.strokePolygon(xPoints, yPoints, 3);}
    }

}
