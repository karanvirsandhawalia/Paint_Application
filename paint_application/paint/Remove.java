package ca.utoronto.utm.assignment2.paint;

/**
 * This class represents an action of removing a shape from the canvas,
 * giving it its own undo and redo implementation
 */
public class Remove extends Moves {
    Shape shape;

    /**
     * Pass the shape that is removed
     * @param shape the Shape being removed
     */
    public Remove(Shape shape) {
        this.shape = shape;
    }

    /**
     * Reverts the removal of the shape by setting the visibility
     * of the shape to the opposite
     */
    @Override
    public void undo() {
        if (shape != null) shape.setOpp();
    }

    /**
     * Reapplies the removal of the shape by setting the visibility
     * of the shape to the opposite
     */
    @Override
    public void redo() {
        if (shape != null) shape.setOpp();
    }
}
