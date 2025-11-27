package ca.utoronto.utm.assignment2.paint;

import javafx.event.EventType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * Represents a strategy for handling mouse interactions for squiggle.
 *
 * This class includes methods to handle mouse press, drag, release events,
 * as well as to decide the input action for squiggle drawing mode.
 *
 * @author arnold
 *
 */
public class SquiggleStrategy implements ShapeStrategy {
    private Squiggle shape = null;
    private Integer lineThickness = 0;

    /**
     * Handles the press mouse event for squiggle. Selects the initial dot of the
     * squiggle as the location of the mouse press on the paint application.
     *
     * @param event the MouseEvent representing the mouse press
     * @param model the PaintModel containing the current layers drawing objects
     * @param panel the PaintPanel where shapes are drawn
     */
    @Override
    public void mousePressed(MouseEvent event, PaintModel model, PaintPanel panel){
        System.out.println("Started Squiggle");
        this.lineThickness = panel.getLineThickness();
        Point point = new Point(event.getX(), event.getY());
        this.shape=new Squiggle(point, panel.getLineThickness(), panel.getColor());
    }

    /**
     * Handles the drag mouse event for squiggle. Continues to draw the squiggle
     * based on the location of the mouse on the paint application and displays
     * it drawing.
     *
     * @param event the MouseEvent representing the mouse press
     * @param model the PaintModel containing the current layers drawing objects
     * @param panel the PaintPanel where shapes are drawn
     */
    @Override
    public void mouseDragged(MouseEvent event, PaintModel model, PaintPanel panel) {
        shape.addPoint(new Point(event.getX(), event.getY()));

        GraphicsContext g2d = panel.getGraphicsContext2D();
        g2d.setLineWidth(lineThickness);

        g2d.setStroke(panel.getColor());
        ArrayList<Point> points = shape.getPoint();
        
        for(int k=0;k<points.size()-1; k++) {
            Point p1 = points.get(k);
            Point p2 = points.get(k + 1);
            g2d.strokeLine(p1.x, p1.y, p2.x, p2.y);
        }
    }

    /**
     * Handles the release mouse event for squiggle. Once the mouse is released,
     * the squiggle object is completely created and is added to the associated
     * PaintModel.
     *
     * @param event the MouseEvent representing the mouse press
     * @param model the PaintModel containing the current layers drawing objects
     * @param panel the PaintPanel where shapes are drawn
     */
    @Override
    public void mouseReleased(MouseEvent event, PaintModel model, PaintPanel panel) {
        panel.getActionArray().insert(new Draw(this.shape));
        shape.addPoint(new Point(event.getX(), event.getY()));
        System.out.println("Added Squiggle");
        model.addSquiggle(shape);

    }

    /**
     * Decides which mouse event occurs by the user during squiggle mode and calls
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

