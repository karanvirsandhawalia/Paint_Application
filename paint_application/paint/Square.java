package ca.utoronto.utm.assignment2.paint;


import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

/**
 * This class represents the square existing within
 * the paint application.
 *
 * @author arnold
 *
 */

public class Square extends Rectangle{
    private Integer lineThickness;

    /**
     * A Square shape is created at the following corners
     * with the given line thickness
     *
     * @param tl corner1
     * @param bt corner2
     * @param thickness the line thickness of the square
     */
    public Square(Point tl, Point bt, Integer thickness){
        super(tl, bt, thickness);
        lineThickness = thickness;
    }

    /**
     * Prints the square to the paint application on the correct
     * layer of the canvas.
     *
     * @param args list containing GraphicsContext, PaintModel, and square
     */
    public void print(ArrayList<Object> args) {
        GraphicsContext graphicsContext = (GraphicsContext) args.get(0);
        Square s = (Square) args.get(1);

        graphicsContext.setFill(s.getColor());
        graphicsContext.setStroke(s.getColor());
        Point c1 = s.getCorner1();
        Point c2 = s.getCorner2();

        double length = Math.min(Math.abs(c2.x - c1.x), Math.abs(c2.y - c1.y));
        double x = c1.x;
        double y = c1.y;
        if (c2.x < c1.x) {x = c1.x - length;}
        if (c2.y < c1.y) {y = c1.y - length;}
        if (s.isFilled()) {graphicsContext.fillRect(x, y, length, length);}
        else {
            graphicsContext.setLineWidth(lineThickness + 1);
            graphicsContext.strokeRect(x, y, length, length);}
    }
}
