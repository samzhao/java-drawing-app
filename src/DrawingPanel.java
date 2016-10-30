import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.TriangleMesh;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by samz on 2016-10-29.
 * Heavily influenced by https://tips4java.wordpress.com/2009/05/08/custom-painting-approaches/
 */
public class DrawingPanel extends JPanel {
    AppState appState = AppState.getInstance();

    private static final int width = 600;
    private static final int height = 500;

    private Constants.MODES currentMode;
    private Shape currentShape;

    private ArrayList rects = new ArrayList();
    private ArrayList trigs = new ArrayList();
    private ArrayList lines = new ArrayList();
    private ArrayList ovals = new ArrayList();

    public DrawingPanel() {
        setBackground(Color.WHITE);

        MyMouseListener ml = new MyMouseListener();
        addMouseListener(ml);
        addMouseMotionListener(ml);

        appState.subscribe(onStateChange());
    }

    @Override
    public Dimension getPreferredSize() {
        return isPreferredSizeSet() ? super.getPreferredSize() : new Dimension(width, height);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Color foreground = g.getColor();
        Graphics2D g2d = (Graphics2D) g;
//
        for (Object rect : this.rects) {
            Rectangle r = ((ColoredRect) rect).getRect();
            g.setColor(((ColoredRect) rect).getBackground());
            g.drawRect(r.x, r.y, r.width, r.height);
            g.fillRect(r.x, r.y, r.width, r.height);

            if (((ColoredRect) rect).isActive()) {
                g.setColor(Constants.SELECTION_COLOR);
                ((Graphics2D) g).setStroke(new BasicStroke(Constants.SELECTION_STROKE_WIDTH));
                g.drawRect(
                        r.x-Constants.SELECTION_STROKE_WIDTH,
                        r.y-Constants.SELECTION_STROKE_WIDTH,
                        r.width+Constants.SELECTION_STROKE_WIDTH*2,
                        r.height+Constants.SELECTION_STROKE_WIDTH*2
                );
                ((Graphics2D) g).setStroke(new BasicStroke(1));
                g.setColor(foreground);
            }
        }

        if (currentShape != null) {
            g2d.setColor(foreground);
            g2d.draw(currentShape);
            g2d.fill(currentShape);
        }
    }

    private void addRect(Rectangle rect, Color color) {
        ColoredRect crect = new ColoredRect(rect, color);
        Event event = new Event(Constants.EVENTS.ADD_RECT, crect);
        appState.dispatch(event);
        repaint();
    }

    class MyMouseListener extends MouseInputAdapter {
        private Point startPoint;
        private ColoredRect selectedRect = null;

        public void mousePressed(MouseEvent e) {
            startPoint = e.getPoint();

            switch (currentMode) {
                case RECT:
                    currentShape = new Rectangle();
                    break;
                case TRIG:
                    currentShape = (Shape) new TriangleMesh();
                    break;
                case LINE:
                    currentShape = (Shape) new Line();
                    break;
                case OVAL:
                    currentShape = (Shape) new Circle();
                    break;
                case EDIT:
                    selectedRect = null;
                    for (Object rect : rects) {
                        if (((ColoredRect) rect).isIn(startPoint)) {
                            selectedRect = (ColoredRect) rect;
                        }
                    }

                    Event event = new Event(Constants.EVENTS.SET_ACTIVE_RECT, selectedRect);
                    appState.dispatch(event);
                    break;
            }
        }

        public void mouseDragged(MouseEvent e) {
            int x = Math.min(startPoint.x, e.getX());
            int y = Math.min(startPoint.y, e.getY());
            int width = Math.abs(startPoint.x - e.getX());
            int height = Math.abs(startPoint.y - e.getY());

            switch (currentMode) {
                case RECT:
                    ((Rectangle) currentShape).setBounds(x, y, width, height);
                    break;
                case TRIG:
                    currentShape = (Shape) new TriangleMesh();
                    break;
                case LINE:
                    currentShape = (Shape) new Line();
                    break;
                case OVAL:
                    currentShape = (Shape) new Circle();
                    break;
                case EDIT:
                    if (selectedRect == null) break;

                    int dx = (int) (e.getX() - selectedRect.getRect().getX());
                    int dy = (int) (e.getY() - selectedRect.getRect().getY());

                    selectedRect.setLocation(e.getX() - dx, e.getY() - dy);
                    Event event = new Event(Constants.EVENTS.UPDATE_RECT, selectedRect);
                    appState.dispatch(event);
                    break;
            }

            repaint();
        }

        public void mouseReleased(MouseEvent e) {
            int width = Math.abs(startPoint.x - e.getX());
            int height = Math.abs(startPoint.y - e.getY());

            if (width == 0 || height == 0) {
                currentShape = null;
                return;
            }

            switch (currentMode) {
                case RECT:
                    addRect((Rectangle) currentShape, e.getComponent().getForeground());
                    break;
                case TRIG:
                    currentShape = (Shape) new TriangleMesh();
                    break;
                case LINE:
                    currentShape = (Shape) new Line();
                    break;
                case OVAL:
                    currentShape = (Shape) new Circle();
                    break;
            }

            currentShape = null;
        }
    }

    private AppState.Subscriber onStateChange() {
        return state -> {
            this.setForeground(((Constants.COLORS) state.get("ACTIVE_COLOR")).getColor());
            this.currentMode = (Constants.MODES) state.get("ACTIVE_MODE");

            Map objects = (Map) state.get("OBJECTS");
            ArrayList rects = (ArrayList) objects.get("RECTS");
            ArrayList trigs = (ArrayList) objects.get("TRIGS");
            ArrayList lines = (ArrayList) objects.get("LINES");
            ArrayList ovals = (ArrayList) objects.get("OVALS");

            this.rects = rects;
            this.trigs = trigs;
            this.lines = lines;
            this.ovals = ovals;

            this.repaint();
        };
    }
}
