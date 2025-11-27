package ca.utoronto.utm.assignment2.paint;

/**
 * Class implementing the strategy for selecting and setting
 * the drawing mode to select.
 *
 * This class implements the ChooserStrategy for select. Select is a
 * mode that allows the user to select existing objects on the canvas.
 * It updates the Paint application to display that the user selected
 * select mode.
 *
 *
 * @author arnold
 *
 */
public class SelectChooserStrategy implements ChooserStrategy{

    /**
     * Sets the drawing mode to select. Displaying on the Paint application that
     * the current drawing mode is select.
     *
     * @param chooserPanel the ShapeChooserPanel object where the drawing mode
     *                     is selected by the user
     */
    @Override
    public void setMode(ShapeChooserPanel chooserPanel) {
        chooserPanel.viewSetMode("Select");
        chooserPanel.resetButtons();
        chooserPanel.getLisButtons().get(chooserPanel.getButtonLabels().indexOf("Select")).setStyle(
                "-fx-background-color: linear-gradient(to bottom, #9bc0f8, #99fbe3);"
                + "-fx-border-color: #84ffce; -fx-border-radius: 5px;"
                + "-fx-background-radius: 5px;");
    }
}
