import java.awt.*;
import java.util.UUID;

/**
 * Created by samz on 2016-10-30.
 */
public class ColoredRect extends Rectangle {
    private Color background;
    private boolean isActive;
    private UUID id = UUID.randomUUID();

    public ColoredRect(Color background) {
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

    public UUID getId() {
        return id;
    }

    public Boolean isIn(Point point) {
        return this.contains(point.x, point.y);
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!this.getClass().equals(obj.getClass())) return false;

        ColoredRect obj2 = (ColoredRect) obj;
        if (this.id.equals(obj2.getId())) return false;

        if (this.background.equals(obj2.getBackground()) && this.isActive == obj2.isActive()) return true;
        else return false;
    }

    public String toString() {
        return "ID: " + this.id + ", Background: " + this.background + ", isActive: " + this.isActive;
    }
}
