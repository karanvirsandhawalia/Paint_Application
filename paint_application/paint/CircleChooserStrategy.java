package ca.utoronto.utm.assignment2.paint;

/**
 * Class implementing the strategy for selecting and setting
 * the drawing mode to circle in ShapeChooserPanel.
 *
 * This class implements the ChooserStrategy for circle.
 * It updates the Paint application to display that the user selected
 * circle drawing mode.
 *
 * @author arnold
 *
 */
public class CircleChooserStrategy implements ChooserStrategy{

    /**
     * Sets the drawing mode to Circle. Displaying on the Paint application that
     * the current drawing mode is circle.
     *
     * @param chooserPanel the ShapeChooserPanel object where the drawing mode
     *                     is selected by the user
     */
    @Override
    public void setMode(ShapeChooserPanel chooserPanel) {
        chooserPanel.viewSetMode("Circle");
        chooserPanel.resetButtons();
        chooserPanel.getLisButtons().get(chooserPanel.getButtonLabels().indexOf("Circle")).setStyle(
                "-fx-background-color: linear-gradient(to bottom, #9bc0f8, #99fbe3);"
                + "-fx-border-color: #84ffce; -fx-border-radius: 5px;"
                + "-fx-background-radius: 5px;");
    }
}
