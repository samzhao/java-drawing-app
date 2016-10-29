import javax.swing.*;
import java.awt.*;

/**
 * Created by samz on 2016-10-29.
 */
public class Main {
    static final int windowWidth = 600;
    static final int windowHeight = 500;

    public Main() {
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}
