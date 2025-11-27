package ca.utoronto.utm.assignment2.paint;

/**
 * This is an abstract base class designed to handle actions
 * within the paint application
 */
abstract class Moves {
    Shape shape = null;
    Moves next = null;
    Moves before = null;

    /**
     * Set the next move
     * @param next the next move
     */
    public void setNext(Moves next){this.next = next;};

    /**
     * @return the next move
     */
    public Moves getNext(){return next;}

    /**
     * Set the previous move
     * @param before the previous move
     */
    public void setbefore(Moves before){this.before = before;};

    /**
     * @return the previous move
     */
    public Moves getBefore(){return before;}

    /**
     * Undo the last action
     * This method should be implemented in subclasses to define how to undo the specific action.
     */
    abstract void undo();
    /**
     * Redo the last undone action
     * This method should be implemented in subclasses to define how to redo the specific action.
     */
    abstract void redo();
}
