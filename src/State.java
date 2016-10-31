import java.util.ArrayList;
import java.util.List;

/**
 * Created by samz on 2016-10-29.
 */
public class State {
    private static State defaultInstance;
    private List<Subscriber> subscribers;
    private AppState state = new AppState(
            new ArrayList<MyShape>(),
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

        ArrayList shapes = (ArrayList) state.getShapes().clone();
        int activeShapeIndex = state.getActiveShapeIndex();

        switch (event.type) {
            case RESET_CANVAS:
                shapes.clear();

                state = new AppState(shapes, state.getActiveColor(), state.getActiveMode(), null);
                break;
            case SET_ACTIVE_COLOR:
                Constants.COLORS activeColor = (Constants.COLORS) payload;
                state = new AppState(state.getShapes(), activeColor, state.getActiveMode(), state.getActiveShapeId());
                break;
            case SET_ACTIVE_MODE:
                Constants.MODES activeMode = (Constants.MODES) payload;
                state = new AppState(state.getShapes(), state.getActiveColor(), activeMode, state.getActiveShapeId());
                break;
            case ADD_SHAPE:
                for (Object shape : shapes) {
                    ((MyShape) shape).setInactive();
                }

                shapes.add(payload);
                state = new AppState(shapes, state.getActiveColor(), state.getActiveMode(), null);
                break;
            case UPDATE_SHAPE:
                if (payload == null) {
                    for (Object shape : shapes) {
                        ((MyShape) shape).setInactive();
                    }
                    state = new AppState(shapes, state.getActiveColor(), state.getActiveMode(), null);
                    break;
                }

                MyShape payloadShape = (MyShape) payload;
                int selectedShapeIndex = -1;
                MyShape activeShape = null;

                for (int i = 0; i < shapes.size(); i++) {
                    MyShape shape = (MyShape) shapes.get(i);
                    shape.setInactive();

                    if (shape.getId() == payloadShape.getId()) {
                        selectedShapeIndex = i;
                    }
                }

                if (selectedShapeIndex > -1) {
                    shapes.set(selectedShapeIndex, payload);
                    activeShape = (MyShape) shapes.get(selectedShapeIndex);
                }

                state = new AppState(shapes, state.getActiveColor(), state.getActiveMode(), activeShape.getId());
                break;
            case CHANGE_COLOR:
                if (activeShapeIndex < 0) return;

                shapes.set(activeShapeIndex, payload);

                state = new AppState(shapes, state.getActiveColor(), state.getActiveMode(), state.getActiveShapeId());
                break;
            case DELETE_OBJECT:
                if (activeShapeIndex < 0) return;

                shapes.remove(activeShapeIndex);

                state = new AppState(shapes, state.getActiveColor(), state.getActiveMode(), state.getActiveShapeId());
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
