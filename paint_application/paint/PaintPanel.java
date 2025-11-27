package ca.utoronto.utm.assignment2.paint;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.MenuButton;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Map;
import java.util.Observer;
/**
 * The PaintPanel class represents the drawing area of the paint application.
 * It manages multiple layers of shapes that can be drawn, modified, and interacted
 * with. The class supports various drawing modes such as Circle, Rectangle, Polyline,
 * and allows for shape selection, and layer visibility control.
 */

public class PaintPanel extends Canvas implements Observer {
    private PaintController controller;
    private String mode="Circle";
    private ArrayList<MenuButton> menuButtons;
    private javafx.scene.image.Image image = null;
    private javafx.scene.paint.Color color;
    private PaintModel model;
    private HashMap<String, PaintModel> modelLayers = new HashMap<String, PaintModel>();
    private HashMap<String, Double> opacityLayers = new HashMap<String, Double>();
    private ArrayList<Integer> visibleLayers= new ArrayList<Integer>();
    private ShapeStrategy strategy;
    private SelectStrategy selectStrategy=new SelectStrategy();
    private IsoSelectStrategy isoStrategy=new IsoSelectStrategy();
    private HashMap<String, ShapeStrategy> map = new HashMap<String, ShapeStrategy>() {{
        put("Circle", new CircleStrategy());
        put("Rectangle", new RectangleStrategy());
        put("Oval", new OvalStrategy());
        put("Square", new SquareStrategy());
        put("Triangle", new TriangleStrategy());
        put("Squiggle", new SquiggleStrategy());
        put("EyeDropper", new EyeDropperStrategy());
        put("Eraser", new EraserStrategy());
        put("Polyline", new PolylineStrategy());
        put("Select", selectStrategy);
        put("IsoSelect", isoStrategy);
    }};
    private int lineThickness;
    private Canvas hiddenCanvas = new Canvas(this.getWidth(), this.getHeight());
    private GraphicsContext g2d = this.getGraphicsContext2D();
    public ShapeChooserPanel chooserPanel;

    public Circle circle; // This is VERY UGLY, should somehow fix this!!
    public Rectangle rectangle;
    public Square square;
    public Oval oval;
    public Triangle triangle;
    public ActionArray actionArray = new ActionArray(this, model);
    public Integer layerNumber;


