package ca.utoronto.utm.assignment2.paint;

import javafx.event.EventType;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

/**
 * Represents a strategy for handling mouse interactions for Eye Dropper.
 *
 * This class includes methods to handle mouse press, drag, release events,
 * as well as to decide the input action for eye dropper drawing mode.
 *
 * @author arnold
 *
 */
public class EyeDropperStrategy implements ShapeStrategy{

    private boolean mousePressed = false;

    /**
     * Handles the press mouse event for eye dropper. Gets the color of
     * the pixel at the mouse location on the paint application when the
     * mouse is pressed.
     *
     * @param event the MouseEvent representing the mouse press
     * @param model the PaintModel containing the current layers drawing objects
     * @param panel the PaintPanel where shapes are drawn
     */
    @Override
    public void mousePressed(MouseEvent event, PaintModel model, PaintPanel panel){
        System.out.println("EyeDropper");
        mousePressed = !mousePressed;
        panel.setColor(panel.getColorAt(panel.getG2d(), (int) event.getX(), (int) event.getY()));
        panel.chooserPanel.resetButtons();
        panel.setMode(null);
    }

    /**
     * Handles the drag mouse event for eye dropper. Eye dropper does nothing
     * for this action.
     *
     * @param event the MouseEvent representing the mouse press
     * @param model the PaintModel containing the current layers drawing objects
     * @param panel the PaintPanel where shapes are drawn
     */
    @Override
    public void mouseDragged(MouseEvent event, PaintModel model, PaintPanel panel) {
    }

    /**
     * Handles the release mouse event for eye dropper. Eye dropper does nothing
     * for this action.
     *
     * @param event the MouseEvent representing the mouse press
     * @param model the PaintModel containing the current layers drawing objects
     * @param panel the PaintPanel where shapes are drawn
     */
    @Override
    public void mouseReleased(MouseEvent event, PaintModel model, PaintPanel panel) {
    }

    /**
     * Decides which mouse event occurs by the user during eye dropper mode and calls
     * the corresponding method to handle it.
     *
     * @param event the MouseEvent representing the mouse press
     * @param model the PaintModel containing the current layers drawing objects
     * @param panel the PaintPanel where shapes are drawn
     */
    @Override
    public void decideInput(MouseEvent event, PaintModel model, PaintPanel panel){
        EventType<MouseEvent> mouseEventType = (EventType<MouseEvent>) event.getEventType();
        if(mouseEventType.equals(MouseEvent.MOUSE_PRESSED)) {
            this.mousePressed(event, model, panel);

        } else if (mouseEventType.equals(MouseEvent.MOUSE_DRAGGED)) {
            this.mouseDragged(event, model,panel);

        } else if (mouseEventType.equals(MouseEvent.MOUSE_RELEASED)) {
            this.mouseReleased(event, model, panel);

        }

    }
}
