package Auth;

import java.util.HashMap;
import java.util.Map;

public class Context {
    private static final Map<String, String> attributes = new HashMap<>();

    public static void setAttribute(String key, String value) {
        attributes.put(key, value);
    }

    public static String getAttribute(String key) {
        return attributes.get(key);
    }
}