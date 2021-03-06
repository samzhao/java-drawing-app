import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by samz on 2016-10-29.
 */
public class Constants {
    public final static Color SELECTION_COLOR = new Color(63, 121, 186);
    public final static int SELECTION_STROKE_WIDTH = 5;
    public final static BasicStroke DEFAULT_STROKE = new BasicStroke(0);
    public final static BasicStroke LINE_STROKE = new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
    public final static BasicStroke LINE_SELECTION_STROKE = new BasicStroke(10, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
    public final static BasicStroke SELECTION_STROKE = new BasicStroke(SELECTION_STROKE_WIDTH, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);

    public enum EVENTS {
        RESET_CANVAS,

        SET_ACTIVE_COLOR,
        SET_ACTIVE_MODE,

        ADD_SHAPE,
        UPDATE_SHAPE,
        CHANGE_COLOR,

        DELETE_OBJECT,
    }

    public enum MODES {
        FREE("Free", 0),
        RECT("Rectangle", 1),
        TRIG("Triangle", 2),
        LINE("Line", 3),
        OVAL("Oval", 4),
        EDIT("Edit", 5);

        private final String name;
        private final int value;
        MODES(final String name, final int val) {
            this.name = name;
            this.value = val;
        }

        public String getName() {
            return name;
        }
        public int getValue() {
            return value;
        }

        // http://stackoverflow.com/a/34052075/1248894
        static final Map<String, MODES> names = Arrays.stream(MODES.values())
                .collect(Collectors.toMap(MODES::getName, Function.identity()));
        static final Map<Integer, MODES> values = Arrays.stream(MODES.values())
                .collect(Collectors.toMap(MODES::getValue, Function.identity()));

        public static MODES fromName(final String name) {
            return names.get(name);
        }

        public static MODES fromValue(final int value) {
            return values.get(value);
        }
    }

    public enum COLORS {
        BLACK(new Color(29, 29, 33)),
        BLUE(new Color(42, 179, 231)),
        YELLOW(new Color(252, 211, 61)),
        GREEN(new Color(60, 171, 39)),
        RED(new Color(210, 39, 109));

        private Color color;
        private static Map<Color, COLORS> map = new HashMap<Color, COLORS>() {{
            for (COLORS c : COLORS.values()) {
                put(c.color, c);
            }
        }};

        COLORS(Color color) {
            this.color = color;
        }

        public Color getColor() {
            return color;
        }

        public static COLORS valueOf(Color c) {
            return map.get(c);
        }
    }
}
