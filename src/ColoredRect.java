import java.awt.*;

/**
 * Created by samz on 2016-10-30.
 */
public class ColoredRect extends MyShape {
    public ColoredRect(Color color) {
        super(color);
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!this.getClass().equals(obj.getClass())) return false;

        ColoredRect obj2 = (ColoredRect) obj;
        if (this.getId().equals(obj2.getId())) return false;

        if (this.getColor().equals(obj2.getColor()) && this.isActive() == obj2.isActive()) return true;
        else return false;
    }

    public String toString() {
        return "ID: " + this.getId() + ", Background: " + this.getColor() + ", isActive: " + this.isActive();
    }

    public void draw(Graphics g) {
        super.draw(g);

        if (g == null) return;

        Rectangle bounds = this.getBounds();
        g.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
        g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);

        if (isActive()) {
            g.setColor(Constants.SELECTION_COLOR);
            ((Graphics2D) g).setStroke(new BasicStroke(Constants.SELECTION_STROKE_WIDTH));
            g.drawRect(
                    bounds.x-Constants.SELECTION_STROKE_WIDTH,
                    bounds.y-Constants.SELECTION_STROKE_WIDTH,
                    bounds.width+Constants.SELECTION_STROKE_WIDTH*2,
                    bounds.height+Constants.SELECTION_STROKE_WIDTH*2
            );
            ((Graphics2D) g).setStroke(new BasicStroke(1));
        }
    }

    public ColoredRect clone() {
        return new ColoredRect(this.getColor());
    }
}
