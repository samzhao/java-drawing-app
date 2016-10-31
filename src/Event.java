/**
 * Created by samz on 2016-10-29.
 */
public class Event {
    public final Constants.EVENTS type;
    public final Object payload;

    public Event(Constants.EVENTS type) {
        this.type = type;
        this.payload = null;
    }

    public Event(Constants.EVENTS type, Object payload) {
        this.type = type;
        this.payload = payload;
    }

    public String toString() {
        return ""+this.type+this.payload;
    }
}
