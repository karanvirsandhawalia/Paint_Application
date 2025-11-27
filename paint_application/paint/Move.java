package ca.utoronto.utm.assignment2.paint;

/**
 * This class represents an action of moving a shape from the canvas,
 * giving it its own undo and redo implementation
 */
public class Move extends Moves{
    Shape shape;
    Integer offsetX;
    Integer offsetY;

    /**
     * Pass the shape that is moved
     * @param offsetx this represents the horizontal displacement of the shape
     * @param offsety this represents the vertical displacement of the shape
     */
    public Move(Shape shape, Integer offsetx, Integer offsety ) {
        this.shape = shape;
        this.offsetX = offsetx;
        this.offsetY = offsety;
    }

    /**
     * Reverts the shape's position by moving it in the opposite direction
     * of the original movement
     */
    @Override
    public void undo() {
        if (shape != null){
            if (offsetX > 0){
                shape.moveLeft();
            }
            else if (offsetX < 0){
                shape.moveRight();
            }
            else if (offsetY < 0){
                shape.moveDown();
            }
            else if (offsetY > 0){
                shape.moveUp();
            }
        }
    }

    /**
     * Reapplies the shape's position by moving it in the same direction
     * as the original movement
     */
    @Override
    public void redo() {
        if (shape != null){
            if (offsetX < 0){
                shape.moveLeft();
            }
            else if (offsetX > 0){
                shape.moveRight();
            }
            else if (offsetY > 0){
                shape.moveDown();
            }
            else if (offsetY < 0){
                shape.moveUp();
            }
        }
    }
}