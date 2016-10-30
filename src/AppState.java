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
        Map objects = (Map) state.get("OBJECTS");
        ArrayList rects = (ArrayList) objects.get("RECTS");
        ArrayList trigs = (ArrayList) objects.get("TRIGS");
        ArrayList lines = (ArrayList) objects.get("LINES");
        ArrayList ovals = (ArrayList) objects.get("OVALS");

        Object payload = event.payload;

        switch (event.type) {
            case RESET_CANVAS:
                rects.clear();
                trigs.clear();
                lines.clear();
                ovals.clear();
                break;
            case SET_ACTIVE_COLOR:
                state.put("ACTIVE_COLOR", payload);
                break;
            case SET_ACTIVE_MODE:
                state.put("ACTIVE_MODE", payload);
                break;
            case ADD_RECT:
                rects.add(event.payload);
                break;
            case SET_ACTIVE_RECT:
                int selectedRectIndex = -1;

                for(int i = 0; i < rects.size(); i++) {
                    ColoredRect rect = (ColoredRect) rects.get(i);
                    rect.setInactive();

                    if (rects.contains(payload)) {
                        if (rect.getRect() == ((ColoredRect) payload).getRect()) {
                            selectedRectIndex = i;
                        }
                    }
                }

                if (selectedRectIndex > -1) {
                    ColoredRect rect = (ColoredRect) rects.get(selectedRectIndex);
                    rect.setActive();
                }

                break;
            case UPDATE_RECT:
                ColoredRect selectedRect = null;

                for (Object rect : rects) {
                    if (((ColoredRect) rect).isActive()) {
                        selectedRect = (ColoredRect) rect;
                    }
                }

                if (selectedRect == null) break;

                selectedRect.setLocation(((ColoredRect) payload).getRect().getLocation());

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
