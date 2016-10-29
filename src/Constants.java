import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by samz on 2016-10-29.
 */
public class Constants {
    public enum EVENTS {
        RESET_CANVAS,
        SET_ACTIVE_COLOR
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
