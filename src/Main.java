import javax.swing.*;
import java.awt.*;

/**
 * Created by samz on 2016-10-29.
 */
public class Main {
    public Main() {
        JFrame frame = new JFrame("SwingDraw");
        Container container = frame.getContentPane();

        DrawingPanel dp = new DrawingPanel();
        ControlPanel cp = new ControlPanel();

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
