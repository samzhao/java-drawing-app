import java.awt.*;
import java.awt.geom.Line2D;

/**
 * Created by samz on 2016-10-31.
 */
public class ColoredLine extends MyShape {
    private Line2D shape;
    private int translateX;
    private int translateY;

    public ColoredLine(Color color) {
        super(color);
        shape = new Line2D.Double();
        translateX = 0;
        translateY = 0;
    }

    public void draw(Graphics2D g) {
        super.draw(g);

        if (g == null) return;

        if (isActive()) {
            g.setColor(Constants.SELECTION_COLOR);
            g.setStroke(Constants.LINE_SELECTION_STROKE);

            g.draw(shape);

            g.setColor(this.getColor());
            g.setStroke(Constants.LINE_STROKE);

            g.draw(shape);
        } else {
            g.setColor(this.getColor());
            g.setStroke(Constants.LINE_STROKE);

            g.draw(shape);
        }

        g.setStroke(Constants.DEFAULT_STROKE);
    }

    public boolean contains(Point p) {
        return shape.getBounds().contains(p);
    }
    public int getX() {
        return (int) shape.getX1();
    }
    public int getY() {
        return (int) shape.getY1();
    }

    public void setLocation(Point p) {
        int dx = p.x - translateX;
        int dy = p.y - translateY;

        shape.setLine(shape.getX1()+dx, shape.getY1()+dy, shape.getX2()+dx, shape.getY2()+dy);

        translateX = p.x;
        translateY = p.y;
    }
    public void setBounds(Point startPoint, Point endPoint) {
        shape.setLine(startPoint, endPoint);

        translateX = startPoint.x;
        translateY = startPoint.y;
    }
}
