import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by samz on 2016-10-29.
 */
class ControlPanel extends JPanel implements ActionListener {
    State state = State.getInstance();

    private Color panelBg = Color.lightGray;

    private ArrayList<MyButton> colorControls = new ArrayList();
    MyButton resetBtn = createBtn("Reset");

    public ControlPanel() {
        state.subscribe(onStateChange());

        for (Constants.COLORS color : Constants.COLORS.values()) {
            colorControls.add(createBtn(color.getColor()));
        }

        for (MyButton btn : colorControls) {
            add(btn);
        }

        // set default selected color
        selectColor(colorControls.get(0).getBackground());

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

    private void selectColor(Color btnBg) {
        Constants.COLORS color = Constants.COLORS.valueOf(btnBg);
        Event event = new Event(Constants.EVENTS.SET_ACTIVE_COLOR, color);
        state.dispatch(event);
    }

    private State.Subscriber onStateChange() {
        return event -> {
            switch(event.type) {
                case SET_ACTIVE_COLOR:
                    for (MyButton btn : colorControls) {
                        btn.setInactive();

                        if (Constants.COLORS.valueOf(btn.getBackground()) == event.payload) {
                            btn.setActive();
                        }
                    }
                    break;
            }
        };
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MyButton clickedBtn = (MyButton) e.getSource();

        if (clickedBtn == resetBtn) {
            Event event = new Event(Constants.EVENTS.RESET_CANVAS);
            state.dispatch(event);

            return;
        }

        if (colorControls.contains(clickedBtn)) {
            selectColor(clickedBtn.getBackground());
            return;
        }
    }
}
