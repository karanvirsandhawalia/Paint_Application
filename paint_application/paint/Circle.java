package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.ArrayList;

/**
 * Represents a circle shape in the Paint application.
 *
 * This class allows a Circle object to contain a centre,
 * radius, color, filled, and line thickness which is stored
 * in the PaintModel alongside other shapes to represent the
 * drawings on the current layer of the canvas.
 *
 * @author arnold
 *
 */
public class Circle extends Shape{
        private Point centre;
        private double radius;
        private javafx.scene.paint.Color color;
        private boolean filled;
        private Integer lineThickness;

        /**
         * Constructs a new Circle object stored in the PaintModel based
         * on user input on the Paint Application.
         *
         * @param centre the centre of the Circle object drawn by the user
         * @param radius the radius of the Circle object drawn by the user
         * @param lineThickness the line thickness of the Circle object drawn by the user
         */
        public Circle(Point centre, double radius, Integer lineThickness){
                this.centre = centre;
                this.radius = radius;
                this.lineThickness = lineThickness + 1;
        }

        /**
         * Returns whether the circle drawn is filled.
         *
         * @return true or false
         */
        public boolean isFilled() {return filled;}

        /**
         * Sets whether the current circle is filled in or not
         * on the current canvas.
         *
         * @param filled the status of the circles fill on the canvas
         */
        public void setFilled(boolean filled) {
                this.filled = filled;
        }

        /**
         * Returns the center of the circle.
         *
         * @return Point representing the center of the circle
         */
        public Point getCentre() {
                return centre;
        }

        /**
         * Sets the Centre of the circle object.
         *
         * @param centre new centre of the circle object
         */
        public void setCentre(Point centre) {
                this.centre = centre;
        }

        /**
         * Returns the radius of the circle object.
         *
         * @return radious of circle
         */
        public double getRadius() {
                return radius;
        }

        /**
         * Sets the radius of the circle object.
         *
         * @param radius new radius of the circle object
         */
        public void setRadius(double radius) {
                this.radius = radius;
        }

        /**
         * Sets the color of the circle object.
         *
         * @param color new color of the circle object
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
         * Creates a copy of the Circle with the same attributes as the instance that this method is called on.
         *
         * @return A cloned version of the Circle that is not placed on the panel, nor is it added to the model.
         */
        @Override
        public Circle clone() {
                Circle a = new Circle(new Point(999999999, 999999999), this.getRadius(), this.lineThickness);
                a.setFilled(this.isFilled());
                a.setColor(this.realColor);
                return a;
        }

        /**
         * Moves the Circle instance to the old position that it was on prior to a paste action.
         *
         * @param x The old x position that the Circle was on prior to the paste.
         * @param y The old y position that the Circle was on prior to the paste.
         */
        @Override
        void undoPaste(double x, double y) {
                this.setCentre(new Point(x, y));
        }

        /**
         * Moves the Circle to a new location, called often with the paste methods.
         *
         * @param x The new x position that the Circle instance is being moved to.
         * @param y The new y position that the Circle instance is being moved to.
         */
        @Override
        void moveNewLocation(double x, double y) {
                this.setCentre(new Point(x, y));
        }
        /**
         * Inverts the color of the circle object displayed
         * on the Paint application.
         *
         */
        public void invertColor(){
                this.color = invertedColor;
        }

        /**
         * Resets the color of the circle object displayed
         * on the Paint application to its original color.
         *
         */
        public void revertColor(){
                if (this.realColor != null){
                        this.color = realColor;
                }
        }

        /**
         * Returns the color of the circle object.
         *
         * @return color of circle
         */
        public javafx.scene.paint.Color getColor() {return this.color;}

        /**
         * Returns whether a point given is within the circle objects
         * area on the Paint application.
         *
         * @param P point that is being checked
         * @return whether the circle contains the point (true or false)
         */
        @Override
        public boolean contains (Point P) {
                double px = P.x - this.centre.x;
                double py = P.y - this.centre.y;
                return (Math.pow(px, 2) + Math.pow(py, 2)) <= Math.pow(this.radius, 2);
        }

        /**
         * Moves the circle on the Paint application 5 units
         * to the left.
         */
        @Override
        void moveLeft() {
                this.centre.x -= 5;
        }

        /**
         * Moves the circle on the Paint application 5 units
         * to the right.
         */
        @Override
        void moveRight() {
                this.centre.x += 5;
        }

        /**
         * Moves the circle on the Paint application 5 units
         * up.
         */
        @Override
        void moveUp() {
                this.centre.y -= 5;
        }

        /**
         * Moves the circle on the Paint application 5 units
         * down.
         */
        @Override
        void moveDown() {
                this.centre.y += 5;
        }

        /**
         * Prints the circle onto the current layer of the
         * canvas displayed on the Paint Application.
         *
         * @param args array containing the GraphicsContext, circle, and PaintModel
         */
        @Override
        public void print(ArrayList<Object> args) {
                GraphicsContext graphicsContext = (GraphicsContext) args.get(0);
                Circle c = (Circle) args.get(1);
                graphicsContext.setFill(c.getColor());
                graphicsContext.setStroke(c.getColor());
                double x = c.getCentre().x - c.getRadius();
                double y = c.getCentre().y - c.getRadius();
                double radius = c.getRadius();
                double diameter = radius * 2;
                if (c.isFilled()){graphicsContext.fillOval(x, y, radius*2, radius*2);}
                else{
                        graphicsContext.setLineWidth(lineThickness);
                        graphicsContext.strokeOval(x, y, diameter, diameter);}
        }
}
