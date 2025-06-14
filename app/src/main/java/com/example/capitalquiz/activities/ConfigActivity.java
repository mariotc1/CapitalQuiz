package com.example.capitalquiz.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.capitalquiz.R;

public class ConfigActivity extends AppCompatActivity {

    Spinner spinnerCantidad, spinnerIntentos;
    RadioGroup modoJuegoGroup;
    Button btnIniciar;

    String[] cantidades = {"50", "100", "150", "194"};
    String[] intentos = {"0", "1", "2", "3"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        spinnerCantidad = findViewById(R.id.spinnerCantidad);
        spinnerIntentos = findViewById(R.id.spinnerIntentos);
        modoJuegoGroup = findViewById(R.id.radioGroupModo);
        btnIniciar = findViewById(R.id.btnIniciar);

        // Set up spinners
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cantidades);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCantidad.setAdapter(adapter1);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, intentos);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIntentos.setAdapter(adapter2);

        btnIniciar.setOnClickListener(v -> {
            int cantidad = Integer.parseInt(spinnerCantidad.getSelectedItem().toString());
            int intentos = Integer.parseInt(spinnerIntentos.getSelectedItem().toString());
            int modo = modoJuegoGroup.getCheckedRadioButtonId(); // 0 = país -> capital, 1 = capital -> país

            String modoSeleccionado = "pais-capital"; // default
            if (modo == R.id.radioCapitalPais) modoSeleccionado = "capital-pais";

            Intent intent = new Intent(this, QuizActivity.class);
            intent.putExtra("cantidad", cantidad);
            intent.putExtra("intentos", intentos);
            intent.putExtra("modo", modoSeleccionado);
            startActivity(intent);
            finish();
        });
    }
}