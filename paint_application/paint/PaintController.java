package ca.utoronto.utm.assignment2.paint;

import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.HashMap;
/**
 * The PaintController class manages mouse interactions for a painting application.
 * It determines which drawing strategy to use based on the current mode and directs mouse events
 * to the appropriate strategy.
 */
public class PaintController implements EventHandler<MouseEvent> {
    private HashMap<String, ShapeStrategy> map;
    private ShapeStrategy strategy;
    private String mode = "Circle";
    private PaintModel model;
    private Color color;
    private ShapeChooserPanel chooserPanel;
    private PaintPanel paintPanel;

    /**
     * Initializes a new PaintController with the specified drawing strategies, model, chooser panel, and paint panel.
     *
     * @param paints        a mapping of shape names to their respective drawing strategies
     * @param paintModel    the model that holds the paint data
     * @param chooserPanel  the panel that allows users to choose shape options
     * @param paintPanel    the panel on which shapes are drawn
     */
    public PaintController(HashMap<String, ShapeStrategy> paints, PaintModel paintModel,
                           ShapeChooserPanel chooserPanel, PaintPanel paintPanel) {
        map = paints;
        model = paintModel;
        color = Color.BLACK;
        this.chooserPanel = chooserPanel;
        this.paintPanel = paintPanel;
    }
    /**
     * Sets the current drawing mode, which determines the {@link ShapeStrategy} to be used.
     *
     * @param mode the name of the drawing mode to set
     */
    public void setMode(String mode) {
        this.mode = mode;
    }
    /**
     * Sets the model that holds the paint data.
     *
     * @param model the {@link PaintModel} to be used
     */
    public void setModel(PaintModel model) {
        this.model = model;
    }
    /**
     * Sets the color to be used for drawing.
     *
     * @param color the {@link Color} to set for drawing
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Sets the panel that allows users to choose shapes.
     *
     * @param chooserPanel the {@link ShapeChooserPanel} to set
     */
    public void setShapeChooserPanel(ShapeChooserPanel chooserPanel) {
        this.chooserPanel = chooserPanel;
    }

    /**
     * Handle mouse event actions
     * @param mouseEvent the mouse actions taking place
     */
    @Override
    public void handle(MouseEvent mouseEvent) {
        EventType<MouseEvent> mouseEventType = (EventType<MouseEvent>) mouseEvent.getEventType();

        if (this.mode != null && mouseEvent.getButton() == MouseButton.PRIMARY) {
            strategy = map.get(mode);
            strategy.decideInput(mouseEvent, model, paintPanel);
            if (this.chooserPanel != null) {this.chooserPanel.setColor(this.color);}
        }
    }
}
