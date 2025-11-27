package ca.utoronto.utm.assignment2.paint;

/**
 * Represents an array of Actions done on the canvas in PaintPanel, storing them as
 * a doubly linked list.
 *
 * This class allows the user to undo and redo moves by moving the
 * position of the current pointer.
 *
 * @author arnold
 *
 */
public class ActionArray {
    Moves head; // head of list
    Moves tail;
    Moves current;
    PaintPanel panel;
    PaintModel model;
    Moves extra;
    Boolean check=false;

    /**
     * Constructs a new ActionArray object using the given PaintPanel and PaintModel.
     *
     * @param panel the panel containing the current canvas and PaintPanel
     * @param model the model containing all objects drawn on the current layer of the canvas
     */
    public ActionArray(PaintPanel panel, PaintModel model) {
        head = null;
        tail = null;
        current = null;
        this.panel = panel;
        this.model = model;

    }

    /**
     * Clears the current ActionArray to remove logs of all actions
     * taken by the user.
     */
    public void clear(){
        head = null;
        tail = null;
        current = null;
    }

    /**
     * Inserts a move representing an action taken by the user into
     * the ActionArray object.
     *
     * @param moves the move object representing an action
     */
    public void insert(Moves moves) {
        if (head == null) {
            head = moves;
            head.setbefore(null);
            tail = moves;
            current = head;
        } else {
            if (current == null) {
                current = extra;}
            current.setNext(moves);
            moves.setbefore(current);
            current = moves;
            tail = current;
        }
    }

    /**
     * Performs an undo command on the latest action in the ActionArray object and moves the pointer
     * to the next action that should be undone.
     */
    public void undo() {
        if (current != null) {
            if (!(current instanceof Cut)){
                current.undo();}
            extra = current;
            current = current.getBefore();
            if (current instanceof Cut ){
                current = current.getBefore();
            }
            if (current == null) {
                head = null;
            }
            check = false;

            panel.update(model, null);
        }

    }

    /**
     * Performs a redo command on the last action undone in the ActionArray object and moves the pointer
     * to the next action that should be redone.
     */
    public void redo() {
        if (current != null && current.getNext() != null) {
            current = current.getNext();
            if (current instanceof Cut && current.getNext() != null){
                current = current.getNext();
            }
            current.redo();
            panel.update(model, null);
        }
        else if (current == null) {
            current = extra;
            current.redo();
            panel.update(model, null);
        }


    }
}