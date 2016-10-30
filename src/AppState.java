import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by samz on 2016-10-29.
 */
public class AppState {
    private static AppState defaultInstance;
    private List<Subscriber> subscribers;
    private Map<String, Object> defaultObjects = new HashMap() {{
        put("RECTS", new ArrayList<>());
        put("TRIGS", new ArrayList<>());
        put("LINES", new ArrayList<>());
        put("OVALS", new ArrayList<>());
    }};
    private Map<String, Object> state = new HashMap(){{
        put("OBJECTS", defaultObjects);
        put("ACTIVE_COLOR", Constants.COLORS.values()[0]);
        put("ACTIVE_MODE", Constants.MODES.FREEFORM);
    }};

    public static AppState getInstance() {
        if (defaultInstance == null) {
            defaultInstance = new AppState();
        }
        return defaultInstance;
    }

    private AppState() {
        subscribers = new ArrayList();
    }

    public void dispatch(Event event) {
        updateState(event);

        for (Subscriber subscriber : subscribers) {
            subscriber.onEvent(state);
        }
    }

    private void updateState(Event event) {
        switch (event.type) {
            case RESET_CANVAS:
                Map objects = (Map) state.get("OBJECTS");
                ArrayList rects = (ArrayList) objects.get("RECTS");
                ArrayList trigs = (ArrayList) objects.get("TRIGS");
                ArrayList lines = (ArrayList) objects.get("LINES");
                ArrayList ovals = (ArrayList) objects.get("OVALS");

                rects.clear();
                trigs.clear();
                lines.clear();
                ovals.clear();
                break;
            case SET_ACTIVE_COLOR:
                state.put("ACTIVE_COLOR", event.payload);
                break;
            case SET_ACTIVE_MODE:
                state.put("ACTIVE_MODE", event.payload);
                break;
            case ADD_RECT:
                objects = (Map) state.get("OBJECTS");
                rects = (ArrayList) objects.get("RECTS");
                rects.add(event.payload);
                break;
        }
    }

    public void subscribe(Subscriber subscriber) {
        if (subscribers.contains(subscriber)) return;
        subscribers.add(subscriber);
    }

    public void unsubscribe(Subscriber subscriber) {
        if (!subscribers.contains(subscriber)) return;
        subscribers.remove(subscriber);
    }

    public interface Subscriber {
        void onEvent(Map state);
    }

    public Map getState() {
        return this.state;
    }
}
