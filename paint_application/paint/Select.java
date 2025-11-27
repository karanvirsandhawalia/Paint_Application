package ca.utoronto.utm.assignment2.paint;
/**
 * The Select class allows you to select a Shape object and control
 * its movement. It allows for moving the selected shape left, right, up, and down.
 * @author arnold
 */
public class Select {
    private Shape shape;

    /**
     * Constructs a Select object with the specified Shape.
     *
     * @param shape the Shape object to be controlled by this Select
     */
    public Select(Shape shape) {
        this.shape = shape;
    }

    /**
     * Returns the Shape associated with this Select.
     *
     * @return the Shape controlled by this Select
     */
    public Shape getShape() {
        return shape;
    }

    /**
     * Moves the Shape to the left by a specified amount.
     */
    public void moveleft() {shape.moveLeft();}
    /**
     * Moves the Shape to the right by a specified amount.
     */
    public void moveright() {shape.moveRight();}

    /**
     * Moves the Shape upward by a specified amount.
     */
    public void moveup() {shape.moveUp();}

    /**
     * Moves the Shape downward by a specified amount.
     */
    public void movedown() {shape.moveDown();}
}
