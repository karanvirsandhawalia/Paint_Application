package ca.utoronto.utm.assignment2.paint;

/**
 * Class representing a point on the paint application. A point
 * contains two fields to represent the x and y coordinate
 *
 * This class can display and hold points located on the
 * paint application.
 *
 * @author arnold
 *
 */
public class Point {
        double x, y;

        /**
         * Constructs a new Point object using the given x and y
         *
         * @param x the width coordinate of the point
         * @param y the height coordinate of the point
         */
        Point(double x, double y){
                this.x=x; this.y=y;
        }

        /**
         * Sets the x value of the point
         *
         * @param a the new width coordinate of the point
         */
        public void setX(double a){this.x = a;}

        /**
         * Sets the y value of the point
         *
         * @param a the new height coordinate of the point
         */
        public void setY(double a){this.y = a;}
}
