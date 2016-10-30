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
    public enum EVENTS {
        RESET_CANVAS,
        SET_ACTIVE_COLOR,
        SET_ACTIVE_MODE
    }

    public enum MODES {
        FREEFORM("Free", 0),
        SHAPES("Shapes", 1),
        EDIT("Edit", 2);

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
        BLACK(Color.BLACK),
        RED(Color.RED),
        GREEN(Color.GREEN),
        BLUE(Color.BLUE);

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
