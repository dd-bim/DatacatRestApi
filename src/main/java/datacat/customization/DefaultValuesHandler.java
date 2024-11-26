package datacat.customization;

// =====================================================================================================================
// I M P O R T   S E C T I O N
// =====================================================================================================================
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

// =====================================================================================================================
// H A N D L E R   C L A S S   S E C T I O N
// =====================================================================================================================
public class DefaultValuesHandler {

    private static final Map<String, Object> defaultValues = new HashMap<>();

    static {
        defaultValues.put("status", "active");
        defaultValues.put("creatorLanguageCode", "de");
        defaultValues.put("defaultLanguageCode", "de");
        // defaultValues.put("countryOfOrigin", "DE");
        defaultValues.put("propertyStatus", "active");
        defaultValues.put("licence", "");
        defaultValues.put("licenceUrl", "");
        defaultValues.put("isLatestVersion", true);
        defaultValues.put("isVerified", true);
        defaultValues.put("moreInfoUrl", "https://datacat.org/");


        // add other default values here
    }

    public static void ensureDefaults(Object model) {
        for (Map.Entry<String, Object> entry : defaultValues.entrySet()) {
            setDefaultValue(model, entry.getKey(), entry.getValue());
        }
    }

    private static void setDefaultValue(Object model, String fieldName, Object defaultValue) {
        try {
            Field field = model.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            if (field.get(model) == null) {
                field.set(model, defaultValue);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            // Field not found or not accessible, ignore
        }
    }
}