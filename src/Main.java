import javax.swing.*;
import java.awt.*;

/**
 * Created by samz on 2016-10-29.
 */
public class Main {
    Canvas canvas;
    static final int windowWidth = 600;
    static final int windowHeight = 500;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::run);
    }

    public static void run() {
        JFrame frame = new JFrame("SwingDraw");
        Container container = frame.getContentPane();

        ControlPanel cp = new ControlPanel();
        DrawingPanel dp = new DrawingPanel();

        container.add(dp);
        container.add(cp, BorderLayout.NORTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
