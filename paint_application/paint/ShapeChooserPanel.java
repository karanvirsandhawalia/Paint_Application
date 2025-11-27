package ca.utoronto.utm.assignment2.paint;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import java.util.HashMap;
import javafx.scene.control.Slider;
import javafx.scene.control.ColorPicker;

import java.util.ArrayList;
/**
 * The ShapeChooserPanel allows users to choose from a variety
 * of shapes, set the color of those shapes, and adjust their opacity for
 * drawing on a canvas.
 */
public class ShapeChooserPanel extends GridPane implements EventHandler<ActionEvent> {

        private View view;
        private HBox selectedColorLabel;
        private ChooserStrategy strategy;
        private ArrayList<Button> lisButtons;
        private javafx.scene.paint.Color color;
        private ArrayList<String> buttonLabels = new ArrayList<String>();
        private Slider opacitySlider;
        private ColorPicker colorPicker;
        private HashMap<String, ChooserStrategy> map = new HashMap<String, ChooserStrategy>() {{
            put("⬤", new CircleChooserStrategy());
            put("█", new RectangleChooserStrategy());
            put("⬛", new SquareChooserStrategy());
            put("~", new SquiggleChooserStrategy());
            put("⬮", new OvalChooserStrategy());
            put("▲", new TriangleChooserStrategy());
            put("Random", new RandomChooserStrategy());
            put("💧", new EyeDropperChooserStrategy());
            put("X", new EraserChooserStrategy());
            put("|", new PolylineChooserStrategy());
            put("&", new SelectChooserStrategy());
            put("*", new IsoSelectChooserStrategy());
        }};
        private Label layerLabel;

        /**
         * Create a Panel in the GUI for selecting drawing modes
         * @param view The view of the canvas
         */
        public ShapeChooserPanel(View view) {
                lisButtons = new ArrayList<Button>();
                this.view = view;

                String[] buttonSymbols = { "⬤", "█", "⬛", "~", "|", "⬮", "▲", "X", "Random"};
                buttonLabels.add("Select");
                buttonLabels.add("IsoSelect");
                buttonLabels.add("Circle");
                buttonLabels.add("Rectangle");
                buttonLabels.add("Square");
                buttonLabels.add("Squiggle");
                buttonLabels.add("Polyline");
                buttonLabels.add("Oval");
                buttonLabels.add("Triangle");
                buttonLabels.add("Eraser");
                buttonLabels.add("Random");

                String[] buttonLabels = { "Circle", "Rectangle", "Square",
                        "Squiggle", "Polyline", "Oval", "Triangle", "Eraser", "Random"};

                int row = 0;
                int row2 = 1;

                Button select = new Button("&");
                select.setOnAction(this);
                select.setPrefWidth(50);
                lisButtons.add(select);

                Button isoselect = new Button("*");
                isoselect.setOnAction(this);
                isoselect.setPrefWidth(50);
                lisButtons.add(isoselect);

                HBox hbox = new HBox();
                hbox.getChildren().addAll(isoselect, select);
                this.add(hbox, 0, row);
                row++;

                for (int i = 0; i < buttonSymbols.length; i++) {
                        Button button = new Button(buttonSymbols[i]);
                        button.setMinWidth(100);
                        this.add(button, 0, row);
                        this.lisButtons.add(button);
                        row++;
                        row2++;
                        button.setOnAction(this);
                }

                this.colorPicker = new ColorPicker();
                colorPicker.setValue(Color.BLACK);
                this.add(colorPicker, 0, row);
                colorPicker.setPrefWidth(50);
                colorPicker.setOnAction(event -> setColor(colorPicker.getValue()));

                selectedColorLabel = new HBox();
                Button eyeDropper = new Button("💧");
                eyeDropper.setPrefWidth(50);
                eyeDropper.setPrefHeight(20);
                eyeDropper.setOnAction(this);
                selectedColorLabel.getChildren().addAll(colorPicker, eyeDropper);
                this.color = Color.BLACK;
                view.setColor(color);

                for (Button lisButton1 : lisButtons) {
                    lisButton1.setStyle("-fx-background-color: linear-gradient(to bottom, #f2f2f2, #d6d6d6);"
                            + "-fx-border-color: #b3b3b3; -fx-border-radius: 5px;"
                            + "-fx-background-radius: 5px;"
                    );
                }
                lisButtons.add(eyeDropper);
                lisButtons.get(2).setStyle("-fx-background-color: linear-gradient(to bottom, #9bc0f8, #99fbe3);"
                        + "-fx-border-color: #84ffce; -fx-border-radius: 5px;"
                        + "-fx-background-radius: 5px;"
                );
                row++;
                this.add(new Label(""), 0, row);
                row++;

                this.add(selectedColorLabel, 0, row);
                row++;

                this.add(new Label(""), 0, row);
                row++;

                row++;
                this.layerLabel = new Label("Layer 1");
                layerLabel.setStyle("-fx-font-weight: bold;");
                this.add(layerLabel, 0, row);

                row++;
                Label label = new Label("Opacity: ");
                label.setStyle("-fx-font-weight: bold;");
                this.add(label, 0, row);

                row++;
                opacitySlider = new Slider(0, 100, 100);
                opacitySlider.setPrefWidth(100);
                opacitySlider.valueProperty().addListener((x, y, newValue) -> {
                    view.getPaintPanel().setOpacity(view.getPaintModel(), (double) newValue);
                    view.getPaintPanel().update(view.getPaintModel(), new Object());
                });
                this.add(opacitySlider, 0, row);
                this.setPadding(new Insets(10));

                row++;
                label = new Label("");
                this.add(label, 0, row);
        }
        /**
         * Handle action events corresponding to the selected drawing modes
         * @param event the action event input by the user
         */
        @Override
        public void handle(ActionEvent event) {
            String command = ((Button) event.getSource()).getText();
            strategy = map.get(command);
            strategy.setMode(this);
        }

        /**
         * Reset the opacity slider to the default
         * @param opacity the current opacity
         */
        public void resetOpacitySlider(double opacity) {
            opacitySlider.setValue(opacity * 100);
        }

        /**
         * Reset the view of the buttons to the default style
         */
        public void resetButtons() {
            for (Button lisButton : lisButtons) {
                lisButton.setStyle("-fx-background-color: linear-gradient(to bottom, #f2f2f2, #d6d6d6);"
                        + "-fx-border-color: #b3b3b3; -fx-border-radius: 5px;"
                        + "-fx-background-radius: 5px;");
            }
        }

        /**
         * Set the layers displayed text
         * @param text the text of the layers label
         */
        public void setLayerLabel(String text) {
                this.layerLabel.setText(text);
            }

        /**
         * @return return the list of buttons
         */
        public ArrayList<Button> getLisButtons(){return lisButtons;}

        /**
         * @return return the HBox object that contains all the colors
         */
        public HBox getSelectedColorLabel(){return this.selectedColorLabel;}

        /**
         * set the mode of the view to the drawing mode
         * @param s the selected drawing mode
         */
        public void viewSetMode(String s){this.view.setMode(s);}

        /**
         * @return return the current mode of the view
         */
        public String getViewSetMode(){return this.view.getPaintPanel().getMode();}

        /**
         * @return return a list of each label of each button
         */
        public ArrayList<String> getButtonLabels() {return buttonLabels;}

        /**
         * set the color of the shapes to be drawn
         * @param color the selected color
         */
        public void setColor(javafx.scene.paint.Color color) {
                this.color = color;
                view.setColor(color);
                this.colorPicker.setValue(color);}
}