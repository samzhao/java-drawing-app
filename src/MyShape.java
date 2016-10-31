import java.awt.*;
import java.util.UUID;

/**
 * Created by samz on 2016-10-30.
 */
public abstract class MyShape implements Shape {
    private Graphics g;
    private Color color;
    private boolean isActive;
    private final UUID id = UUID.randomUUID();

    public MyShape(Graphics g, Color color) {
        this.g = g;
        this.color = color;
    }

    public void draw() {
        g.setColor(color);
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
}
