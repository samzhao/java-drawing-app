/**
 * Created by samz on 2016-10-30.
 */
public final class AppState {
    private final ObjectModel objects;

    private final Object activeObject;
    private final Constants.COLORS activeColor;
    private final Constants.MODES activeMode;

    public AppState(ObjectModel objects, Constants.COLORS activeColor, Constants.MODES activeMode, Object activeObject) {
        this.objects = objects;
        this.activeColor = activeColor;
        this.activeMode = activeMode;
        this.activeObject = activeObject;
    }

    public ObjectModel getObjects() {
        return objects;
    }

    public Constants.COLORS getActiveColor() {
        return activeColor;
    }

    public Constants.MODES getActiveMode() {
        return activeMode;
    }

    public Object getActiveObject() { return activeObject; }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!this.getClass().equals(obj.getClass())) return false;

        AppState obj2 = (AppState) obj;
        if (
                this.activeColor.equals(obj2.activeColor) &&
                this.activeMode.equals(obj2.activeMode) &&
                this.objects.equals(obj2.objects)
        ) return true;
        else return false;
    }
}
