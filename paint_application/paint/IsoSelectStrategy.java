package ca.utoronto.utm.assignment2.paint;

import javafx.event.Event;
import javafx.event.EventType;
import javafx.scene.input.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * Represents a strategy for handling mouse interactions for IsoSelect.
 *
 * This class includes methods to handle mouse press, drag, release events,
 * as well as to decide the input action for IsoSelect drawing mode.
 *
 * @author arnold
 *
 */
public class IsoSelectStrategy implements ShapeStrategy{

    ArrayList<IsoSelect> unselectedShapes;
    IsoSelect selectedShape;
    Shape copiedShape;
    private double mousex;
    private double mousey;
    private boolean copied;

    /**
     * Returns the selected shape
     * @return Shape object of selected shape
     */
    public IsoSelect getSelectedShape(){return selectedShape;}

    /**
     * Handles the press mouse event for IsoSelect. Selects the shape
     * on the Paint application located at the mouse cursors location.
     * Creates an IsoSelect object for the selected shape.
     *
     * @param event the MouseEvent representing the mouse press
     * @param model the PaintModel containing the current layers drawing objects
     * @param panel the PaintPanel where shapes are drawn
     */
    @Override
    public void mousePressed(MouseEvent event, PaintModel model, PaintPanel panel) {
        selectedShape = new IsoSelect(null);
        unselectedShapes = new ArrayList<>();
        HashMap<String, PaintModel> tempModel = panel.getTempModel();
        Integer layerNumber = panel.layerNumber;
        if (layerNumber == null){
            layerNumber = 1;
        }

        PaintModel model1 = tempModel.get(""+layerNumber);
        ArrayList<Object> lisShapes = model1.getShapes();
        ArrayList<Object> lisShapes1 = new ArrayList<>();
        for (int i = lisShapes.size() -1; i>= 0; i--){
            lisShapes1.add(lisShapes.get(i));
        }
        Point point = new Point(event.getX(), event.getY());

        for (Object o: lisShapes1){
            Shape shape = (Shape)o;
            if (shape.isVisible()){
                if (shape.contains(point) && selectedShape.getShape() == null){
                    selectedShape = new IsoSelect(shape);
                    break;
                }
            }


        }
        for (Object o: lisShapes){
            Shape shape = (Shape)o;
            if (!shape.equals(selectedShape.getShape())){
                unselectedShapes.add(new IsoSelect(shape));
            }
        }

        Shape shape = selectedShape.getShape();
        if (shape != null ){
            shape.invertColor();
            }


        for (IsoSelect select: unselectedShapes){
            Shape shape1 = select.getShape();
            if (shape1.realColor != null && shape1.realColor.equals(Color.BLACK)) {
                shape1.setColor(Color.BLACK);
            }
            else {
                shape1.revertColor();
            }
        }

        panel.update(model, null);
    }

    /**
     * Updates the current mouse position.
     *
     * @param event MouseEvent containing the current mouse coordinates
     * @param model PaintModel containing the current model state
     * @param panel PaintPanel on which shapes are being drawn
     */
    public void mouseMoved(MouseEvent event, PaintModel model, PaintPanel panel){
        this.mousey = event.getY();
        this.mousex = event.getX();
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
        KeyCodeCombination ctrlX = new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN);
        KeyCodeCombination ctrlC = new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN);
        KeyCodeCombination ctrlV = new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN);
        if (event.getCode() == KeyCode.J) {
            panel.getActionArray().insert(new Move(selectedShape.getShape(),-5, 0));
            selectedShape.moveleft();
        } else if (event.getCode() == KeyCode.L) {
            panel.getActionArray().insert(new Move(selectedShape.getShape(),5, 0));
            selectedShape.moveright();

        } else if (event.getCode() == KeyCode.I) {
            panel.getActionArray().insert(new Move(selectedShape.getShape(),0, -5));
            selectedShape.moveup();
        }  else if (event.getCode() == KeyCode.K) {
            panel.getActionArray().insert(new Move(selectedShape.getShape(),0, 5));
            selectedShape.movedown();
        }
        else if (ctrlX.match(event)) {
            if (selectedShape.getShape() != null){
                this.copied = false;
                this.copiedShape = selectedShape.getShape();
                panel.getActionArray().insert(new Cut());
                copiedShape.setVisibleFalse();
                }
        }
        else if (ctrlC.match(event)) {
            if (selectedShape.getShape() != null){
                this.copied = true;
                this.copiedShape = selectedShape.getShape();

            }
        }
        else if (ctrlV.match(event)){
            HashMap<String, PaintModel> tempModel = panel.getTempModel();
            Integer layerNumber = panel.layerNumber;
            if (layerNumber == null){
                layerNumber = 1;
            }

            PaintModel model1 = tempModel.get(""+layerNumber);
            if (copiedShape != null){
                Shape newShape = this.copiedShape.clone();
                if (!copied){
                    panel.getActionArray().insert(new Paste(this.mousex, this.mousey, copiedShape));
                    copiedShape.moveNewLocation(this.mousex, this.mousey);
                }
                else{
                    panel.getActionArray().insert(new Paste(this.mousex, this.mousey, newShape));
                    newShape.moveNewLocation(this.mousex, this.mousey);
                    model1.getShapes().add(newShape);
                    selectedShape = new IsoSelect(null);
                    for (Object o: model1.getShapes()){
                        Shape shape = (Shape)o;
                        shape.revertColor();
                    }
                }
                copiedShape.setVisible();

            }
        }

        panel.update(model, null);
    }

    /**
     * Handles the drag mouse event for IsoSelect. IsoSelect does nothing
     * for this action.
     *
     * @param event the MouseEvent representing the mouse press
     * @param model the PaintModel containing the current layers drawing objects
     * @param panel the PaintPanel where shapes are drawn
     */
    @Override
    public void mouseDragged(MouseEvent event, PaintModel model, PaintPanel panel) {}

    /**
     * Handles the release mouse event for IsoSelect. IsoSelect does nothing
     * for this action.
     *
     * @param event the MouseEvent representing the mouse press
     * @param model the PaintModel containing the current layers drawing objects
     * @param panel the PaintPanel where shapes are drawn
     */
    @Override
    public void mouseReleased(MouseEvent event, PaintModel model, PaintPanel panel) {}

    /**
     * Decides which mouse event occurs by the user during IsoSelect mode and calls
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
        else{
            this.mouseMoved(event, model, panel);


        }

    }
}