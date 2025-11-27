package ca.utoronto.utm.assignment2.paint;

import javafx.scene.input.MouseEvent;
import javafx.scene.canvas.GraphicsContext;

/**
 * Represents a strategy for handling mouse interactions for different drawing modes.
 *
 * Classes implementing this interface handle all mouse events for specific drawing
 * modes in the paint application.
 *
 * This interface includes methods to handle mouse press, drag, release events,
 * as well as to decide the input action.
 *
 * @author arnold
 *
 */
public interface ShapeStrategy {

    /**
     * Handles the press mouse event for drawing modes.
     *
     * @param event the MouseEvent representing the mouse press
     * @param model the PaintModel containing the current layers drawing objects
     * @param panel the PaintPanel where shapes are drawn
     */
    void mousePressed(MouseEvent event, PaintModel model, PaintPanel panel);

    /**
     * Handles the drag mouse event for drawing modes.
     *
     * @param event the MouseEvent representing the mouse press
     * @param model the PaintModel containing the current layers drawing objects
     * @param panel the PaintPanel where shapes are drawn
     */
    void mouseDragged(MouseEvent event, PaintModel model, PaintPanel panel);

    /**
     * Handles the release mouse event for drawing modes.
     *
     * @param event the MouseEvent representing the mouse press
     * @param model the PaintModel containing the current layers drawing objects
     * @param panel the PaintPanel where shapes are drawn
     */
    void mouseReleased(MouseEvent event, PaintModel model, PaintPanel panel);

    /**
     * Decides which mouse event occurs by the user and calls the
     * corresponding method to handle it.
     *
     * @param event the MouseEvent representing the mouse press
     * @param model the PaintModel containing the current layers drawing objects
     * @param panel the PaintPanel where shapes are drawn
     */
    void decideInput(MouseEvent event, PaintModel model, PaintPanel panel);
}
