import java.awt.*;

/**
 * Created by samz on 2016-10-30.
 */
public class ColoredRect extends Rectangle {
    private Color background;
    private Rectangle rect;
    private boolean isActive;
    private int x;
    private int y;

    public ColoredRect(Rectangle rect, Color background) {
        this.rect = rect;
        this.background = background;
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

    public void setBackground(Color background) {
        this.background = background;
    }

    public Color getBackground() {
        return background;
    }

    public void setLocation(int x, int y) {
        rect.setLocation(x, y);
    }

    public Boolean isIn(Point point) {
        return rect.contains(point.x, point.y);
    }

    public Rectangle getRect() {
        return rect;
    }
}
