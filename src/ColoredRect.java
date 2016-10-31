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

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!this.getClass().equals(obj.getClass())) return false;

        ColoredRect obj2 = (ColoredRect) obj;
        if (this.getId().equals(obj2.getId())) return false;

        return this.getColor().equals(obj2.getColor()) && this.isActive() == obj2.isActive();
    }

    public void draw(Graphics g) {
        super.draw(g);

        if (g == null) return;

        g.drawRect(shape.x, shape.y, shape.width, shape.height);
        g.fillRect(shape.x, shape.y, shape.width, shape.height);

        if (isActive()) {
            g.setColor(Constants.SELECTION_COLOR);
            ((Graphics2D) g).setStroke(new BasicStroke(Constants.SELECTION_STROKE_WIDTH));
            g.drawRect(
                    shape.x-Constants.SELECTION_STROKE_WIDTH,
                    shape.y-Constants.SELECTION_STROKE_WIDTH,
                    shape.width+Constants.SELECTION_STROKE_WIDTH*2,
                    shape.height+Constants.SELECTION_STROKE_WIDTH*2
            );
            ((Graphics2D) g).setStroke(new BasicStroke(1));
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
