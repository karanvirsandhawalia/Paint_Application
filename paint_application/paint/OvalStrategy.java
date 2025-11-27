package ca.utoronto.utm.assignment2.paint;

import javafx.event.EventType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 * Represents a strategy for handling mouse interactions for oval.
 *
 * This class includes methods to handle mouse press, drag, release events,
 * as well as to decide the input action for oval drawing mode.
 *
 * @author arnold
 *
 */
public class OvalStrategy implements ShapeStrategy {

    private Oval shape = null;

    /**
     * Handles the press mouse event for oval. Selects the ovals
     * centre as the location of the mouse press on the paint application.
     *
     * @param event the MouseEvent representing the mouse press
     * @param model the PaintModel containing the current layers drawing objects
     * @param panel the PaintPanel where shapes are drawn
     */
    @Override
    public void mousePressed(MouseEvent event, PaintModel model, PaintPanel panel){
        System.out.println("Started Oval");
        Point p = new Point(event.getX(), event.getY());
        this.shape = new Oval(p, 0 ,0, panel.getLineThickness());
        shape.setColor(panel.getColor());
    }

    /**
     * Handles the drag mouse event for oval. Continues to expand the
     * ovals radius based on the location of the mouse on the paint application
     * and displays it expanding.
     *
     * @param event the MouseEvent representing the mouse press
     * @param model the PaintModel containing the current layers drawing objects
     * @param panel the PaintPanel where shapes are drawn
     */
    @Override
    public void mouseDragged(MouseEvent event, PaintModel model, PaintPanel panel) {
        Point current_pos = new Point(event.getX(), event.getY());
        double height = Math.abs(current_pos.y - this.shape.getPinCorner().y);
        double width = Math.abs(current_pos.x - this.shape.getPinCorner().x);

        this.shape.setWidth(width);
        this.shape.setHeight(height);

        GraphicsContext g2d = panel.getGraphicsContext2D();
        g2d.clearRect(0, 0, panel.getWidth(), panel.getHeight());
        panel.update(model, null);
        g2d.setFill(shape.getColor());

        double yDimension = Math.min(this.shape.getPinCorner().y, current_pos.y);
        double xDimension = Math.min(this.shape.getPinCorner().x, current_pos.x);

        g2d.setStroke(shape.getColor());

        g2d.setStroke(shape.getColor());
        if (model.getFilled()){g2d.fillOval(xDimension, yDimension, width, height);}
        else{
            g2d.setLineWidth(panel.getLineThickness() + 1);
            g2d.strokeOval(xDimension, yDimension, width, height);}


    }

    /**
     * Handles the release mouse event for oval. Once the mouse is released,
     * the oval shape is completely created and is added to the associated
     * PaintModel.
     *
     * @param event the MouseEvent representing the mouse press
     * @param model the PaintModel containing the current layers drawing objects
     * @param panel the PaintPanel where shapes are drawn
     */
    @Override
    public void mouseReleased(MouseEvent event, PaintModel model, PaintPanel panel) {
        if (this.shape != null) {
            panel.getActionArray().insert(new Draw(this.shape));
            Point current_pos = new Point(event.getX(), event.getY());
            double height = Math.abs(current_pos.y - this.shape.getPinCorner().y);
            double width = Math.abs(current_pos.x - this.shape.getPinCorner().x);

            this.shape.setWidth(width);
            this.shape.setHeight(height);

            double yDimension = Math.min(this.shape.getPinCorner().y, current_pos.y);
            double xDimension = Math.min(this.shape.getPinCorner().x, current_pos.x);
            this.shape.setPinCorner(new Point(xDimension, yDimension));
            model.addOval(this.shape);
            System.out.println("Added Oval");
            this.shape = null;
        }
    }

    /**
     * Decides which mouse event occurs by the user during oval mode and calls
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

