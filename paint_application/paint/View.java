package ca.utoronto.utm.assignment2.paint;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.util.ArrayList;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.IntBuffer;
import java.util.Objects;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javax.imageio.ImageIO;
/**
 * The View class manages the creation and interaction with the painting model,
 * and handles user input through keyboard and mouse events.
 */
public class View implements EventHandler<ActionEvent> {

        private PaintModel paintModel;
        private ArrayList<PaintModel> paintModels;
        private PaintPanel paintPanel;
        private ArrayList<Boolean> visibleModels = new ArrayList<Boolean>();
        private ShapeChooserPanel shapeChooserPanel;
        private Integer lineThickness = 1;
        private Menu menuLayer;
        private VBox layerBox;
        private int layers = 1;
        private Stage primStage;
        private ArrayList<MenuButton> buttons = new ArrayList<MenuButton>();

        /**
         * The View constructor method initializes the interface for the painting application
         * @param model the PaintModel
         * @param stage the stage containing the entire canvas
         */
        public View(PaintModel model, Stage stage) {
            this.paintModel = model;
            this.paintModels = new ArrayList<PaintModel>();
            this.paintModels.add(model);
            this.primStage = stage;
            this.paintPanel = new PaintPanel(this.paintModel);
            this.shapeChooserPanel = new ShapeChooserPanel(this);
            paintPanel.setShapeChooser(shapeChooserPanel);



            BorderPane root = new BorderPane();
            layerBox = new VBox();
            root.setTop(createMenuBar());
            root.setCenter(this.paintPanel);
            root.setLeft(this.shapeChooserPanel);

            ScrollPane scrollPane = new ScrollPane();
            scrollPane.setContent(layerBox);
            scrollPane.setFitToWidth(true);
            scrollPane.setPannable(true);

            MenuButton btn1 = new MenuButton("Layer 1");
            MenuItem itm = new MenuItem("Select 1");
            this.visibleModels.add(true);
            itm.setOnAction(this);
            btn1.getItems().addAll(itm);
            Button btn2 = new Button("+");
            btn2.setPrefWidth(110);
            btn2.setPrefHeight(20);
            buttons.add(btn1);
            paintPanel.setLayerIcons(buttons);
            layerBox.getChildren().addAll(btn2, btn1);
            layerBox.setPadding(new Insets(10, 10, 10, 10));
            btn1.setOnAction(this);
            btn2.setOnAction( event -> {

                    PaintModel temp = new PaintModel();
                    this.paintModels.add(temp);
                    this.visibleModels.add(true);
                    layers++;
                    paintPanel.addLayer(temp, layers);
                    MenuButton btn = new MenuButton("Layer " + layers);
                    MenuItem tempitm = new MenuItem("Select " + layers);
                    MenuItem tempitm2 = new MenuItem("Hide " + layers);
                    btn.getItems().addAll(tempitm, tempitm2);
                    tempitm.setOnAction(this);
                    tempitm2.setOnAction(this);
                    layerBox.getChildren().add(btn);
                    buttons.add(btn);
                    paintPanel.setLayerIcons(buttons);
                    paintPanel.update(null ,null);
            });

            layerBox.setAlignment(Pos.CENTER);
            layerBox.setPrefWidth(160);
            root.setRight(scrollPane);
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.setTitle("Paint");
            stage.setHeight(470);
            stage.setWidth(700);
            stage.setMinHeight(470);
            stage.setMinWidth(720);
            this.resizingHeight(430);
            this.resizingWidth(720);
            root.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
            stage.show();

                scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
                        KeyCombination undoKey = new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN);
                        KeyCombination redoKey = new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_DOWN);

                        if (undoKey.match(event)) {
                                this.paintPanel.getActionArray().undo();
                                event.consume();
                        } else if (redoKey.match(event)) {
                                this.paintPanel.getActionArray().redo();
                                event.consume();
                        }
                });
                scene.setOnKeyPressed(event -> {
                        if ("Select".equals(this.paintPanel.getMode())) {
                                SelectStrategy selectStrategy = paintPanel.getSelectStrategy();
                                if (!selectStrategy.getSelectedShapes().isEmpty()){
                                        selectStrategy.keyPressed(event, this.paintModel, this.paintPanel);}
                        }
                        if ("IsoSelect".equals(this.paintPanel.getMode())) {

                                IsoSelectStrategy selectStrategy = paintPanel.getIsoSelectStrategy();
                                if (selectStrategy.getSelectedShape().getShape() != null){
                                        selectStrategy.keyPressed(event, this.paintModel, this.paintPanel);}
                        }
                });
        scene.widthProperty().addListener((obs, old, current) -> resizingWidth((Double) current));
        scene.heightProperty().addListener((obs, old, current) -> resizingHeight((Double) current));
        }
        /**
         * Resize the width of the PaintPanel
         * @param a The value to which the width is being reassigned to
         */
        public void resizingWidth(double a){
                this.paintPanel.setWidth(a-300);
                this.paintPanel.update(null, null);
        }

        /**
         * Resize the height of the PaintPanel
         * @param a The value to which the height is being reassigned to
         */
        public void resizingHeight(double a){
                this.paintPanel.setHeight(a-50);

                this.paintPanel.update(null, null);

        }

        /**
         * @return the PaintModel
         */
        public PaintModel getPaintModel() {
                return this.paintModel;
        }

        /**
         * @return the PaintPanel
         */
        public PaintPanel getPaintPanel() {
                return this.paintPanel;
        }


        /**
         * Set the drawing mode
         * @param mode the drawing mode selected
         */
        public void setMode(String mode){
                if (!Objects.equals(mode, "Select") && !mode.equals("IsoSelect")){
                        for (Object obj: paintModel.getShapes()){
                                Shape shape = (Shape) obj;
                                shape.revertColor();
                        }
                        paintPanel.update(paintModel, null);
                }
                this.paintPanel.setMode(mode);
        }

        /**
         * set the color of the shapes to be drawn
         * @param color the selected color
         */
        public void setColor( javafx.scene.paint.Color color){
                this.paintPanel.setColor(color);
        }
        private MenuBar createMenuBar() {

                MenuBar menuBar = new MenuBar();
                Menu menu;
                MenuItem menuItem;


                menu = new Menu("File");

                menuItem = new MenuItem("New");
                menuItem.setOnAction(this);
                menu.getItems().add(menuItem);

                menuItem = new MenuItem("Open");
                menuItem.setOnAction(this);
                menu.getItems().add(menuItem);

                menuItem = new MenuItem("Save");
                menuItem.setOnAction(this);
                menu.getItems().add(menuItem);

                menu.getItems().add(new SeparatorMenuItem());

                menuItem = new MenuItem("Exit");
                menuItem.setOnAction(this);
                menu.getItems().add(menuItem);

                menuBar.getMenus().add(menu);


                menu = new Menu("Edit");

                menu.getItems().add(new SeparatorMenuItem());
                menuItem = new MenuItem("Undo");
                menuItem.setOnAction(this);
                menu.getItems().add(menuItem);

                menuItem = new MenuItem("Redo");
                menuItem.setOnAction(this);
                menu.getItems().add(menuItem);

                menuItem = new MenuItem("Clear All");
                menuItem.setOnAction(this);
                menu.getItems().add(menuItem);

                menuBar.getMenus().add(menu);

                menu = new Menu("Outline");

                        Menu innerMenu = new Menu("Fill");
                                menuItem = new MenuItem("Yes");
                                menuItem.setOnAction(this);
                        innerMenu.getItems().add(menuItem);
                                menuItem = new MenuItem("No");
                                menuItem.setOnAction(this);
                        innerMenu.getItems().add(menuItem);
                menu.getItems().add(innerMenu);


                Slider lineThicknessSlider = new Slider(1, 10, 1);
                lineThicknessSlider.setShowTickMarks(false);
                lineThicknessSlider.setShowTickLabels(false);

                HBox lineThicknessBox = new HBox(10, lineThicknessSlider);

                CustomMenuItem lineThicknessMenuItem = new CustomMenuItem(lineThicknessBox);
                lineThicknessMenuItem.setHideOnClick(true);

                lineThicknessSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
                        this.lineThickness = newValue.intValue();
                        updateLineThickness();
                });


                Menu innerMenu1 = new Menu("Stroke");
                innerMenu1.getItems().add(lineThicknessMenuItem);

                menu.getItems().add(innerMenu1);



                menuBar.getMenus().add(menu);

                return menuBar;
        }

        /**
         * update the lineThickness
         */
        public void updateLineThickness(){paintPanel.updateLineThickness(lineThickness);}

        /**
         * reset the canvas setting up fresh instances
         */
        public void newPainting() {
                this.layers = 1;
                this.buttons = new ArrayList<>();
                layerBox = new VBox();
                this.lineThickness = 1;
                this.visibleModels.clear();
                PaintModel model = new PaintModel();
                this.paintModel = model;
                this.paintModels = new ArrayList<PaintModel>();
                this.paintModels.add(model);

                this.paintPanel = new PaintPanel(this.paintModel);
                this.shapeChooserPanel = new ShapeChooserPanel(this);
                this.paintPanel.chooserPanel = this.shapeChooserPanel;

                BorderPane root = new BorderPane();
                root.setTop(createMenuBar());
                root.setCenter(this.paintPanel);
                root.setLeft(this.shapeChooserPanel);

                ScrollPane scrollPane = new ScrollPane();
                scrollPane.setContent(layerBox);
                scrollPane.setFitToWidth(true);
                scrollPane.setPannable(true);

                MenuButton btn1 = new MenuButton("Layer 1");
                MenuItem itm = new MenuItem("Select 1");
                this.visibleModels.add(true);
                itm.setOnAction(this);
                btn1.getItems().addAll(itm);
                Button btn2 = new Button("+");
                btn2.setPrefWidth(110);
                btn2.setPrefHeight(20);
                buttons.add(btn1);
                paintPanel.setLayerIcons(buttons);
                layerBox.getChildren().addAll(btn2, btn1);
                layerBox.setPadding(new Insets(10, 10, 10, 10));
                btn1.setOnAction(this);
                btn2.setOnAction( event -> {

                        PaintModel temp = new PaintModel();
                        this.paintModels.add(temp);
                        this.visibleModels.add(true);
                        layers++;
                        paintPanel.addLayer(temp, layers);
                        MenuButton btn = new MenuButton("Layer " + layers);
                        MenuItem tempitm = new MenuItem("Select " + layers);
                        MenuItem tempitm2 = new MenuItem("Hide " + layers);
                        btn.getItems().addAll(tempitm, tempitm2);
                        tempitm.setOnAction(this);
                        tempitm2.setOnAction(this);
                        layerBox.getChildren().add(btn);
                        buttons.add(btn);
                        paintPanel.setLayerIcons(buttons);
                        paintPanel.update(null ,null);
                });

                layerBox.setPrefWidth(160);
                layerBox.setAlignment(Pos.CENTER);
                root.setRight(scrollPane);

                Scene scene = new Scene(root);
                primStage.setScene(scene);
                primStage.setTitle("Paint");

                this.resizingHeight(430);
                this.resizingWidth(700);
                primStage.setHeight(470);
                primStage.setWidth(700);
                primStage.setMinHeight(470);
                primStage.setMinWidth(700);

                root.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
                primStage.show();

                scene.addEventFilter(KeyEvent.KEY_PRESSED, event2 -> {
                        KeyCombination undoKeyCombo = new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN);
                        KeyCombination redoKeyCombo = new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_DOWN);

                        if (undoKeyCombo.match(event2)) {
                                this.paintPanel.getActionArray().undo();
                                event2.consume();
                        } else if (redoKeyCombo.match(event2)) {
                                this.paintPanel.getActionArray().redo();
                                event2.consume();
                        }
                });
                scene.setOnKeyPressed(event -> {
                        if ("Select".equals(this.paintPanel.getMode())) {
                                SelectStrategy selectStrategy = paintPanel.getSelectStrategy();
                                if (!selectStrategy.getSelectedShapes().isEmpty()){
                                        selectStrategy.keyPressed(event, this.paintModel, this.paintPanel);}
                        }
                        if ("IsoSelect".equals(this.paintPanel.getMode())) {
                                IsoSelectStrategy selectStrategy = paintPanel.getIsoSelectStrategy();
                                selectStrategy.keyPressed(event, this.paintModel, this.paintPanel);
                        }
                });
                scene.widthProperty().addListener((obs, old, current) -> resizingWidth((Double) current));
                scene.heightProperty().addListener((obs, old, current) -> resizingHeight((Double) current));
        }

        /**
         * Handle action events corresponding to the selected menu items
         * @param event the action event input by the user
         */
        @Override
        public void handle(ActionEvent event) {
                String command = ((MenuItem) event.getSource()).getText();
                System.out.println(command);
                if (command.equals("Exit")) {
                        Platform.exit();
                }

                switch (command) {
                        case "Save":
                                FileChooser selectFile = new FileChooser();
                                FileChooser.ExtensionFilter tempFile = new FileChooser.ExtensionFilter("PNG Files",
                                                                                                "*.png");
                                selectFile.getExtensionFilters().add(tempFile);

                                File choosenFile = selectFile.showSaveDialog(primStage);
                                if (choosenFile != null) {

                                        WritableImage image = new WritableImage((int) paintPanel.getWidth(),
                                                                                        (int) paintPanel.getHeight());
                                        paintPanel.snapshot(null, image);

                                        BufferedImage updatedImage = new BufferedImage((int) paintPanel.getWidth(),
                                                                                        (int) paintPanel.getHeight(),
                                                                                        BufferedImage.TYPE_INT_ARGB);
                                        IntBuffer buffer = IntBuffer.allocate((int) paintPanel.getWidth() *
                                                                                        (int) paintPanel.getHeight());

                                        try{image.getPixelReader().getPixels(0, 0, (int) paintPanel.getWidth(),
                                                                        (int) paintPanel.getHeight(),
                                                                        PixelFormat.getIntArgbInstance(),
                                                                        buffer, (int) paintPanel.getWidth());}
                                        catch (Exception e){
                                        }

                                        updatedImage.setRGB(0, 0, (int) paintPanel.getWidth(),
                                                                (int) paintPanel.getHeight(), buffer.array(), 0,
                                                                (int) paintPanel.getWidth());

                                        try {
                                                ImageIO.write(updatedImage, "png", choosenFile);
                                        } catch (Exception e) {
                                                System.out.println(e.getMessage());
                                        }
                                }
                                break;
                        case "Open":
                                newPainting();
                                FileChooser selectFile2 = new FileChooser();
                                FileChooser.ExtensionFilter tempFile2 = new FileChooser.ExtensionFilter("PNG Files",
                                                                                                "*.png");
                                selectFile2.getExtensionFilters().add(tempFile2);

                                File choosenFile2 = selectFile2.showOpenDialog(primStage);
                                if (choosenFile2 != null) {
                                        Image img = new Image(choosenFile2.toURI().toString());
                                        paintPanel.setImage(img);
                                        paintPanel.update(null, null);
                                        this.resizingHeight(430);
                                        this.resizingWidth(700);
                                }
                                break;
                        case "Yes":
                                this.paintModel.setFilled(true);
                                break;
                        case "No":
                                this.paintModel.setFilled(false);
                                break;
                        case "Undo":
                                this.paintPanel.getActionArray().undo();
                                break;
                        case "Redo":
                                this.paintPanel.getActionArray().redo();
                                break;
                        case "Clear All":
                                paintPanel.getActionArray().clear();
                                this.paintModel.clearAll();
                        case "New":
                                newPainting();
                                this.resizingHeight(430);
                                this.resizingWidth(700);
                                break;
                        default:

                }

                for (int i = 1; i <= layers; i++) {
                        if (command.equals("Select " + i) && this.visibleModels.get(i - 1)) {
                                SelectStrategy selectStrategy = paintPanel.getSelectStrategy();
                                MouseEvent mouseEvent = new MouseEvent(
                                        MouseEvent.MOUSE_PRESSED,
                                        9999999999999999.0, 9999999999999999.0,
                                        500.0, 500.0,
                                        MouseButton.PRIMARY,
                                        1,
                                        false, false, false, false,
                                        true,
                                        false,
                                        false,
                                        false,
                                        false,
                                        false,
                                        null
                                );
                                selectStrategy.mousePressed(mouseEvent,paintModel, paintPanel);
                                paintPanel.setLayer(i);
                                paintModel = paintModels.get(i-1);
                                shapeChooserPanel.setLayerLabel("Layer " + i);
                                double opacity = paintPanel.getOpacity(i);
                                shapeChooserPanel.resetOpacitySlider(opacity);
                        }

                }

                if (command.equals("Select 1")) {
                        SelectStrategy selectStrategy = paintPanel.getSelectStrategy();
                        MouseEvent mouseEvent = new MouseEvent(
                                MouseEvent.MOUSE_PRESSED,
                                9999999999999999.0, 9999999999999999.0,
                                500.0, 500.0,
                                MouseButton.PRIMARY,
                                1,
                                false, false, false, false,
                                true,
                                false,
                                false,
                                false,
                                false,
                                false,
                                null
                        );
                        selectStrategy.mousePressed(mouseEvent,paintModel, paintPanel);
                        paintPanel.setLayer(1);
                        paintModel = paintModels.getFirst();
                        shapeChooserPanel.setLayerLabel("Layer 1");
                        double opacity = paintPanel.getOpacity(1);
                        shapeChooserPanel.resetOpacitySlider(opacity);
                }

                for (int i = 1; i <= layers; i++) {

                        if (command.equals("Hide " + i)) {
                                if (paintModel.equals(paintModels.get(i-1))) {
                                        paintPanel.setLayer(1);
                                        paintModel = paintModels.getFirst();
                                        shapeChooserPanel.setLayerLabel("Layer 1");
                                        double opacity = paintPanel.getOpacity(1);
                                        shapeChooserPanel.resetOpacitySlider(opacity);
                                }
                                ((MenuItem) event.getSource()).setText("Reveal " + i);
                                paintPanel.hideLayer(i);
                                this.visibleModels.set(i-1, false);
                                paintPanel.update(paintModel, new Object());
                                break;
                        }

                        if (command.equals("Reveal " + i)) {
                                ((MenuItem) event.getSource()).setText("Hide " + i);
                                paintPanel.revealLayer(i);
                                this.visibleModels.set(i-1, true);
                                paintPanel.update(paintModel, new Object());
                                break;
                        }
                }
        }
}
