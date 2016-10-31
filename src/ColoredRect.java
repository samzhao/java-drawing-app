import java.awt.*;

/**
 * Created by samz on 2016-10-30.
 */
public class ColoredRect extends MyShape {
    private Rectangle shape;

    public ColoredRect(Color color) {
        super(color);
        shape = new Rectangle();
    }

    public void draw(Graphics2D g) {
        super.draw(g);

        if (g == null) return;

        g.drawRect(shape.x, shape.y, shape.width, shape.height);
        g.fillRect(shape.x, shape.y, shape.width, shape.height);

        if (isActive()) {
            g.setColor(Constants.SELECTION_COLOR);
            g.setStroke(Constants.SELECTION_STROKE);
            g.drawRect(shape.x, shape.y, shape.width, shape.height);
            g.setStroke(Constants.DEFAULT_STROKE);
        }
    }

    public boolean contains(Point p) {
        return this.shape.contains(p);
    }

    public int getX() {
        return (int) this.shape.getX();
    }
    public int getY() {
        return (int) this.shape.getY();
    }
    public void setLocation(Point p) {
        this.shape.setLocation(p);
    }
    public void setBounds(int x, int y, int width, int height) {
        this.shape.setBounds(x, y, width, height);
    }

    public String toString() {
        return "ID: " + this.getId() + ", Background: " + this.getColor() + ", isActive: " + this.isActive();
    }

    protected ColoredRect clone() {
        return (ColoredRect) super.clone();
    }
}
