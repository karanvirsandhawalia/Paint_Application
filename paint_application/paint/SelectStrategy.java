package ca.utoronto.utm.assignment2.paint;

import javafx.event.EventType;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * Represents a strategy for handling mouse interactions for Select.
 *
 * This class includes methods to handle mouse press, drag, release events,
 * as well as to decide the input action for Select drawing mode.
 *
 * @author arnold
 *
 */
public class SelectStrategy implements ShapeStrategy{
    ArrayList<Select> selectedShapes;
    ArrayList<Select> unselectedShapes;

    /**
     * Returns the selected shapes
     * @return list of Shape objects of selected shape
     */
    public ArrayList<Select> getSelectedShapes(){return selectedShapes;}

    /**
     * Handles the press mouse event for Select. Selects the shapes
     * on the Paint application located at the mouse cursors location.
     * Creates a Select object for the selected shapes.
     *
     * @param event the MouseEvent representing the mouse press
     * @param model the PaintModel containing the current layers drawing objects
     * @param panel the PaintPanel where shapes are drawn
     */
    @Override
    public void mousePressed(MouseEvent event, PaintModel model, PaintPanel panel) {
        selectedShapes = new ArrayList<>();
        unselectedShapes = new ArrayList<>();
        HashMap<String, PaintModel> tempModel = panel.getTempModel();
        Integer layerNumber = panel.layerNumber;
        if (layerNumber == null){
            layerNumber = 1;
        }

        PaintModel model1 = tempModel.get(""+layerNumber);
        ArrayList<Object> lisShapes = model1.getShapes();

        Point point = new Point(event.getX(), event.getY());

        for (Object o: lisShapes){
            Shape shape = (Shape)o;
            if (shape.contains(point)){
                Select selectObj = new Select(shape);
                selectedShapes.add(selectObj);}
            else {
                Select selectObj = new Select(shape);
                unselectedShapes.add(selectObj);
            }
        }
        for (Select select: selectedShapes){
            Shape shape = select.getShape();
            if (shape.getColor().equals(shape.getRealColor())){
                if (shape.getColor().equals(Color.BLACK)){
                    shape.setColor(Color.LIGHTGREY);}
                else {shape.setColor(shape.getRealColor().invert());}}
        }
        for (Select select: unselectedShapes){
            Shape shape =select.getShape();
                shape.revertColor();
        }

        panel.update(model, null);
        }

    /**
     * Handles the key press event where the selected shapes can be moved
     * using IJKL. The method moves the shape to the corresponding direction
     * of the key pressed. I -> Up, J -> Left, K -> Down, L -> Right.
     *
     * @param event the KeyEvent representing the key press
     * @param model the PaintModel containing the current layers drawing objects
     * @param panel the PaintPanel where shapes are drawn
     */
    public void keyPressed(KeyEvent event, PaintModel model, PaintPanel panel){
        if (event.getCode() == KeyCode.J) {
            for (Select shape: selectedShapes){
                panel.getActionArray().insert(new Move(shape.getShape(),-5, 0));
                shape.moveleft();
            }
        } else if (event.getCode() == KeyCode.L) {
            for (Select shape: selectedShapes){
                panel.getActionArray().insert(new Move(shape.getShape(),5, 0));
                shape.moveright();
            }
        } else if (event.getCode() == KeyCode.I) {
            for (Select shape : selectedShapes) {
                panel.getActionArray().insert(new Move(shape.getShape(),0, -5));
                shape.moveup();
            }
        }  else if (event.getCode() == KeyCode.K) {
            for (Select shape : selectedShapes) {
                panel.getActionArray().insert(new Move(shape.getShape(),0, 5));
                shape.movedown();
            }
        }
        panel.update(model, null);
    }

    /**
     * Handles the drag mouse event for Select. Select does nothing
     * for this action.
     *
     * @param event the MouseEvent representing the mouse press
     * @param model the PaintModel containing the current layers drawing objects
     * @param panel the PaintPanel where shapes are drawn
     */
    @Override
    public void mouseDragged(MouseEvent event, PaintModel model, PaintPanel panel) {}

    /**
     * Handles the release mouse event for Select. Select does nothing
     * for this action.
     *
     * @param event the MouseEvent representing the mouse press
     * @param model the PaintModel containing the current layers drawing objects
     * @param panel the PaintPanel where shapes are drawn
     */
    @Override
    public void mouseReleased(MouseEvent event, PaintModel model, PaintPanel panel) {}

    /**
     * Decides which mouse event occurs by the user during Select mode and calls
     * the corresponding method to handle it.
     *
     * @param event the MouseEvent representing the mouse press
     * @param model the PaintModel containing the current layers drawing objects
     * @param panel the PaintPanel where shapes are drawn
     */
    @Override
    public void decideInput(MouseEvent event, PaintModel model, PaintPanel panel) {
        EventType<MouseEvent> mouseEventType = (EventType<MouseEvent>) event.getEventType();
        if (mouseEventType.equals(MouseEvent.MOUSE_PRESSED)) {
            this.mousePressed(event, model, panel);
        }
    }
}
