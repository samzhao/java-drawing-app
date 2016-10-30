import java.awt.*;

/**
 * Created by samz on 2016-10-30.
 */
public class ColoredRect {
    private Color background;
    private Rectangle rect;

    public ColoredRect(Rectangle rect, Color background) {
        this.rect = rect;
        this.background = background;
    }

    public void setBackground(Color background) {
        this.background = background;
    }

    public Color getBackground() {

        return background;
    }

    public Rectangle getRect() {
        return rect;
    }
}
