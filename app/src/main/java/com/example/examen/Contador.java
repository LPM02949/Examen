package com.example.examen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class Contador extends AppCompatActivity {
    private TextView contadorTextView;
    private int num = 0;
    private final int incAuto = 1;
    private final int tiempoAutoClick = 1000;
    private boolean isRunning = true; // Bandera para controlar la ejecución del hilo

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contador);
        contadorTextView = findViewById(R.id.textocontador);

    }

    public void sumarAuto(View view) {
        new Thread(() -> {
            while (isRunning) { // Utiliza la bandera para controlar el bucle
                num += incAuto;
                try {
                    Thread.sleep(tiempoAutoClick);
                    runOnUiThread(this::updateCounter);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void updateCounter() {
        int horas = num / 3600;
        int minutos = (num % 3600) / 60;
        int segundos = num % 60;
        String tiempoFormateado = String.format(Locale.getDefault(), "%02d:%02d:%02d", horas, minutos, segundos);
        contadorTextView.setText(tiempoFormateado);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isRunning = false; // Detiene el hilo cuando la actividad se destruye
    }
}
