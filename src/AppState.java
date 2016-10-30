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
    private Map<String, Object> state = new HashMap(){{
        put("ACTIVE_COLOR", Constants.COLORS.BLACK);
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
            subscriber.onEvent(event, state);
        }
    }

    private void updateState(Event event) {
        switch (event.type) {
            case RESET_CANVAS:
                break;
            case SET_ACTIVE_COLOR:
                state.put("ACTIVE_COLOR", event.payload);
                break;
            case SET_ACTIVE_MODE:
                state.put("ACTIVE_MODE", event.payload);
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
        void onEvent(Event event, Map state);
    }

    public Map getState() {
        return this.state;
    }
}
