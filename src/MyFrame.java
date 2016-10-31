import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by samz on 2016-10-30.
 */
public class MyFrame extends JFrame {
    private class MyKeyEventDispatcher implements KeyEventDispatcher {
        @Override
        public boolean dispatchKeyEvent(KeyEvent e) {
            if (e.getID() == KeyEvent.KEY_PRESSED) {
                System.out.println("tester");
            } else if (e.getID() == KeyEvent.KEY_RELEASED) {
                System.out.println("2test2");
            } else if (e.getID() == KeyEvent.KEY_TYPED) {
                System.out.println("3test3");
            }
            return false;
        }
    }
    public MyFrame() {
        System.out.println("test");
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new MyKeyEventDispatcher());
    }

    public static void main(String[] args) {
        MyFrame f = new MyFrame();
        f.pack();
        f.setVisible(true);
    }
}
