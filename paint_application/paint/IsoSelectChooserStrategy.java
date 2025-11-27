package ca.utoronto.utm.assignment2.paint;

/**
 * Class implementing the strategy for selecting and setting
 * the drawing mode to isoselect.
 *
 * This class implements the ChooserStrategy for isoselect. Isoselect
 * is a mode that allows the user to select individual shapes on the Paint
 * application. It updates the Paint application to display that the
 * user selected isoselect drawing mode.
 *
 * @author arnold
 *
 */
public class IsoSelectChooserStrategy implements ChooserStrategy{

    /**
     * Sets the drawing mode to isoselect. Displaying on the Paint application that
     * the current drawing mode is isoselect.
     *
     * @param chooserPanel the ShapeChooserPanel object where the drawing mode
     *                     is selected by the user
     */
    @Override
    public void setMode(ShapeChooserPanel chooserPanel) {
        chooserPanel.viewSetMode("IsoSelect");
        chooserPanel.resetButtons();
        chooserPanel.getLisButtons().get(chooserPanel.getButtonLabels().indexOf("IsoSelect")).setStyle(
                "-fx-background-color: linear-gradient(to bottom, #9bc0f8, #99fbe3);"
                + "-fx-border-color: #84ffce; -fx-border-radius: 5px;"
                + "-fx-background-radius: 5px;");
    }
}