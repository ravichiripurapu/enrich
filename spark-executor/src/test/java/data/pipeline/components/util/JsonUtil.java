package data.pipeline.components.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonUtil {

    public static Map<String, Object> convertStringToJsonObject(String jsonString) {
        Map<String, Object> jsonObject = new HashMap<>();
        // Use regular expression to extract key-value pairs
        Pattern pattern = Pattern.compile("\"(\\w+)\":\\s*\"?([^\"]*)\"?");
        Matcher matcher = pattern.matcher(jsonString);
        while (matcher.find()) {
            String key = matcher.group(1);
            String value = matcher.group(2);
            // Handle different value types (string, number, boolean)
            if (value.matches("\\d+")) {
                jsonObject.put(key, Integer.parseInt(value));
            } else if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
                jsonObject.put(key, Boolean.parseBoolean(value));
            } else {
                jsonObject.put(key, value);
            }
        }
        return jsonObject;
    }
}
