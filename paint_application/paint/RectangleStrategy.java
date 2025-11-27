package ca.utoronto.utm.assignment2.paint;

import javafx.event.EventType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 * Represents a strategy for handling mouse interactions for rectangle.
 *
 * This class includes methods to handle mouse press, drag, release events,
 * as well as to decide the input action for rectangle drawing mode.
 *
 * @author arnold
 *
 */
public class RectangleStrategy implements ShapeStrategy {
        private Rectangle shape = null;

        /**
         * Handles the press mouse event for rectangle. Selects the rectangles
         * top left corner as the location of the mouse press on the paint application.
         *
         * @param event the MouseEvent representing the mouse press
         * @param model the PaintModel containing the current layers drawing objects
         * @param panel the PaintPanel where shapes are drawn
         */
        @Override
        public void mousePressed(MouseEvent event, PaintModel model, PaintPanel panel){
            System.out.println("Started Rectangle");
            Point c1 = new Point(event.getX(), event.getY());
            this.shape=new Rectangle(c1, null, panel.getLineThickness());
            shape.setColor(panel.getColor());
        }

        /**
         * Handles the drag mouse event for rectangle. Continues to the other corner
         * of the rectangle based on the location of the mouse on the paint application
         * and displays it expanding.
         *
         * @param event the MouseEvent representing the mouse press
         * @param model the PaintModel containing the current layers drawing objects
         * @param panel the PaintPanel where shapes are drawn
         */
        @Override
        public void mouseDragged(MouseEvent event, PaintModel model, PaintPanel panel) {
            Point release = new Point(event.getX(), event.getY());
            GraphicsContext g2d = panel.getGraphicsContext2D();
            g2d.clearRect(0, 0, panel.getWidth(), panel.getHeight());
            panel.update(model, null);
            g2d.setFill(shape.getColor());
            Point c1 = this.shape.getCorner1();
            Point c2 = release;
            double x = Math.min(c1.x, c2.x);
            double y = Math.min(c1.y, c2.y);
            double width = Math.abs(c1.x - c2.x);
            double height = Math.abs(c1.y - c2.y);
            g2d.setStroke(shape.getColor());
            if (model.getFilled()){g2d.fillRect(x, y, width, height);}
            else{
                g2d.setLineWidth(panel.getLineThickness() + 1);
                g2d.strokeRect(x, y, width, height);}


        }

        /**
         * Handles the release mouse event for rectangle. Once the mouse is released,
         * the rectangle shape is completely created and is added to the associated
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
                model.addRectangle(this.shape);
                System.out.println("Added Rectangle");
                this.shape=null;
            }
        }

        /**
         * Decides which mouse event occurs by the user during rectangle mode and calls
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

