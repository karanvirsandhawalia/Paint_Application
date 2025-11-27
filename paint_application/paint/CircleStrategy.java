package ca.utoronto.utm.assignment2.paint;

import javafx.event.EventType;
import javafx.scene.input.MouseEvent;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Objects;

/**
 * Represents a strategy for handling mouse interactions for circle.
 *
 * This class includes methods to handle mouse press, drag, release events,
 * as well as to decide the input action for circle drawing mode.
 *
 * @author arnold
 *
 */
public class CircleStrategy implements ShapeStrategy {
    private Circle shape = null;

    /**
     * Handles the press mouse event for circle. Selects the circles
     * centre as the location of the mouse press on the paint application.
     *
     * @param event the MouseEvent representing the mouse press
     * @param model the PaintModel containing the current layers drawing objects
     * @param panel the PaintPanel where shapes are drawn
     */
    @Override
    public void mousePressed(MouseEvent event, PaintModel model, PaintPanel panel){
        System.out.println("Started Circle");
        Point centre = new Point(event.getX(), event.getY());
        this.shape=new Circle(centre, 0, panel.getLineThickness());
        shape.setColor(panel.getColor());
    }

    /**
     * Handles the drag mouse event for circle. Continues to expand the
     * circles radius based on the location of the mouse on the paint application
     * and displays it expanding.
     *
     * @param event the MouseEvent representing the mouse press
     * @param model the PaintModel containing the current layers drawing objects
     * @param panel the PaintPanel where shapes are drawn
     */
    @Override
    public void mouseDragged(MouseEvent event, PaintModel model, PaintPanel panel) {
        Point release = new Point(event.getX(), event.getY());
        double radius = Math.sqrt(Math.pow((shape.getCentre().x - release.x), 2) +
                Math.pow((shape.getCentre().y - release.y), 2) );
        this.shape.setRadius(radius);
        GraphicsContext g2d = panel.getGraphicsContext2D();
        g2d.clearRect(0, 0, panel.getWidth(), panel.getHeight());
        panel.update(model, null);
        g2d.setFill(shape.getColor());
        double x = this.shape.getCentre().x - this.shape.getRadius();
        double y = this.shape.getCentre().y - this.shape.getRadius();
        radius = this.shape.getRadius();

        g2d.setStroke(shape.getColor());
        if (model.getFilled()){g2d.fillOval(x, y, radius*2, radius*2);}
        else{
            g2d.setLineWidth(panel.getLineThickness() + 1);
            g2d.strokeOval(x, y, radius*2, radius*2);}

    }

    /**
     * Handles the release mouse event for circle. Once the mouse is released,
     * the circle shape is completely created and is added to the associated
     * PaintModel.
     *
     * @param event the MouseEvent representing the mouse press
     * @param model the PaintModel containing the current layers drawing objects
     * @param panel the PaintPanel where shapes are drawn
     */
    @Override
    public void mouseReleased(MouseEvent event, PaintModel model, PaintPanel panel) {
        if(this.shape!=null){
            panel.getActionArray().insert(new Draw(this.shape));
            model.addCircle(this.shape);
            System.out.println("Added Circle");
            this.shape=null;

        }
    }

    /**
     * Decides which mouse event occurs by the user during circle mode and calls
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
