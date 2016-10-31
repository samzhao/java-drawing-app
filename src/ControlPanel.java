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
    AppState appState = state.getState();

    private Color panelBg = Color.lightGray;

    private ArrayList<MyButton> colorControls = new ArrayList<>();
    private ArrayList<MyButton> modeControls = new ArrayList<>();

    MyButton resetBtn = createBtn("Reset");

    public ControlPanel() {
        state.subscribe(onStateChange());

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
        state.dispatch(event);
    }
    private void selectMode(String btnText) {
        Constants.MODES mode = Constants.MODES.fromName(btnText);
        Event event = new Event(Constants.EVENTS.SET_ACTIVE_MODE, mode);
        state.dispatch(event);
    }

    private void changeColor(Color btnBg) {
        Constants.COLORS color = Constants.COLORS.valueOf(btnBg);

        MyShape shape = appState.getActiveShape();
        if (shape == null) return;

        MyShape shapeClone = shape.clone();
        shapeClone.setColor(color.getColor());

        Event event = new Event(Constants.EVENTS.UPDATE_SHAPE, shapeClone);
        state.dispatch(event);
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

    private State.Subscriber onStateChange() {
        return newState -> {
            updateColorControls(newState.getActiveColor());
            updateModeControls(newState.getActiveMode());

            appState = newState;
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
        } else if (modeControls.contains(clickedBtn)) {
            selectMode(clickedBtn.getText());
        }

        if (appState.getActiveMode().equals(Constants.MODES.EDIT)) {
            if (colorControls.contains(clickedBtn)) {
                changeColor(clickedBtn.getBackground());
            }
        }
    }
}
