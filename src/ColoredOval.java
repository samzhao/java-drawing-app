import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * Created by samz on 2016-10-31.
 */
public class ColoredOval extends MyShape {
    private Ellipse2D shape;

    public ColoredOval(Color color) {
        super(color);
        shape = new Ellipse2D.Double();
    }

    public void draw(Graphics2D g) {
        super.draw(g);

        if (g == null) return;

        g.draw(shape);
        g.fill(shape);

        if (isActive()) {
            g.setColor(Constants.SELECTION_COLOR);
            g.setStroke(Constants.SELECTION_STROKE);
            g.draw(shape);
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
        this.shape.setFrame(p.x, p.y, this.shape.getWidth(), this.shape.getHeight());
    }
    public void setBounds(int x, int y, int width, int height) {
        this.shape.setFrame(x, y, width, height);
    }
}
