package com.example.capitalquiz.data;

import android.content.Context;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class DataManager {
    public static Map<String, String> loadCountryCapitalMap(Context context) {
        Map<String, String> map = new LinkedHashMap<>();
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(context.getAssets().open("capitales.txt"))
            );
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 2) {
                    map.put(parts[0].trim(), parts[1].trim());
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
