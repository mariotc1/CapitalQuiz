package com.example.capitalquiz.activities;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capitalquiz.R;
import com.example.capitalquiz.utils.GameManager;


public class QuizActivity extends AppCompatActivity {

    TextView txtPregunta, txtProgreso, txtFeedback;
    EditText edtRespuesta;
    Button btnEnviar;
    ProgressBar progressBar;

    GameManager gameManager;
    int intentosRestantes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        txtPregunta = findViewById(R.id.txtPregunta);
        txtProgreso = findViewById(R.id.txtProgreso);
        txtFeedback = findViewById(R.id.txtFeedback);
        edtRespuesta = findViewById(R.id.edtRespuesta);
        btnEnviar = findViewById(R.id.btnEnviar);
        progressBar = findViewById(R.id.progressBar);

        // Recoger datos del intent
        Intent intent = getIntent();
        int cantidad = intent.getIntExtra("cantidad", 50);
        int intentos = intent.getIntExtra("intentos", 3);
        String modo = intent.getStringExtra("modo");

        gameManager = new GameManager(this, cantidad, intentos, modo);
        cargarPregunta();
    }

    private void cargarPregunta() {
        if (!gameManager.hasNext()) {
            // Fin del juego
            Intent intent = new Intent(this, ResultActivity.class);
            intent.putExtra("aciertos", gameManager.getAciertos());
            intent.putExtra("fallos", gameManager.getFallos());
            intent.putExtra("tiempo", gameManager.getTiempoTotalMs());
            startActivity(intent);
            finish();
            return;
        }

        txtPregunta.setText(gameManager.getPreguntaActual());

        int preguntaActual = gameManager.getIndiceActual() + 1;
        int total = gameManager.getTotal();

        txtProgreso.setText("Pregunta " + preguntaActual + "/" + total);

        // Actualizar barra de progreso
        progressBar.setMax(total);
        progressBar.setProgress(preguntaActual);

        edtRespuesta.setText("");
        txtFeedback.setText("");
        intentosRestantes = gameManager.getIntentosPermitidos();

        ObjectAnimator.ofInt(progressBar, "progress", preguntaActual)
                .setDuration(300)
                .start();

        btnEnviar.setOnClickListener(v -> comprobarRespuesta());
    }

    private void comprobarRespuesta() {
        String respuestaUsuario = edtRespuesta.getText().toString();
        if (respuestaUsuario.trim().isEmpty()) {
            Toast.makeText(this, "Escribe una respuesta", Toast.LENGTH_SHORT).show();
            return;
        }

        if (gameManager.comprobarRespuesta(respuestaUsuario)) {
            txtFeedback.setText("¡Correcto!");
            txtFeedback.setTextColor(getColor(R.color.verde));
            gameManager.registrarResultado(true);
            new android.os.Handler().postDelayed(this::cargarPregunta, 1000);
        } else {
            intentosRestantes--;
            if (intentosRestantes > 0) {
                txtFeedback.setText("Incorrecto. Te quedan " + intentosRestantes + " intento(s)");
                txtFeedback.setTextColor(getColor(R.color.rojo));
            } else {
                txtFeedback.setText("Fallaste. Respuesta correcta: " + gameManager.getRespuestaCorrecta());
                txtFeedback.setTextColor(getColor(R.color.rojo));
                gameManager.registrarResultado(false);
                new android.os.Handler().postDelayed(this::cargarPregunta, 2000);
            }
        }
    }
}
