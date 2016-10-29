import java.util.ArrayList;
import java.util.List;

/**
 * Created by samz on 2016-10-29.
 */
public class State {
    private static State defaultInstance;
    private List<Subscriber> subscribers;

    public static State getInstance() {
        if (defaultInstance == null) {
            defaultInstance = new State();
        }
        return defaultInstance;
    }

    private State() {
        subscribers = new ArrayList();
    }

    public void dispatch(Event event) {
        for (Subscriber subscriber : subscribers) {
            subscriber.onEvent(event);
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
        void onEvent(Event event);
    }
}
