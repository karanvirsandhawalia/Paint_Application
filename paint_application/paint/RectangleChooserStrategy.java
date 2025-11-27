package ca.utoronto.utm.assignment2.paint;

/**
 * Class implementing the strategy for selecting and setting
 * the drawing mode to rectangle.
 *
 * This class implements the ChooserStrategy for rectangle.
 * It updates the Paint application to display that the user selected
 * rectangle drawing mode.
 *
 * @author arnold
 *
 */
public class RectangleChooserStrategy implements ChooserStrategy{

    /**
     * Sets the drawing mode to rectangle. Displaying on the Paint application that
     * the current drawing mode is rectangle.
     *
     * @param chooserPanel the ShapeChooserPanel object where the drawing mode
     *                     is selected by the user
     */
    @Override
    public void setMode(ShapeChooserPanel chooserPanel) {
        chooserPanel.viewSetMode("Rectangle");
        chooserPanel.resetButtons();
        chooserPanel.getLisButtons().get(chooserPanel.getButtonLabels().indexOf("Rectangle")).setStyle(
                "-fx-background-color: linear-gradient(to bottom, #9bc0f8, #99fbe3);"
                + "-fx-border-color: #84ffce; -fx-border-radius: 5px;"
                + "-fx-background-radius: 5px;");
    }
}
