import java.awt.*;

/**
 * Created by samz on 2016-10-30.
 */
public class ColoredTriangle extends MyShape {
    private Polygon shape;
    private Point center;
    private Point p1;
    private Point p2;
    private Point p3;

    public ColoredTriangle(Color color) {
        super(color);
        shape = new Polygon();
    }

    public void draw(Graphics2D g) {
        super.draw(g);

        if (g == null || center == null || p1 == null) return;

        g.drawPolygon(this.shape);
        g.fillPolygon(this.shape);

        if (isActive()) {
            g.setColor(Constants.SELECTION_COLOR);
            g.setStroke(Constants.SELECTION_STROKE);
            g.drawPolygon(this.shape);
            g.setStroke(Constants.DEFAULT_STROKE);
        }
    }

    private Point[] generatePoints(Point center, Point first) {
        // Reference: https://github.com/doreen-portfolio/Java-RubberBandEquilateralTriangle

        double m = Math.sqrt(Math.pow((first.x - center.x), 2) + Math.pow((first.y - center.y), 2));
        double a1 = Math.atan2((first.y - center.y), (first.x - center.x));
        double a2 = a1 + 2 * Math.PI / Integer.parseInt("3");
        double a3 = a2 + 2 * Math.PI / Integer.parseInt("3");
        Point p2 = new Point(center.x + (int) (m * Math.cos(a2)), center.y
                + (int) (m * Math.sin(a2)));
        Point p3 = new Point(center.x + (int) (m * Math.cos(a3)), center.y
                + (int) (m * Math.sin(a3)));

        return new Point[] {p2, p3};
    }
    private void update(Point newCenter) {
        if (center == null || newCenter == null) return;

        int dx = newCenter.x - center.x;
        int dy = newCenter.y - center.y;

        this.shape.translate(dx, dy);
    }
    private void updateShape() {
        this.shape.reset();
        this.shape.addPoint(p1.x, p1.y);
        this.shape.addPoint(p2.x, p2.y);
        this.shape.addPoint(p3.x, p3.y);
    }

    public boolean contains(Point p) {
        return this.shape.contains(p);
    }

    public int getX() {
        return center.x;
    }
    public int getY() {
        return center.y;
    }
    public void setLocation(Point center) {
        update(center);

        this.center = center;
    }
    public void setBounds(Point center, Point p1) {
        this.center = center;
        this.p1 = p1;

        p2 = generatePoints(center, p1)[0];
        p3 = generatePoints(center, p1)[1];

        updateShape();
    }
}
