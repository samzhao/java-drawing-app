import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by samz on 2016-10-29.
 */
class ControlPanel extends JPanel implements ActionListener {
    private Color panelBg = Color.lightGray;

    private ArrayList<MyButton> controls = new ArrayList();
    MyButton resetBtn = createBtn("Reset");

    public ControlPanel() {
        controls.add(createBtn(Color.BLACK));
        controls.add(createBtn(Color.RED));
        controls.add(createBtn(Color.GREEN));
        controls.add(createBtn(Color.BLUE));

        for (MyButton btn : controls) {
            add(btn);
        }

        // set default selected color
        controls.get(0).setActive();

        resetBtn.setCanBeActive(false);
        add(resetBtn);

        this.setBackground(panelBg);
    }

    private MyButton createBtn(String label) {
        MyButton btn = createBtn(Color.white);
        btn.setText(label);

        return btn;
    }
    private MyButton createBtn(Color btnBg) {
        MyButton btn = new MyButton(panelBg, btnBg);
        btn.addActionListener(this);

        return btn;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MyButton clickedBtn = (MyButton) e.getSource();

        if (clickedBtn == resetBtn) {
            return;
        }

        for (MyButton btn : controls) {
            btn.setInactive();
        }
        clickedBtn.setActive();
    }
}
