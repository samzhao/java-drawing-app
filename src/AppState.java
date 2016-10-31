import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by samz on 2016-10-30.
 */
public final class AppState {
    private final ArrayList<MyShape> shapes;

    private final UUID activeShapeId;
    private final Constants.COLORS activeColor;
    private final Constants.MODES activeMode;

    public AppState(ArrayList<MyShape> shapes, Constants.COLORS activeColor, Constants.MODES activeMode, UUID activeShapeId) {
        this.shapes = shapes;
        this.activeColor = activeColor;
        this.activeMode = activeMode;
        this.activeShapeId = activeShapeId;
    }

    public ArrayList<MyShape> getShapes() {
        return shapes;
    }

    public Constants.COLORS getActiveColor() {
        return activeColor;
    }

    public Constants.MODES getActiveMode() {
        return activeMode;
    }

    public MyShape getActiveShape() {
        for (MyShape shape : shapes) {
            if (shape.getId() == activeShapeId) return shape;
        }

        return null;
    }

    public int getActiveShapeIndex() {
        for (int i = 0; i < shapes.size(); i++) {
            MyShape shape = shapes.get(i);
            if (shape.getId() == activeShapeId) return i;
        }

        return -1;
    }

    public UUID getActiveShapeId() {
        return activeShapeId;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!this.getClass().equals(obj.getClass())) return false;

        AppState obj2 = (AppState) obj;
        return this.activeColor.equals(obj2.activeColor) &&
                this.activeMode.equals(obj2.activeMode) &&
                deepEqual(this.shapes, obj2.shapes);
    }

    private boolean deepEqual(ArrayList list1, ArrayList list2) {
        if (list1.size() != list2.size()) return false;
        for (Object item1 : list1) {
            for (Object item2 : list2) {
                if (!item1.equals(item2)) return false;
            }
        }
        return true;
    }
}
