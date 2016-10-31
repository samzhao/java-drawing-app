import java.util.ArrayList;
import java.util.List;

/**
 * Created by samz on 2016-10-29.
 */
public class State {
    private static State defaultInstance;
    private List<Subscriber> subscribers;
    private ObjectModel defaultObjects = new ObjectModel(
            new ArrayList(),
            new ArrayList(),
            new ArrayList(),
            new ArrayList()
    );
    private AppState state = new AppState(
            defaultObjects,
            Constants.COLORS.values()[0],
            Constants.MODES.values()[0],
            null
    );

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
        updateState(event);

        for (Subscriber subscriber : subscribers) {
            subscriber.onEvent(state);
        }
    }

    private void updateState(Event event) {
        Object payload = event.payload;

        ObjectModel objects = state.getObjects();
        ArrayList rects = (ArrayList) objects.getRects().clone();
        ArrayList trigs = (ArrayList) objects.getTrigs().clone();
        ArrayList lines = (ArrayList) objects.getLines().clone();
        ArrayList ovals = (ArrayList) objects.getOvals().clone();

        switch (event.type) {
            case RESET_CANVAS:
                rects.clear();
                trigs.clear();
                lines.clear();
                ovals.clear();

                ObjectModel newObjects = new ObjectModel(rects, trigs, lines, ovals);
                state = new AppState(newObjects, state.getActiveColor(), state.getActiveMode(), null);
                break;
            case SET_ACTIVE_COLOR:
                Constants.COLORS activeColor = (Constants.COLORS) payload;
                state = new AppState(state.getObjects(), activeColor, state.getActiveMode(), state.getActiveObject());
                break;
            case SET_ACTIVE_MODE:
                Constants.MODES activeMode = (Constants.MODES) payload;
                state = new AppState(state.getObjects(), state.getActiveColor(), activeMode, state.getActiveObject());
                break;
            case ADD_RECT:
                rects.add(event.payload);

                newObjects = new ObjectModel(rects, trigs, lines, ovals);
                state = new AppState(newObjects, state.getActiveColor(), state.getActiveMode(), state.getActiveObject());
                break;
            case UPDATE_RECT:
                if (payload == null) {
                    for (Object rect : rects) {
                        ((ColoredRect) rect).setInactive();
                    }

                    break;
                }

                ColoredRect payloadRect = (ColoredRect) payload;
                int selectedRectIndex = -1;

                for(int i = 0; i < rects.size(); i++) {
                    ColoredRect rect = (ColoredRect) rects.get(i);
                    rect.setInactive();

                    if (rect.getId() == payloadRect.getId()) {
                        selectedRectIndex = i;
                    }
                }

                if (selectedRectIndex > -1) {
                    rects.set(selectedRectIndex, payload);
                }

                newObjects = new ObjectModel(rects, trigs, lines, ovals);
                state = new AppState(newObjects, state.getActiveColor(), state.getActiveMode(), state.getActiveObject());
                break;

            case CHANGE_COLOR:
                int activeRectIndex = objects.getActiveRectIndex();
                rects.set(activeRectIndex, payload);

                newObjects = new ObjectModel(rects, trigs, lines, ovals);
                state = new AppState(newObjects, state.getActiveColor(), state.getActiveMode(), state.getActiveObject());
                break;

            case DELETE_OBJECT:
                System.out.println("DELET ITEM");
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
        void onEvent(AppState state);
    }

    public AppState getState() {
        return this.state;
    }
}
