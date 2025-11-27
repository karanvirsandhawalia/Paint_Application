package ca.utoronto.utm.assignment2.paint;

import java.util.ArrayList;
import java.util.Observable;
import java.util.List;
/**
 * Create lists of different shapes accessible in the canvas. Manage the various
 * shapes and objects that can be drawn on the canvas and state changes as the class
 * extends Observable
 *
 * @author arnold
 */
public class PaintModel extends Observable {
        private ArrayList<Point> points=new ArrayList<Point>();
        private ArrayList<Circle> circles=new ArrayList<Circle>();
        private ArrayList<Rectangle> rectangle =new ArrayList<Rectangle>();
        private ArrayList<ArrayList<Point>> squiggles= new ArrayList<ArrayList<Point>>();
        private ArrayList<javafx.scene.paint.Color> squiggleColors= new ArrayList<javafx.scene.paint.Color>();
        private ArrayList<javafx.scene.paint.Color> polyLineColors= new ArrayList<javafx.scene.paint.Color>();
        private ArrayList<Square> square =new ArrayList<Square>();
        private ArrayList<Oval> ovals = new ArrayList<Oval>();
        private ArrayList<Triangle> triangle= new ArrayList<Triangle>();
        private boolean filled = true;
        private ArrayList<Object> shapes = new ArrayList<>();
        private ArrayList<Polyline> polylines = new ArrayList<>();
        private Polyline latestPolyline = null;

        /**
         * Set the shaped to fill
         * @param filled fill in the shape
         */
        public void setFilled(boolean filled) {this.filled = filled;}

        /**
         * @return whether the shape is currently filled or not
         */
        public boolean getFilled() {return filled;}

        /**
         * Add a circle to the list of circles
         * @param c Circle
         */
        public void addCircle(Circle c){
                shapes.add(c);
                c.setFilled(filled);
                this.circles.add(c);
                this.setChanged();
                this.notifyObservers();
        }

        /**
         * Add a rectangle to the list of rectangles
         * @param r Rectangle
         */
        public void addRectangle(Rectangle r){
                shapes.add(r);
                r.setFilled(filled);
                this.rectangle.add(r);
                this.setChanged();
                this.notifyObservers();
        }

        /**
         * Add an oval to the list of ovals
         * @param o Oval
         */
        public void addOval (Oval o){
                shapes.add(o);
                o.setFilled(filled);
                this.ovals.add(o);
                this.setChanged();
                this.notifyObservers();
        }

        /**
         * Add a squiggle to the list of squiggles
         * @param s Squiggle
         */
        public void addSquiggle(Squiggle s){
                shapes.add(s);
                this.setChanged();
                this.notifyObservers();
        }

        /**
         * Add a color to the list of Polyline colors
         * @param color Color of the Polyline
         */
        public void addpolyLineColor(javafx.scene.paint.Color color){
                this.polyLineColors.add(color);
                this.setChanged();
                this.notifyObservers();
        }

        /**
         * Document the change to extended observable class
         */
        public void changed(){
                this.setChanged();
        }

        /**
         * Add a triangle to the list of triangles
         * @param t Triangle
         */
        public void addTriangle(Triangle t){
                shapes.add(t);
                t.setFilled(filled);
                this.triangle.add(t);
                this.setChanged();
                this.notifyObservers();
        }

        /**
         * Add a square to the list of squares
         * @param s Square
         */
        public void addSquare(Square s){
                s.setFilled(filled);
                this.square.add(s);
                shapes.add(s);
                this.setChanged();
                this.notifyObservers();
        }

        /**
         * Clear the current canvas of all its shapes
         */
        public void clearAll() {
                this.shapes.clear();
                this.setChanged();
                this.notifyObservers();
        }

        /**
         * @return an arraylist containing all the lists drawn on the canvas
         */
        public ArrayList<Object> getShapes(){return shapes;}

        public void setPolyline(ArrayList<Polyline> p) {this.polylines = p;}

        /**
         * Add a polyLine to the list of polyLines
         * @param p PolyLine
         */
        public void addPolyline(Polyline p){
                shapes.add(p);
                this.polylines.add(p);
                setChanged();
                notifyObservers();
        }

        /**
         * @return the latest polyLine drawn on the canvas
         */
        public Polyline getLatestPolyline(){return latestPolyline;}

        /**
         * set the latest polyLine drawn on the canvas
         * @param p PolyLine
         */
        public void setLatestPolyline(Polyline p){latestPolyline = p;}

        public ArrayList<Polyline> getPolylines(){return this.polylines;}
}
