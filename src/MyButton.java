import javax.swing.*;
import java.awt.*;

/**
 * Created by samz on 2016-10-29.
 */
public class MyButton extends JButton {
    private Boolean isActive = false;
    private Boolean canBeActive = true;
    private int btnWidth = 60;
    private int btnHeight = 30;
    private Color parentBackground = Color.lightGray;

    public MyButton(Color parentBackground, Color bg) {
        super();

        this.parentBackground = parentBackground;

        this.setBackground(bg);
        this.setContentAreaFilled(false);
        this.setOpaque(true);
        this.setMinimumSize(new Dimension(btnWidth, btnHeight));
        this.setPreferredSize(new Dimension(btnWidth, btnHeight));
        setInactiveBorder();
    }

    public void setCanBeActive(Boolean canBeActive) {
        this.canBeActive = canBeActive;
    }

    public void setActive() {
        toggleActive(true);
    }
    public void setInactive() {
        toggleActive(false);
    }

    private void toggleActive(Boolean isActive) {
        if (!canBeActive) return;

        this.isActive = isActive;

        if (isActive) {
            setActiveBorder();
        } else {
            setInactiveBorder();
        }

        repaint();
    }

    private void setActiveBorder() {
        this.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(this.getBackground(), 5),
                BorderFactory.createLineBorder(this.getBackground(), 3)
        ));
    }
    private void setInactiveBorder() {

        this.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(parentBackground, 3),
                BorderFactory.createLineBorder(this.getBackground(), 3)
        ));
    }
}
