package com.example.capitalquiz.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capitalquiz.R;
import com.example.capitalquiz.model.GameResult;
import com.example.capitalquiz.utils.HistoryManager;

public class ResultActivity extends AppCompatActivity {

    TextView txtResumen, txtAciertos, txtFallos, txtTiempo;
    Button btnVolver, btnReiniciar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        txtResumen = findViewById(R.id.txtResumen); // Título principal
        txtAciertos = findViewById(R.id.txtAciertos); // ✔ Aciertos
        txtFallos = findViewById(R.id.txtFallos);     // ✘ Fallos
        txtTiempo = findViewById(R.id.txtTiempo);     // ⏱ Tiempo

        btnVolver = findViewById(R.id.btnVolver);
        btnReiniciar = findViewById(R.id.btnReiniciar);

        // Recogemos datos del intent
        int aciertos = getIntent().getIntExtra("aciertos", 0);
        int fallos = getIntent().getIntExtra("fallos", 0);
        long tiempo = getIntent().getLongExtra("tiempo", 0);

        // Formato bonito del tiempo
        long segundos = (tiempo / 1000) % 60;
        long minutos = (tiempo / 1000) / 60;

        // Establecer valores en sus campos
        txtAciertos.setText("✔ Aciertos: " + aciertos);
        txtFallos.setText("✘ Fallos: " + fallos);
        txtTiempo.setText("⏱ Tiempo: " + minutos + " min " + segundos + " seg");

        btnVolver.setOnClickListener(v -> {
            Intent i = new Intent(this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
        });

        btnReiniciar.setOnClickListener(v -> {
            finish(); // vuelve atrás al QuizActivity
        });

        // Guardar en historial
        GameResult resultado = new GameResult(aciertos, fallos, tiempo, System.currentTimeMillis());
        HistoryManager.saveResult(this, resultado);
    }
}