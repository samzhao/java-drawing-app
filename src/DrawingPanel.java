import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Created by samz on 2016-10-29.
 */
public class DrawingPanel extends JPanel {
    State state = State.getInstance();
    AppState appState = state.getState();

    private static final int width = 600;
    private static final int height = 500;

    private MyShape currentShape;

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

        Graphics2D g2d = (Graphics2D) g;

        for (MyShape shape : appState.getShapes()) {
            shape.draw(g);
        }

        if (currentShape != null) {
            currentShape.draw(g2d);
        }
    }

    class MyMouseListener extends MouseInputAdapter {
        private Point startPoint;
        private MyShape selectedRect = null;
        private int lastX;
        private int lastY;

        public void mousePressed(MouseEvent e) {
            startPoint = e.getPoint();

            switch (appState.getActiveMode()) {
                case RECT:
                    currentShape = new ColoredRect(appState.getActiveColor().getColor());
                    break;
                case TRIG:
//                    currentShape = (Shape) new TriangleMesh();
                    break;
                case LINE:
//                    currentShape = (Shape) new Line();
                    break;
                case OVAL:
//                    currentShape = (Shape) new Circle();
                    break;
                case EDIT:
                    selectedRect = null;
                    appState.getShapes().stream().filter(shape -> shape.contains(startPoint)).forEach(shape -> selectedRect = shape.clone());

                    if (selectedRect != null) {
                        selectedRect.setActive();
                        lastX = selectedRect.getX() - e.getX();
                        lastY = selectedRect.getY() - e.getY();
                    }

                    Event event = new Event(Constants.EVENTS.UPDATE_SHAPE, selectedRect);
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

                selectedRect = selectedRect.clone();
                Point p = new Point(e.getX() + lastX, e.getY() + lastY);
                selectedRect.setLocation(p);

                Event event = new Event(Constants.EVENTS.UPDATE_SHAPE, selectedRect);
                state.dispatch(event);

                return;
            }

            switch (appState.getActiveMode()) {
                case RECT:
                    ((ColoredRect) currentShape).setBounds(x, y, width, height);
                    break;
                case TRIG:
//                    currentShape = (Shape) new TriangleMesh();
                    break;
                case LINE:
//                    currentShape = (Shape) new Line();
                    break;
                case OVAL:
//                    currentShape = (Shape) new Circle();
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
                    Event event = new Event(Constants.EVENTS.ADD_SHAPE, currentShape);
                    state.dispatch(event);
                    break;
                case TRIG:
//                    currentShape = (Shape) new TriangleMesh();
                    break;
                case LINE:
//                    currentShape = (Shape) new Line();
                    break;
                case OVAL:
//                    currentShape = (Shape) new Circle();
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
        appState = newState;

        this.repaint();
    }
}
