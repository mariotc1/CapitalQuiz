package com.example.capitalquiz.utils;


import android.content.Context;
import android.content.SharedPreferences;

import com.example.capitalquiz.model.GameResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.*;

public class HistoryManager {
    private static final String PREF_NAME = "game_history";
    private static final String KEY = "results";

    public static void saveResult(Context context, GameResult result) {
        List<GameResult> lista = getHistory(context);
        lista.add(result);
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        editor.putString(KEY, gson.toJson(lista));
        editor.apply();
    }

    public static List<GameResult> getHistory(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String json = prefs.getString(KEY, null);
        if (json == null) return new ArrayList<>();
        Type type = new TypeToken<List<GameResult>>(){}.getType();
        return new Gson().fromJson(json, type);
    }

    public static void clearHistory(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().remove(KEY).apply();
    }
}
