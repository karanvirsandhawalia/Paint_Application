package ca.utoronto.utm.assignment2.paint;

/**
 * This class represents an action of drawing a shape from the canvas,
 * giving it its own undo and redo implementation
 */
public class Draw extends Moves {
    Shape shape;

    /**
     * Pass the shape that is drawn
     * @param shape the Shape drawn
     */
    public Draw(Shape shape) {
        this.shape = shape;
    }

    /**
     * Reverts the drawing of the shape by setting the visibility
     * of the shape to the opposite
     */
    @Override
    public void undo() {
        if (shape != null) shape.setOpp();
    }

    /**
     * Reapplies the drawing of the shape by setting the visibility
     * of the shape to the opposite
     */
    @Override
    public void redo() {
        if (shape != null) shape.setOpp();
    }
}
