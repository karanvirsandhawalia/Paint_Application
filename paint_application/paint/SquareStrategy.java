package ca.utoronto.utm.assignment2.paint;

import javafx.event.EventType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 * Represents a strategy for handling mouse interactions for square.
 *
 * This class includes methods to handle mouse press, drag, release events,
 * as well as to decide the input action for square drawing mode.
 *
 * @author arnold
 *
 */
public class SquareStrategy implements ShapeStrategy {
    private Square shape = null;

    /**
     * Handles the press mouse event for square. Selects the squares
     * top left corner as the location of the mouse press on the paint application.
     *
     * @param event the MouseEvent representing the mouse press
     * @param model the PaintModel containing the current layers drawing objects
     * @param panel the PaintPanel where shapes are drawn
     */
    @Override
    public void mousePressed(MouseEvent event, PaintModel model, PaintPanel panel){
        System.out.println("Started Square");
        Point c1 = new Point(event.getX(), event.getY());
        this.shape=new Square(c1, null, panel.getLineThickness());
        shape.setColor(panel.getColor());
    }

    /**
     * Handles the drag mouse event for square. Continues to expand the square
     * based on the location of the mouse on the paint application and displays
     * it expanding.
     *
     * @param event the MouseEvent representing the mouse press
     * @param model the PaintModel containing the current layers drawing objects
     * @param panel the PaintPanel where shapes are drawn
     */
    @Override
    public void mouseDragged(MouseEvent event, PaintModel model, PaintPanel panel) {
        Point c2 = new Point(event.getX(), event.getY());
        GraphicsContext g2d = panel.getGraphicsContext2D();
        g2d.clearRect(0, 0, panel.getWidth(), panel.getHeight());
        panel.update(model, null);
        g2d.setFill(shape.getColor());
        Point c1 = this.shape.getCorner1();
        double length = Math.min(Math.abs(c2.x - c1.x), Math.abs(c2.y - c1.y));
        double x = c1.x;
        double y = c1.y;
        if (c2.x < c1.x) {
            x = c1.x - length;
        }
        if (c2.y < c1.y) {
            y = c1.y - length;
        }


        g2d.setStroke(shape.getColor());
        if (model.getFilled()){g2d.fillRect(x, y, length, length);}
        else{
            g2d.setLineWidth(panel.getLineThickness() + 1);
            g2d.strokeRect(x, y, length, length);}

    }

    /**
     * Handles the release mouse event for square. Once the mouse is released,
     * the square shape is completely created and is added to the associated
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
            Point c2 = new Point(event.getX(), event.getY());
            this.shape.setCorner2(c2);
            model.addSquare(this.shape);
            System.out.println("Added Square");
            this.shape=null;
        }
    }

    /**
     * Decides which mouse event occurs by the user during square mode and calls
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

