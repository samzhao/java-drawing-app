import javax.swing.*;

/**
 * Created by samz on 2016-10-29.
 */
public class DrawingPanel extends JPanel {
    AppState appState = AppState.getInstance();

    public DrawingPanel() {
        appState.subscribe(onStateChange());
    }

    private AppState.Subscriber onStateChange() {
        return (event, state) -> {
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
