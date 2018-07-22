package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    private final static String KEY_NAME = "name";
    private final static String KEY_MAIN_NAME = "mainName";
    private final static String KEY_ALSO_KNOWN = "alsoKnownAs";
    private final static String KEY_ORIGIN = "placeOfOrigin";
    private final static String KEY_DESC = "description";
    private final static String KEY_IMAGE = "image";
    private final static String KEY_INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) {

        try {
            JSONObject root = new JSONObject(json);
            JSONObject name = root.getJSONObject(KEY_NAME);
            String mainName = name.getString(KEY_MAIN_NAME);
            JSONArray alsoKnownAsArray = name.getJSONArray(KEY_ALSO_KNOWN);
            ArrayList<String> alsoKnownAs = new ArrayList<String>();
            for (int i = 0; i < alsoKnownAsArray.length(); i++) {
                alsoKnownAs.add(alsoKnownAsArray.getString(i));
            }
            String placeOfOrigin = root.getString(KEY_ORIGIN);
            String description = root.getString(KEY_DESC);
            String image = root.getString(KEY_IMAGE);
            JSONArray ingredientsArray = root.getJSONArray(KEY_INGREDIENTS);
            ArrayList<String> ingredients = new ArrayList<String>();
            for (int i = 0; i < ingredientsArray.length(); i++) {
                ingredients.add(ingredientsArray.getString(i));
            }
            return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);

        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
