package com.example.capitalquiz.utils;

import android.content.Context;

import com.example.capitalquiz.data.DataManager;

import java.util.*;

public class GameManager {

    public enum GameMode {
        PAIS_CAPITAL,
        CAPITAL_PAIS
    }

    private List<String> paises;
    private Map<String, String> mapa;
    private int totalPreguntas;
    private int intentosMax;
    private GameMode modo;

    private int indiceActual;
    private int aciertos;
    private int fallos;
    private long tiempoInicio;

    private List<String> paisesSeleccionados;
    private List<Boolean> resultados;

    public GameManager(Context context, int totalPreguntas, int intentosMax, String modoStr) {
        this.totalPreguntas = totalPreguntas;
        this.intentosMax = intentosMax;
        this.modo = modoStr.equals("capital-pais") ? GameMode.CAPITAL_PAIS : GameMode.PAIS_CAPITAL;

        mapa = DataManager.loadCountryCapitalMap(context);
        if (mapa == null || mapa.isEmpty()) {
            paises = new ArrayList<>();
            paisesSeleccionados = new ArrayList<>();
            return; // sin datos, se sale (hasNext() será false)
        }

        paises = new ArrayList<>(mapa.keySet());
        Collections.shuffle(paises);

        paisesSeleccionados = new ArrayList<>(paises.subList(0, Math.min(totalPreguntas, paises.size())));


        indiceActual = 0;
        aciertos = 0;
        fallos = 0;
        resultados = new ArrayList<>();

        tiempoInicio = System.currentTimeMillis();
    }

    public boolean hasNext() {
        return indiceActual < paisesSeleccionados.size();
    }

    public String getPreguntaActual() {
        String pais = paisesSeleccionados.get(indiceActual);
        return (modo == GameMode.PAIS_CAPITAL) ? pais : mapa.get(pais);
    }

    public String getRespuestaCorrecta() {
        String pais = paisesSeleccionados.get(indiceActual);
        return (modo == GameMode.PAIS_CAPITAL) ? mapa.get(pais) : pais;
    }

    public boolean comprobarRespuesta(String respuestaUsuario) {
        String correcta = getRespuestaCorrecta().trim().toLowerCase();
        return correcta.equals(respuestaUsuario.trim().toLowerCase());
    }

    public void registrarResultado(boolean correcto) {
        resultados.add(correcto);
        if (correcto) aciertos++;
        else fallos++;
        indiceActual++;
    }

    public int getIntentosPermitidos() {
        return intentosMax;
    }

    public int getAciertos() {
        return aciertos;
    }

    public int getFallos() {
        return fallos;
    }

    public int getTotal() {
        return paisesSeleccionados.size();
    }

    public long getTiempoTotalMs() {
        if (tiempoInicio == 0) return 0; // protección
        return System.currentTimeMillis() - tiempoInicio;
    }

    public List<Boolean> getResultados() {
        return resultados;
    }

    public int getIndiceActual() {
        return indiceActual;
    }

}
