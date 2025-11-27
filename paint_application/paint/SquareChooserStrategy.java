package ca.utoronto.utm.assignment2.paint;

/**
 * Class implementing the strategy for selecting and setting
 * the drawing mode to square.
 *
 * This class implements the ChooserStrategy for square.
 * It updates the Paint application to display that the user selected
 * square drawing mode.
 *
 * @author arnold
 *
 */
public class SquareChooserStrategy implements ChooserStrategy{

    /**
     * Sets the drawing mode to square. Displaying on the Paint application that
     * the current drawing mode is square.
     *
     * @param chooserPanel the ShapeChooserPanel object where the drawing mode
     *                     is selected by the user
     */
    @Override
    public void setMode(ShapeChooserPanel chooserPanel) {
        chooserPanel.viewSetMode("Square");
        chooserPanel.resetButtons();
        chooserPanel.getLisButtons().get(chooserPanel.getButtonLabels().indexOf("Square")).setStyle(
                "-fx-background-color: linear-gradient(to bottom, #9bc0f8, #99fbe3);"
                + "-fx-border-color: #84ffce; -fx-border-radius: 5px;"
                + "-fx-background-radius: 5px;");
    }

}
