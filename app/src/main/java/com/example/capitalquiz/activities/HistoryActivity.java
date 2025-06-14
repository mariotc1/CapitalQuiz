package com.example.capitalquiz.activities;


import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;


import com.example.capitalquiz.R;
import com.example.capitalquiz.model.GameResult;
import com.example.capitalquiz.utils.HistoryManager;

import java.text.SimpleDateFormat;
import java.util.*;

public class HistoryActivity extends AppCompatActivity {

    ListView listView;
    Spinner spinnerOrdenar;
    Button btnBorrar;
    ArrayAdapter<String> adapter;
    List<GameResult> historial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        listView = findViewById(R.id.listaHistorial);
        spinnerOrdenar = findViewById(R.id.spinnerOrdenar);
        btnBorrar = findViewById(R.id.btnBorrarHistorial);

        historial = HistoryManager.getHistory(this);

        spinnerOrdenar.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,
                new String[]{"Más aciertos", "Mejor tiempo", "Más reciente"}));

        spinnerOrdenar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                ordenarHistorial(pos);
                mostrarHistorial();
            }

            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });

        btnBorrar.setOnClickListener(v -> {
            HistoryManager.clearHistory(this);
            historial.clear();
            mostrarHistorial();
        });

        mostrarHistorial();
    }

    private void ordenarHistorial(int modo) {
        switch (modo) {
            case 0:
                historial.sort((a, b) -> Integer.compare(b.aciertos, a.aciertos));
                break;
            case 1:
                historial.sort(Comparator.comparingLong(a -> a.tiempoMs));
                break;
            case 2:
                historial.sort((a, b) -> Long.compare(b.fecha, a.fecha));
                break;
        }
    }

    private void mostrarHistorial() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        List<String> mostrar = new ArrayList<>();
        for (GameResult r : historial) {
            String linea = "✅ " + r.aciertos + " | ❌ " + r.fallos +
                    " | ⏱ " + (r.tiempoMs / 1000) + "s\n🗓 " + sdf.format(new Date(r.fecha));
            mostrar.add(linea);
        }

        adapter = new ArrayAdapter<String>(this, R.layout.list_item_historial, R.id.textoHistorial, mostrar);
        listView.setAdapter(adapter);
    }

}