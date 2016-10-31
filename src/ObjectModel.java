import java.util.ArrayList;

/**
 * Created by samz on 2016-10-30.
 */
public final class ObjectModel {
    private final ArrayList rects;
    private final ArrayList trigs;
    private final ArrayList lines;
    private final ArrayList ovals;

    public ObjectModel(ArrayList rects, ArrayList trigs, ArrayList lines, ArrayList ovals) {
        this.rects = rects;
        this.trigs = trigs;
        this.lines = lines;
        this.ovals = ovals;
    }

    public ArrayList getRects() {
        return rects;
    }

    public ArrayList getTrigs() {
        return trigs;
    }

    public ArrayList getLines() {
        return lines;
    }

    public ArrayList getOvals() {
        return ovals;
    }

    public ColoredRect getActiveRect() {
        if (getActiveRectIndex() < 0) return null;

        return (ColoredRect) rects.get(getActiveRectIndex());
    }
    public int getActiveRectIndex() {
        for (int i = 0; i < rects.size(); i++) {
            ColoredRect r = (ColoredRect) rects.get(i);
            if (r.isActive()) return i;
        }

        return -1;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!this.getClass().equals(obj.getClass())) return false;

        ObjectModel obj2 = (ObjectModel) obj;
        if (
                deepEqual(this.rects, obj2.rects) &&
                deepEqual(this.trigs, obj2.trigs) &&
                deepEqual(this.lines, obj2.lines) &&
                deepEqual(this.ovals, obj2.ovals)
        )
            return true;
        else return false;
    }

    public boolean deepEqual(ArrayList list1, ArrayList list2) {
        if (list1.size() != list2.size()) return false;
        for (Object item1 : list1) {
            for (Object item2 : list2) {
                if (!item1.equals(item2)) return false;
            }
        }
        return true;
    }
}
