package com.example.capitalquiz.model;

public class GameResult {
    public int aciertos;
    public int fallos;
    public long tiempoMs;
    public long fecha;

    public GameResult(int aciertos, int fallos, long tiempoMs, long fecha) {
        this.aciertos = aciertos;
        this.fallos = fallos;
        this.tiempoMs = tiempoMs;
        this.fecha = fecha;
    }
}

