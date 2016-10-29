import javax.swing.*;
import java.awt.*;

/**
 * Created by samz on 2016-10-29.
 */
public class Main {
    Canvas canvas;
    static final int windowWidth = 600;
    static final int windowHeight = 500;

    JFrame window = new JFrame("SwingDraw");
    JPanel container = new JPanel(new BorderLayout());
    JPanel controlPanel = new JPanel();

    public Main() {
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().add(container);
        window.pack();

        container.add(controlPanel, BorderLayout.PAGE_START);

        window.setSize(windowWidth, windowHeight);
        window.setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }
}
