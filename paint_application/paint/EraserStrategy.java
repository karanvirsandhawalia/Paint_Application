package ca.utoronto.utm.assignment2.paint;

import javafx.event.EventType;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

/**
 * Represents a strategy for handling mouse interactions for eraser.
 *
 * This class includes methods to handle mouse press, drag, release events,
 * as well as to decide the input action for eraser drawing mode.
 *
 * @author arnold
 *
 */
public class EraserStrategy implements ShapeStrategy{

    /**
     * Handles the press mouse event for eraser. The method removes
     * any shapes located at location of the mouse on the paint application.
     *
     * @param event the MouseEvent representing the mouse press
     * @param model the PaintModel containing the current layers drawing objects
     * @param panel the PaintPanel where shapes are drawn
     */
    @Override
    public void mousePressed(MouseEvent event, PaintModel model, PaintPanel panel) {
        System.out.println("Started Eraser");
        Point p = new Point(event.getX(), event.getY());
        ArrayList<Object> shapes = model.getShapes();

        for (Object shape : shapes) {
            Shape s = (Shape) shape;

            try{
                Polyline polyline = (Polyline) s;
                if (polyline.contains(p) && polyline.isVisible() && polyline.completed) {
                    panel.getActionArray().insert(new Remove(s));
                    s.setVisibleFalse();
                }
            }catch (ClassCastException e){
                if (s.contains(p) && s.isVisible()){
                    panel.getActionArray().insert(new Remove(s));
                    s.setVisibleFalse();
            }

            }
        }

        panel.update(model, null);
    }

    /**
     * Handles the drag mouse event for eraser. The method removes
     * any shapes located at location of the mouse on the paint application
     * as the mouse is dragged across the screen.
     *
     * @param event the MouseEvent representing the mouse press
     * @param model the PaintModel containing the current layers drawing objects
     * @param panel the PaintPanel where shapes are drawn
     */
    @Override
    public void mouseDragged(MouseEvent event, PaintModel model, PaintPanel panel) {
        Point p = new Point(event.getX(), event.getY());
        ArrayList<Object> shapes = model.getShapes();

        for (Object shape : shapes) {
            Shape s = (Shape) shape;
            try{
                Polyline polyline = (Polyline) s;
                if (polyline.contains(p) && polyline.isVisible() && polyline.completed) {
                    panel.getActionArray().insert(new Remove(s));
                    s.setVisibleFalse();
                }
            }catch (ClassCastException e){
                if (s.contains(p) && s.isVisible()){
                    panel.getActionArray().insert(new Remove(s));
                    s.setVisibleFalse();
                }

            }
        }
        panel.update(model, null);
    }

    /**
     * Handles the release mouse event for eraser. Eraser mode does not
     * do anything when the mouse is released.
     *
     * @param event the MouseEvent representing the mouse press
     * @param model the PaintModel containing the current layers drawing objects
     * @param panel the PaintPanel where shapes are drawn
     */
    @Override
    public void mouseReleased(MouseEvent event, PaintModel model, PaintPanel panel) {
        System.out.println("Finished Eraser");
    }

    /**
     * Decides which mouse event occurs by the user during eraser mode and calls
     * the corresponding method to handle it.
     *
     * @param event the MouseEvent representing the mouse press
     * @param model the PaintModel containing the current layers drawing objects
     * @param panel the PaintPanel where shapes are drawn
     */
    @Override
    public void decideInput(MouseEvent event, PaintModel model, PaintPanel panel) {
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
