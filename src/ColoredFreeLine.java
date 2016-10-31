import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;

/**
 * Created by samz on 2016-10-31.
 */
public class ColoredFreeLine extends MyShape {
    private ArrayList<Line2D> lines;
    private Point lastPoint;
    private int translateX;
    private int translateY;

    public ColoredFreeLine(Color color) {
        super(color);
        lines = new ArrayList<>();
        lastPoint = null;
        translateX = 0;
        translateY = 0;
    }

    public void draw(Graphics2D g) {
        super.draw(g);

        if (g == null) return;

        if (isActive()) {
            g.setColor(Constants.SELECTION_COLOR);
            g.setStroke(Constants.LINE_SELECTION_STROKE);

            for (Line2D line : lines) {
                g.draw(line);
            }

            g.setColor(this.getColor());
            g.setStroke(Constants.LINE_STROKE);

            for (Line2D line : lines) {
                g.draw(line);
            }
        } else {
            g.setColor(this.getColor());
            g.setStroke(Constants.LINE_STROKE);

            for (Line2D line : lines) {
                g.draw(line);
            }
        }

        g.setStroke(Constants.DEFAULT_STROKE);
    }

    public boolean contains(Point p) {
        for (Line2D line : lines) {
            if (Math.floor(line.ptSegDist(p)) < 8) {
                return true;
            }
        }
        return false;
    }

    public int getX() {
        if (lines.size() < 1) return 0;
        return (int) lines.get(0).getX1();
    }
    public int getY() {
        if (lines.size() < 1) return 0;
        return (int) lines.get(0).getY1();
    }
    public void setLocation(Point p) {
        int dx = p.x - translateX;
        int dy = p.y - translateY;

        for (Line2D line : lines) {
            Line2D newLine = new Line2D.Double(line.getX1()+dx, line.getY1()+dy, line.getX2()+dx, line.getY2()+dy);
            line.setLine(newLine);
        }

        translateX = p.x;
        translateY = p.y;
    }
    public void setBounds(Point startPoint, Point p) {
        if (this.lastPoint == null) {
            this.lastPoint = startPoint;
            translateX = startPoint.x;
            translateY = startPoint.y;
        }

        Line2D line = new Line2D.Double(lastPoint, p);
        lines.add(line);

        this.lastPoint = p;
    }
}
