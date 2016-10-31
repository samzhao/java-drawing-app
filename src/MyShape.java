import java.awt.*;
import java.util.UUID;

/**
 * Created by samz on 2016-10-30.
 */
public class MyShape implements Cloneable {
    private Color color;
    private boolean isActive;
    private UUID id = UUID.randomUUID();
    private Shape shape;

    public MyShape(Color color) {
        this.color = color;
    }
    private MyShape(UUID id, Color color) {
        this.id = id;
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

    public boolean contains(Point p) {
        return false;
    }

    public void draw(Graphics g) {
        if (g != null) {
            g.setColor(color);
        }
    }

    public int getX() {
        return 0;
    }
    public int getY() {
        return 0;
    }

    public void setLocation(Point p) {}

    @Override
    protected MyShape clone() {
        try {
            return (MyShape) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return null;
    }
}
