package ca.utoronto.utm.assignment2.paint;

import javafx.scene.paint.Color;
import java.util.Random;

/**
 * Class implementing the strategy for selecting and setting
 * the drawing mode to random
 *
 * This class implements the ChooserStrategy for random mode. The random
 * mode selects a random shape and color to draw onto the Paint application.
 * It updates the Paint application to display the drawing mode selected by
 * random.
 *
 * @author arnold
 *
 */
public class RandomChooserStrategy implements ChooserStrategy{

    /**
     * Sets the drawing mode to random. Displaying on the Paint application that
     * the current drawing mode is random.
     *
     * @param chooserPanel the ShapeChooserPanel object where the drawing mode
     *                     is selected by the user
     */
    @Override
    public void setMode(ShapeChooserPanel chooserPanel) {
        int randomIndex = new Random().nextInt(chooserPanel.getLisButtons().size() - 5) + 2;
        String shape1 = chooserPanel.getButtonLabels().get(randomIndex);

        double r = new Random().nextDouble();
        double g = new Random().nextDouble();
        double b = new Random().nextDouble();

        javafx.scene.paint.Color randColorHex = new Color(r, g, b, 1.0);
        chooserPanel.viewSetMode(shape1);
        chooserPanel.setColor(randColorHex);

        chooserPanel.resetButtons();
        chooserPanel.getLisButtons().get(chooserPanel.getButtonLabels().indexOf(shape1)).setStyle(
                "-fx-background-color: linear-gradient(to bottom, #9bc0f8, #99fbe3);"
                + "-fx-border-color: #84ffce; -fx-border-radius: 5px;"
                + "-fx-background-radius: 5px;");
    }
}
