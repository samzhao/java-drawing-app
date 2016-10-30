import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by samz on 2016-10-29.
 */
class ControlPanel extends JPanel implements ActionListener {
    AppState appState = AppState.getInstance();

    private Color panelBg = Color.lightGray;

    private ArrayList<MyButton> colorControls = new ArrayList<>();
    private ArrayList<MyButton> modeControls = new ArrayList<>();

    MyButton resetBtn = createBtn("Reset");

    public ControlPanel() {
        appState.subscribe(onStateChange());

        setupColorControls();
        setupModeControls();

        // set default selected color
        selectColor(colorControls.get(0).getBackground());
        selectMode(modeControls.get(0).getText());

        resetBtn.setCanBeActive(false);
        add(resetBtn);

        this.setBackground(panelBg);
    }

    private void setupModeControls() {
        for (Constants.MODES mode : Constants.MODES.values()) {
            modeControls.add(createBtn(mode.getName()));
        }

        for (MyButton btn : modeControls) {
            add(btn);
        }
    }

    private void setupColorControls() {
        for (Constants.COLORS color : Constants.COLORS.values()) {
            colorControls.add(createBtn(color.getColor()));
        }

        for (MyButton btn : colorControls) {
            add(btn);
        }
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
        appState.dispatch(event);
    }
    private void selectMode(String btnText) {
        Constants.MODES mode = Constants.MODES.fromName(btnText);
        Event event = new Event(Constants.EVENTS.SET_ACTIVE_MODE, mode);
        appState.dispatch(event);
    }

    private void updateColorControls(Constants.COLORS color) {
        for (MyButton btn : colorControls) {
            btn.setInactive();

            if (Constants.COLORS.valueOf(btn.getBackground()) == color) {
                btn.setActive();
            }
        }
    }
    private void updateModeControls(Constants.MODES mode) {
        for (MyButton btn : modeControls) {
            btn.setInactive();

            if (Constants.MODES.fromName(btn.getText()) == mode) {
                btn.setActive();
            }
        }
    }

    private AppState.Subscriber onStateChange() {
        return (event, state) -> {
            switch(event.type) {
                case SET_ACTIVE_COLOR:
                    updateColorControls((Constants.COLORS) state.get("ACTIVE_COLOR"));
                    break;
                case SET_ACTIVE_MODE:
                    updateModeControls((Constants.MODES) state.get("ACTIVE_MODE"));
                    break;
            }
        };
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MyButton clickedBtn = (MyButton) e.getSource();

        if (clickedBtn == resetBtn) {
            Event event = new Event(Constants.EVENTS.RESET_CANVAS);
            appState.dispatch(event);

            return;
        }

        if (colorControls.contains(clickedBtn)) {
            selectColor(clickedBtn.getBackground());
            return;
        }

        if (modeControls.contains(clickedBtn)) {
            selectMode(clickedBtn.getText());
        }
    }
}
