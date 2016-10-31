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

    private MyShape tempShape;

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
            if (shape != null) shape.draw(g);
        }

        if (tempShape != null) {
            tempShape.draw(g2d);
        }
    }

    class MyMouseListener extends MouseInputAdapter {
        private Point startPoint;
        private MyShape selectedShape = null;
        private int lastX;
        private int lastY;

        public void mousePressed(MouseEvent e) {
            startPoint = e.getPoint();
            Color activeColor = appState.getActiveColor().getColor();

            switch (appState.getActiveMode()) {
                case RECT:
                    tempShape = new ColoredRect(activeColor);
                    break;
                case TRIG:
                    tempShape = new ColoredTriangle(activeColor);
                    break;
                case LINE:
                    break;
                case OVAL:
                    break;
                case EDIT:
                    selectedShape = null;
                    appState.getShapes().stream().filter(shape -> shape.contains(startPoint)).forEach(shape -> selectedShape = shape.clone());

                    if (selectedShape != null) {
                        selectedShape.setActive();
                        lastX = selectedShape.getX() - e.getX();
                        lastY = selectedShape.getY() - e.getY();
                    }

                    Event event = new Event(Constants.EVENTS.UPDATE_SHAPE, selectedShape);
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
                if (selectedShape == null) return;

                selectedShape = selectedShape.clone();
                Point p = new Point(e.getX() + lastX, e.getY() + lastY);
                selectedShape.setLocation(p);

                Event event = new Event(Constants.EVENTS.UPDATE_SHAPE, selectedShape);
                state.dispatch(event);

                return;
            }

            switch (appState.getActiveMode()) {
                case RECT:
                    ((ColoredRect) tempShape).setBounds(x, y, width, height);
                    break;
                case TRIG:
                    ((ColoredTriangle) tempShape).setBounds(startPoint, e.getPoint());
                    break;
                case LINE:
                    break;
                case OVAL:
                    break;
            }
            repaint();
        }

        public void mouseReleased(MouseEvent e) {
            int width = Math.abs(startPoint.x - e.getX());
            int height = Math.abs(startPoint.y - e.getY());

            if (width == 0 || height == 0) {
                tempShape = null;
                return;
            }

            if (tempShape == null) return;

            switch (appState.getActiveMode()) {
                case RECT:
                case TRIG:
                case LINE:
                case OVAL:
                default:
                    Event event = new Event(Constants.EVENTS.ADD_SHAPE, tempShape);
                    state.dispatch(event);
                    break;
            }

            tempShape = null;
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
