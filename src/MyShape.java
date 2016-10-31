import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.UUID;

/**
 * Created by samz on 2016-10-30.
 */
public class MyShape implements Shape {
    private Color color;
    private boolean isActive;
    private final UUID id = UUID.randomUUID();

    public MyShape(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isActive() {
        return isActive;
    }
    public void setActive() {
        isActive = true;
    }
    public void setInactive() {
        isActive = false;
    }

    public UUID getId() {
        return id;
    }

    public void setLocation(Point p) {
        AffineTransform at = new AffineTransform();
        at.translate(p.getX(), p.getY());
    }

    public int getX() {
        return (int) this.getBounds().getX();
    }

    public int getY() {
        return (int) this.getBounds().getY();
    }

    public void draw(Graphics g) {
        if (g != null) {
            g.setColor(color);
        }
    }

    @Override
    public Rectangle getBounds() {
        return null;
    }

    @Override
    public Rectangle2D getBounds2D() {
        return null;
    }

    @Override
    public boolean contains(double x, double y) {
        return false;
    }

    @Override
    public boolean contains(Point2D p) {
        return false;
    }

    @Override
    public boolean intersects(double x, double y, double w, double h) {
        return false;
    }

    @Override
    public boolean intersects(Rectangle2D r) {
        return false;
    }

    @Override
    public boolean contains(double x, double y, double w, double h) {
        return false;
    }

    @Override
    public boolean contains(Rectangle2D r) {
        return false;
    }

    @Override
    public PathIterator getPathIterator(AffineTransform at) {
        return null;
    }

    @Override
    public PathIterator getPathIterator(AffineTransform at, double flatness) {
        return null;
    }
}
