package ca.utoronto.utm.assignment2.paint;
/**
 * Interface representing a strategy for selecting and setting a
 * drawing mode in ShapeChooserPanel.
 *
 * This interface allows the implementation of strategies for different
 * shapes to draw them onto the canvas in PaintPanel as needed.
 *
 * @author arnold
 *
 */
public interface ChooserStrategy {

    /**
     * Sets the drawing mode specified by the user. Allowing the
     * paint application to display the correct drawing mode.
     *
     * @param chooserPanel the ShapeChooserPanel object where the drawing mode
     *                     is selected by the user
     */
    void setMode(ShapeChooserPanel chooserPanel);
}
