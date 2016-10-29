import javax.swing.*;

/**
 * Created by samz on 2016-10-29.
 */
public class DrawingPanel extends JPanel {
    State state = State.getInstance();

    public DrawingPanel() {
        state.subscribe(onStateChange());
    }

    private State.Subscriber onStateChange() {
        return event -> {
            switch(event.type) {
                case RESET_CANVAS:
                    resetCanvas();
                    break;
            }
        };
    }

    private void resetCanvas() {
        System.out.println("Canvas Reset");
    }
}