    /**
     * Create a PaintPanel
     * @param model The PaintModel in the PaintPanel
     */
    public PaintPanel(PaintModel model) {
        super(300, 300);
        this.model=model;
        this.model.addObserver(this);
        modelLayers.put("1", model);
        opacityLayers.put("1", 1.0);
        visibleLayers.add(1);

        this.controller = new PaintController(map, model, chooserPanel, this);

        this.addEventHandler(MouseEvent.MOUSE_PRESSED, controller);
        this.addEventHandler(MouseEvent.MOUSE_RELEASED, controller);
        this.addEventHandler(MouseEvent.MOUSE_MOVED, controller);
        this.addEventHandler(MouseEvent.MOUSE_CLICKED, controller);
        this.addEventHandler(MouseEvent.MOUSE_DRAGGED, controller);

        g2d = this.getGraphicsContext2D();
        g2d.setFill(Color.WHITE);
        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());

    }

    public void setShapeChooser(ShapeChooserPanel chooserPanel) {
        this.chooserPanel = chooserPanel;
        controller.setShapeChooserPanel(chooserPanel);
    }

    /**
     *  Controller aspect of this
     * @param mode The current drawing mode
     */
    public void setMode(String mode){
        this.mode=mode;
        controller.setMode(mode);
        if (mode != "Select"){
            for (Object obj: model.getShapes()){
                Shape shape = (Shape) obj;
                shape.revertColor();
            }
        }
        System.out.println(this.mode);
    }

    /**
     * Set the background image of the canvas
     * @param img The image being set
     */
    public void setImage(javafx.scene.image.Image img) {
        this.image=img;
        this.setWidth(img.getWidth());
        this.setHeight(img.getHeight());
    }

    /**
     * Set the opacity of the canvas on the specific layer
     * @param model The current model being modified
     * @param opacity This is the amount of the opacity
     */
    public void setOpacity(PaintModel model, double opacity){
        String layer = "1";
        for (Map.Entry<String, PaintModel> tempLayer : modelLayers.entrySet()) {
            if (tempLayer.getValue().equals(model)) {
                layer = tempLayer.getKey();
                break;
            }
        }
        opacityLayers.put(layer, opacity/100.0);
    }

    /**
     * @return the current drawing mode
     */
    public String getMode(){return this.mode; }

    /**
     * Set the color of the current shape
     * @param color the color requested by the user
     */
    public void setColor(javafx.scene.paint.Color color){
        this.color=color;
        controller.setColor(color);
    }

    /**
     * @param num layer number
     * @return returns the opacity or a specified layer. Given a layer number
     */
    public double getOpacity(int num){
        return opacityLayers.get("" +num);
    }

    /**
     * @param g2d the graphics context
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @return return he color at the specified coordinates.
     */
    public Color getColorAt(GraphicsContext g2d, int x, int y){
        WritableImage image = new WritableImage((int)this.getWidth(), (int)this.getHeight());
        g2d.getCanvas().snapshot(null, image);
        return image.getPixelReader().getColor(x, y);
    }

    /**
     * Set the lineThickness
     * @param lineThick The users requested lineThickness
     */
    public void updateLineThickness(int lineThick){
        this.lineThickness = lineThick;
    }

    /**
     * @return the lineThickness
     */
    public Integer getLineThickness(){return lineThickness;}

    /**
     * @return the graphics context
     */
    public GraphicsContext getG2d() {
        return g2d;
    }

    /**
     * @return the list of shapes drawn on the canvas
     */
    public ArrayList<Object> getShapes(){return model.getShapes();}

    /**
     * @return a list of the different actions that are done by the user
     */
    public ActionArray getActionArray() {return actionArray;}

    /**
     * Set the layer of the canvas based on the users request
     * @param layer the requested layer
     */
    public void setLayer(int layer) {
        this.model = modelLayers.get( "" + layer);
        controller.setModel(model);
        layerNumber = layer;
    }
    public PaintModel getLayer(){
        return model;
    }

    /**
     * Add a layer to the canvas
     * @param model The model to which a layer is being added to
     * @param layer The layer number being added to the canvas
     */
    public void addLayer(PaintModel model, int layer) {
        model.addObserver(this);
        modelLayers.put(""+layer,model);
        opacityLayers.put(""+layer,1.0);
        visibleLayers.add(layer);

    }

    /**
     * Reveal the layer on the canvas
     * @param layer The layer number being revealed on the canvas
     */
    public void revealLayer(int layer) {
        boolean added = false;
        for (int i = 0; i < visibleLayers.size(); i++) {
            if( layer < visibleLayers.get(i) ) {
                visibleLayers.add(i, layer);
                added = true;
                break;
            }
        }
        if (!added) {
            visibleLayers.add(layer);
        }
    }
    /**
     * @return the SelectStrategy
     */
    public SelectStrategy getSelectStrategy(){
        return selectStrategy;
    }

    /**
     * @return the IsoSelectStrategy
     */
    public IsoSelectStrategy getIsoSelectStrategy(){
        return isoStrategy;
    }

    /**
     * Hide the layer on the canvas
     * @param layerNumber The layer number being hid on the canvas
     */
    public void hideLayer(int layerNumber) {

        visibleLayers.remove((Integer) layerNumber);
    }

    /**
     * @return the color of the shape
     */
    public javafx.scene.paint.Color getColor(){return this.color;}

    /**
     * set the layer icons
     * @param buttons list of buttons
     */
    public void setLayerIcons(ArrayList<MenuButton> buttons) {
        menuButtons = buttons;
    }


    /**
     * @return the layers on the model
     */
   public HashMap<String, PaintModel> getTempModel() {return modelLayers;}

    /**
     * Update the canvas
     * @param o the observable class
     * @param arg the update being made in the canvas
     */
    @Override
    public void update(Observable o, Object arg) {
        GraphicsContext hiddenG2d = hiddenCanvas.getGraphicsContext2D();

        hiddenG2d.getCanvas().setHeight(this.getHeight());
        hiddenG2d.getCanvas().setWidth(this.getWidth());
        g2d = this.getGraphicsContext2D();
        g2d.clearRect(0, 0, this.getWidth(), this.getHeight());
        g2d.setGlobalAlpha(opacityLayers.get("1"));
        g2d.setFill(Color.WHITE);
        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
        if (image != null) {g2d.drawImage(image, 0, 0, this.getWidth(), this.getHeight());}
        for (int layer : visibleLayers) {
            PaintModel modelTemp = modelLayers.get("" + layer);
            double opacity = opacityLayers.get("" + layer);
            ArrayList<Object> lisShapes = modelTemp.getShapes();
            for (Object shape: lisShapes){
                Shape sumshape = (Shape) shape;
                if (!sumshape.isVisible()){
                    continue;
                }
                ArrayList array = new ArrayList<>();
                array.add(hiddenG2d);
                array.add(shape);
                array.add(modelTemp);
                sumshape.print(array);
                }
            WritableImage layerImage = new WritableImage((int) this.getWidth(), (int)this.getHeight());
            if (layer != 1 || image != null) {
                SnapshotParameters param = new SnapshotParameters();
                param.setFill(Color.TRANSPARENT);
                hiddenCanvas.snapshot(param, layerImage);
            }
            else {
                hiddenCanvas.snapshot(null, layerImage);
            }
            g2d.setGlobalAlpha(opacity);
            g2d.drawImage(layerImage, 0, 0);
            ImageView imageView = new ImageView(layerImage);
            imageView.setFitWidth(30);
            imageView.setFitHeight(30);
            menuButtons.get(layer-1).setGraphic(imageView);
            hiddenG2d.clearRect(0, 0, layerImage.getWidth(), layerImage.getHeight());

            }
        }
    }

