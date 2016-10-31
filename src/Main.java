import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by samz on 2016-10-29.
 */
public class Main {
    public Main() {
        JFrame frame = new JFrame("SwingDraw");
        Container container = frame.getContentPane();

        DrawingPanel dp = new DrawingPanel();
        ControlPanel cp = new ControlPanel();

        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new MyKeyEventDispatcher());

        container.add(dp);
        container.add(cp, BorderLayout.NORTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private class MyKeyEventDispatcher implements KeyEventDispatcher {
        @Override
        public boolean dispatchKeyEvent(KeyEvent e) {
            if (e.getID() == KeyEvent.KEY_PRESSED) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_BACK_SPACE || keyCode == KeyEvent.VK_DELETE) {
                    Event event = new Event(Constants.EVENTS.DELETE_OBJECT);
                    State.getInstance().dispatch(event);
                }
            }

            return false;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}
