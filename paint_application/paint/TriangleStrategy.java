package ca.utoronto.utm.assignment2.paint;

import javafx.event.EventType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 * Represents a strategy for handling mouse interactions for triangle.
 *
 * This class includes methods to handle mouse press, drag, release events,
 * as well as to decide the input action for triangle drawing mode.
 *
 * @author arnold
 *
 */
public class TriangleStrategy implements ShapeStrategy {
    private Triangle shape = null;

    /**
     * Handles the press mouse event for triangle. Selects the top left
     * corner of a rectangle containing the largest possible isosceles triangle
     * as the location of the mouse press on the paint application.
     *
     * @param event the MouseEvent representing the mouse press
     * @param model the PaintModel containing the current layers drawing objects
     * @param panel the PaintPanel where shapes are drawn
     */
    @Override
    public void mousePressed(MouseEvent event, PaintModel model, PaintPanel panel){
        System.out.println("Started Triangle");
        Point topLeft = new Point(event.getX(), event.getY());
        this.shape = new Triangle(topLeft, panel.getLineThickness());
        shape.setColor(panel.getColor());
    }

    /**
     * Handles the drag mouse event for triangle. Continues to expand the
     * box containing the triangle based on the location of the mouse on the paint
     * application and displays the triangle expanding.
     *
     * @param event the MouseEvent representing the mouse press
     * @param model the PaintModel containing the current layers drawing objects
     * @param panel the PaintPanel where shapes are drawn
     */
    @Override
    public void mouseDragged(MouseEvent event, PaintModel model, PaintPanel panel) {
        Point bottomRight = new Point(event.getX(), event.getY());
        Point topL = this.shape.getTopLeft();

        if (topL.y > bottomRight.y){
            topL = bottomRight;
            bottomRight = this.shape.getTopLeft();
        }

        GraphicsContext g2d = panel.getGraphicsContext2D();
        g2d.clearRect(0, 0, panel.getWidth(), panel.getHeight());
        panel.update(model, null);
        g2d.setFill(shape.getColor());

        Point topRight = new Point(bottomRight.x , topL.y);
        Point p2 = new Point(((float) (topRight.x - topL.x)/2) + topL.x, topL.y);
        Point p1 = new Point(topL.x, bottomRight.y);

        double[] xPoints = {p1.x, p2.x, bottomRight.x};
        double[] yPoints = {p1.y, p2.y, bottomRight.y};
        g2d.setStroke(shape.getColor());
        if (model.getFilled()){g2d.fillPolygon(xPoints, yPoints, 3);}
        else{
            g2d.setLineWidth(panel.getLineThickness() + 1);
            g2d.strokePolygon(xPoints, yPoints, 3);}


    }

    /**
     * Handles the release mouse event for triangle. Once the mouse is released,
     * the triangle shape is completely created and is added to the associated
     * PaintModel.
     *
     * @param event the MouseEvent representing the mouse press
     * @param model the PaintModel containing the current layers drawing objects
     * @param panel the PaintPanel where shapes are drawn
     */
    @Override
    public void mouseReleased(MouseEvent event, PaintModel model, PaintPanel panel) {
        panel.getActionArray().insert(new Draw(this.shape));
        if (this.shape != null) {
            Point bottomRight = new Point(event.getX(), event.getY());
            this.shape.setBottomRight(bottomRight);
            model.addTriangle(this.shape);
            System.out.println("Added Triangle");
            this.shape = null;
        }
    }

    /**
     * Decides which mouse event occurs by the user during triangle mode and calls
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

