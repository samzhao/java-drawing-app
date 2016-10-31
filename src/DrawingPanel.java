import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.TriangleMesh;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Created by samz on 2016-10-29.
 * Heavily influenced by https://tips4java.wordpress.com/2009/05/08/custom-painting-approaches/
 */
public class DrawingPanel extends JPanel {
    State state = State.getInstance();
    AppState appState = state.getState();

    private static final int width = 600;
    private static final int height = 500;

    private Shape currentShape;

    public DrawingPanel() {
        setBackground(Color.WHITE);

        MyMouseListener ml = new MyMouseListener();
        addMouseListener(ml);
        addMouseMotionListener(ml);

        state.subscribe(onStateChange());
        update(appState);
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

        for (Object rect : appState.getObjects().getRects()) {
            ColoredRect r = ((ColoredRect) rect);
            g.setColor(r.getBackground());
            g.drawRect(r.x, r.y, r.width, r.height);
            g.fillRect(r.x, r.y, r.width, r.height);

            if (r.isActive()) {
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

    class MyMouseListener extends MouseInputAdapter {
        private Point startPoint;
        private ColoredRect selectedRect = null;
        private int lastX;
        private int lastY;

        public void mousePressed(MouseEvent e) {
            startPoint = e.getPoint();

            switch (appState.getActiveMode()) {
                case RECT:
                    currentShape = new ColoredRect(e.getComponent().getForeground());
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
                    for (Object rect : appState.getObjects().getRects()) {
                        if (((ColoredRect) rect).isIn(startPoint)) {
                            selectedRect = (ColoredRect) ((ColoredRect) rect).clone();
                        }
                    }

                    if (selectedRect != null) {
                        selectedRect.setActive();
                        lastX = selectedRect.x - e.getX();
                        lastY = selectedRect.y - e.getY();
                    }

                    Event event = new Event(Constants.EVENTS.UPDATE_RECT, selectedRect);
                    state.dispatch(event);
                    break;
            }
        }

        public void mouseDragged(MouseEvent e) {
            int x = Math.min(startPoint.x, e.getX());
            int y = Math.min(startPoint.y, e.getY());
            int width = Math.abs(startPoint.x - e.getX());
            int height = Math.abs(startPoint.y - e.getY());

            if (appState.getActiveMode() == Constants.MODES.EDIT) {
                if (selectedRect == null) return;

                int dx = (int) (startPoint.x - selectedRect.getX());
                int dy = (int) (startPoint.y - selectedRect.getY());

                selectedRect = (ColoredRect) selectedRect.clone();
                Point p = new Point(e.getX() + lastX, e.getY() + lastY);
                selectedRect.setLocation(p);

                Event event = new Event(Constants.EVENTS.UPDATE_RECT, selectedRect);
                state.dispatch(event);

                return;
            }

            switch (appState.getActiveMode()) {
                case RECT:
                    ((ColoredRect) currentShape).setBounds(x, y, width, height);
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
            repaint();
        }

        public void mouseReleased(MouseEvent e) {
            int width = Math.abs(startPoint.x - e.getX());
            int height = Math.abs(startPoint.y - e.getY());

            if (width == 0 || height == 0) {
                currentShape = null;
                return;
            }

            switch (appState.getActiveMode()) {
                case RECT:
                    Event event = new Event(Constants.EVENTS.ADD_RECT, currentShape);
                    state.dispatch(event);
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

    private State.Subscriber onStateChange() {
        return newState -> {
            if (appState.equals(newState)) return;

            System.out.println("CHANGE");

            update(newState);
        };
    }

    private void update(AppState newState) {
        setForeground(newState.getActiveColor().getColor());
        appState = newState;

        this.repaint();
    }
}
