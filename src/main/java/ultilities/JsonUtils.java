package ultilities;

import constants.FrameworkConstants;
import helpers.Helpers;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * To construct the map by the reading the config values from JSON. Not used in this framework but can be leveraged
 * Instead of property file based on the requirements
 */
public class JsonUtils {
    private static String jsonFilePathDefault = Helpers.getCurrentDir() + "src/test/resources/datajson/testConfig.json";
    private static String payloadJsonPath = Helpers.getCurrentDir() + "src/test/resources/payloads/";

    public JsonUtils() {
        super();
    }

    /**
     * Get all need data from parsed testconfig json file
     */
    public static JsonNode getTestConfig() {
        return recursiveJsonReplacement();
    }

    /**
     * Fetch Json body for Post request with updating the $$ values with replacement map
     */
    public static JsonNode getJsonBody(String payloadName, Map<String, Object> replacementMap) {
        return replaceValues(parseJsonFile(payloadName), replacementMap);
    }

    /**
     * Fetch Json body for Post request with updating the $$ values with replacement map
     */
    public static String getJsonBody(String payloadName, JSONArray jsonArray) {
        JsonNode jsonNode = parseJsonFile(payloadName);
        return jsonNode.toPrettyString().replaceAll("jsonString", jsonArray.toString());
    }

    /**
     * Parse any JSON file with filepath as input
     */
    public static JsonNode parseJsonFile(String payloadName) {
        JsonNode jsonNode = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            jsonNode = objectMapper.readTree(new File(payloadJsonPath + payloadName + ".json"));
        } catch (Exception e) {
            LogUtils.error("Failed to parse json files");
        }
        return jsonNode;
    }

    /**
     * Replace the values in json provided that are starting with $$ with keys in Map provided
     **/
    public static JsonNode replaceValues(JsonNode node, Map<String, Object> replacementMap) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            if (node.isObject()) {
                ObjectNode objectNode = (ObjectNode) node;
                Iterator<Map.Entry<String, JsonNode>> fields = objectNode.fields();
                while (fields.hasNext()) {
                    Map.Entry<String, JsonNode> field = fields.next();
                    JsonNode fieldValue = field.getValue();
                    if (fieldValue.isTextual()) {
                        String textValue = fieldValue.asText();
                        if (textValue.startsWith("$$")) {
                            String replacementKey = textValue.substring(2);
                            if (replacementMap.containsKey(replacementKey)) {
                                Object replacementValue = replacementMap.get(replacementKey);
                                if (replacementValue instanceof String) {
                                    objectNode.put(field.getKey(), (String) replacementValue);
                                } else if (replacementValue instanceof Integer) {
                                    objectNode.put(field.getKey(), (Integer) replacementValue);
                                } else if (replacementValue instanceof Boolean) {
                                    objectNode.put(field.getKey(), (Boolean) replacementValue);
                                } else if (replacementValue == null) {
                                    objectNode.putNull(field.getKey());
                                } else if (replacementValue instanceof JSONArray) {
                                    JSONArray jsonArray = (JSONArray) replacementValue;
                                    try {
                                        JsonNode jsonNode = objectMapper.readTree(jsonArray.toString());
                                        objectNode.set(field.getKey(), jsonNode);
                                    } catch (
                                            Exception e) {
                                        // Handle parsing error
                                    }
                                } else if (replacementValue instanceof JSONObject) {
                                    JSONObject jsonObject = (JSONObject) replacementValue;
                                    try {
                                        JsonNode jsonNode = objectMapper.readTree(jsonObject.toString());
                                        objectNode.set(field.getKey(), jsonNode);
                                    } catch (
                                            Exception e) {
                                        // Handle parsing error
                                    }
                                }// Add more cases for other types if needed
                            }
                        }
                    }
                    // Recursively call replaceValues for nested elements
                    replaceValues(fieldValue, replacementMap);
                }
            } else if (node.isArray()) {
                for (JsonNode element : node) {
                    // Recursively call replaceValues for array elements
                    replaceValues(element, replacementMap);
                }
            }
        } catch (Exception e) {
            LogUtils.fail("Failed to replace and update json file " + e.getMessage());
        }
        return node;
    }

    /**
     * Recursive function to replace the environment key in test config json file with actual environment
     **/
    public static JsonNode recursiveJsonReplacement() {
        try {
            // Read the JSON content from the file
            File jsonFile = new File(jsonFilePathDefault);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonFile);
            // Replace placeholders like ${xxxx} with a specific value throughout the JSON
            replacePlaceholders(jsonNode, "${EnvironmentPrefix}", FrameworkConstants.ENVIRONMENT_PREFIX.toLowerCase());
            return jsonNode;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Recursive function to replace the environment key in test config json file with actual environment
     **/
    private static void replacePlaceholders(JsonNode node, String placeholderFormat, String replacement) {
        if (node.isObject()) {
            // If it's an object, iterate through its fields
            node.fields().forEachRemaining(entry -> {
                JsonNode fieldValue = entry.getValue();
                if (fieldValue.isTextual()) {
                    String updatedValue = replacePlaceholdersInString(fieldValue.asText(), placeholderFormat, replacement);
                    ((com.fasterxml.jackson.databind.node.ObjectNode) node).put(entry.getKey(), updatedValue);
                } else {
                    replacePlaceholders(fieldValue, placeholderFormat, replacement); // Recursively process nested objects/arrays
                }
            });
        } else if (node.isArray()) {
            // If it's an array, iterate through its elements
            for (int i = 0; i < node.size(); i++) {
                JsonNode arrayElement = node.get(i);
                if (arrayElement.isTextual()) {
                    String updatedValue = replacePlaceholdersInString(arrayElement.asText(), placeholderFormat, replacement);
                    ((com.fasterxml.jackson.databind.node.ArrayNode) node).remove(i);
                    ((com.fasterxml.jackson.databind.node.ArrayNode) node).insert(i, updatedValue);
                } else {
                    replacePlaceholders(arrayElement, placeholderFormat, replacement); // Recursively process nested objects/arrays
                }
            }
        }
    }

    /**
     * Recursive function to replace the environment key in test config json file with actual environment
     **/
    private static String replacePlaceholdersInString(String input, String placeholderFormat, String replacement) {
        String regex = Pattern.quote(placeholderFormat);
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        StringBuffer buffer = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(buffer, replacement);
        }
        matcher.appendTail(buffer);
        return buffer.toString();
    }
}
