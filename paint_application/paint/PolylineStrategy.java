package ca.utoronto.utm.assignment2.paint;


import javafx.event.EventType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;

/**
 * Represents a strategy for handling mouse interactions for polyline.
 *
 * This class includes methods to handle mouse press, drag, release events,
 * as well as to decide the input action for polyline drawing mode.
 *
 * @author arnold
 *
 */
public class PolylineStrategy implements ShapeStrategy{
    private Polyline latestPolyline;

    /**
     * Handles the press mouse event for polyline. Selects the initial of the
     * polyline as the location of the mouse press on the paint application. The
     * polyline continues to grow until another point touches the initial.
     *
     * @param event the MouseEvent representing the mouse press
     * @param model the PaintModel containing the current layers drawing objects
     * @param panel the PaintPanel where shapes are drawn
     */
    @Override
    public void mousePressed(MouseEvent event, PaintModel model, PaintPanel panel) {
        this.latestPolyline = model.getLatestPolyline();
        if (this.latestPolyline == null){
            System.out.println("Started Polyline");
            this.latestPolyline = new Polyline(panel.getLineThickness());
            model.addPolyline(this.latestPolyline);
            latestPolyline.setColor(panel.getColor());
        }
        this.latestPolyline.newVertex(new Point(event.getX(), event.getY()));

        if (this.latestPolyline.getVertices().size() > 2){
            if ((Math.abs(this.latestPolyline.getVertices().getFirst().x -
                    this.latestPolyline.getVertices().getLast().x) < 4.0) &&
                    (Math.abs(this.latestPolyline.getVertices().getFirst().y -
                            this.latestPolyline.getVertices().getLast().y) < 4.0)){
                Point lastPoint = this.latestPolyline.getVertices().getLast();
                Point firstPoint = this.latestPolyline.getVertices().getFirst();
                lastPoint.setX(firstPoint.x);
                lastPoint.setY(firstPoint.y);
                this.latestPolyline.completed = true;
                panel.getActionArray().insert(new Draw(this.latestPolyline));
//                model.addPolyline(this.latestPolyline);
                this.latestPolyline = null;
                System.out.println("Added Polyline");

            }
        }
        model.setLatestPolyline(this.latestPolyline);
        model.changed();
        model.notifyObservers();
        panel.update(model, null);
    }

    /**
     * Handles the drag mouse event for polyline. Polyline does nothing
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
     * Handles the drag mouse event for polyline. Polyline does nothing
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
     * Handles the moved mouse event for polyline. As the mouse is moved, it adds the location
     * of the polyline according to the location of the mouse on the paint panel.
     *
     * @param event the MouseEvent representing the mouse press
     * @param model the PaintModel containing the current layers drawing objects
     * @param panel the PaintPanel where shapes are drawn
     */
    public void mouseMoved(MouseEvent event, PaintModel model, PaintPanel panel) {
        this.latestPolyline = model.getLatestPolyline();
        Point currentPoint = new Point(event.getX(), event.getY());
        if (this.latestPolyline != null && !this.latestPolyline.getVertices().isEmpty() && currentPoint != null){
            GraphicsContext g2d = panel.getG2d();
            Point lastPoint = this.latestPolyline.getVertices().get(this.latestPolyline.getVertices().size()-1);
            model.addpolyLineColor(panel.getColor());
            g2d.strokeLine(lastPoint.x, lastPoint.y, currentPoint.x, currentPoint.y);
            for (int i = 0; i < this.latestPolyline.getVertices().size(); i++){
                if (i == 0) {
                    g2d.moveTo(this.latestPolyline.getVertices().get(i).x, this.latestPolyline.getVertices().get(i).y);
                }
                else {
                    g2d.lineTo(this.latestPolyline.getVertices().get(i).x, this.latestPolyline.getVertices().get(i).y);
                }
                g2d.lineTo(currentPoint.x, currentPoint.y);
                g2d.stroke();
            }
        model.setLatestPolyline(this.latestPolyline);
        model.changed();
        model.notifyObservers();
        panel.update(model, null);
        }
    }

    /**
     * Decides which mouse event occurs by the user during polyline mode and calls
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
        else if (mouseEventType.equals(MouseEvent.MOUSE_MOVED)){
            this.mouseMoved(event, model, panel);
        }
    }
}
