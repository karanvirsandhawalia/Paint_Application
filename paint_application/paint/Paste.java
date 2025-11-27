package ca.utoronto.utm.assignment2.paint;

import java.util.ArrayList;

/**
 * Captures a paste action and keeps track of the history of the canvas for undoing that action and redoing an undone
 * action.
 *
 * @author arnold
 *
 */
public class Paste extends Moves{
    private double newX;
    private double newY;
    private Shape shape;
    private double oldX;
    private double oldY;

    /**
     * Constructs the new Paste object that stores the new shape and the coordinate to store it.
     *
     * @param mousex the x coordinate of the current mouse position.
     * @param mousey the y coordinate of the current mouse position.
     * @param copiedShape the shape that is to be pasted into a new location.
     */

    public Paste(double mousex, double mousey, Shape copiedShape) {
        this.newX = mousex;
        this.newY = mousey;
        this.shape = copiedShape;

        if (this.shape instanceof Polyline){
            Polyline p = (Polyline) this.shape;
            Point point = p.getVertices().getFirst();
            this.oldX = point.x;
            this.oldY = point.y;
        }
        else if (this.shape instanceof Squiggle){
            Squiggle s = (Squiggle) this.shape;
            Point point = s.getPoint().getFirst();
            this.oldY = point.y;
            this.oldX = point.x;
        }
        else if (this.shape instanceof Rectangle){
            Rectangle r = (Rectangle) this.shape;
            Point a =r.getCorner1();
            this.oldX = a.x;
            this.oldY = a.y;

        }
        else if (this.shape instanceof Circle){
            Circle c = (Circle) this.shape;
            this.oldX = c.getCentre().x;
            this.oldY = c.getCentre().y;
        }
        else if (this.shape instanceof Oval){
            Oval oval = (Oval) this.shape;
            this.oldY = oval.getPinCorner().y - oval.getHeight()/2;
            this.oldX = oval.getPinCorner().x - oval.getWidth()/2;

        }

        else if (this.shape instanceof Triangle){
            Triangle t = (Triangle) this.shape;
            this.oldX = t.getTopLeft().x;
            this.oldY = t.getTopLeft().y;

        }

    }

    /**
     * Undo the Paste change that has happened last.
     */
    @Override
    void undo() {
        this.shape.undoPaste(oldX, oldY);
    }

    /**
     * Redo the last undone change that was a Paste.
     */
    @Override
    void redo() {
        this.shape.moveNewLocation(newX, newY);
    }
}
