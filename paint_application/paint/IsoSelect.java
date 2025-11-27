package ca.utoronto.utm.assignment2.paint;

/**
 * The IsoSelect class allows for selecting and controlling the movement
 * of a Shape object. It allows for moving the shape left, right, up, and down.
 * @author arnold
 */
public class IsoSelect {
    private Shape shape;

    /**
     * Constructs an IsoSelect object with the specified Shape.
     * @param shape the Shape object to be controlled by this IsoSelect
     */
    public IsoSelect(Shape shape) {
        this.shape = shape;
    }
    /**
     * Returns the Shape associated with this IsoSelect.
     *
     * @return the Shape controlled by this IsoSelect
     */
    public Shape getShape() {
        return shape;
    }
    /**
     * Moves the Shape to the left.
     */
    public void moveleft() {shape.moveLeft();}
    /**
     * Moves the Shape to the right.
     */
    public void moveright() {shape.moveRight();}
    /**
     * Moves the Shape upward.
     */
    public void moveup() {shape.moveUp();}
    /**
     * Moves the Shape downward.
     */
    public void movedown() {shape.moveDown();}
}
