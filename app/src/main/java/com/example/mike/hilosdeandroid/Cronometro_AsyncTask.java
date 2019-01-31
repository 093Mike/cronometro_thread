package com.example.mike.hilosdeandroid;

import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Cronometro_AsyncTask extends AppCompatActivity {
    TextView l_cronometro;
    Button inio, parar;
    String salida;
    int horas, min, seg, micros;
    boolean pausado;
    MiAsync asy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);

        l_cronometro = findViewById(R.id.crono);
        l_cronometro.setText("00:00:00");
        inio = findViewById(R.id.start);
        parar = findViewById(R.id.b_para);
        //inicializo las variables
        horas = 0;
        min = 0;
        seg = 0;
        micros = 0;
        pausado = false;
        asy = new MiAsync();

    }

    //Boton de Start
    public void b_start(View view) {

        if (pausado) {
            synchronized (l_cronometro) {
                pausado = false;
                parar.setEnabled(true);
                inio.setEnabled(false);
                l_cronometro.notifyAll();
            }
        } else {
            asy.execute();
        }
    }

    public void b_pausa(View view) {
        pausado = true;
        parar.setEnabled(false);
        inio.setEnabled(true);
    }


    public void crono() {
        micros++;
        if (micros == 100) {
            seg++;
            micros = 0;
        }
        if (seg == 60) {
            min++;
            seg = 0;
        }
        if (min == 60) {
            min = 0;
            horas++;
        }
    }

    public String formato() {
        salida="";
        if (horas <= 9) {
            salida += "0";
        }
        salida += horas;
        salida += ":";
        if (min <= 9) {
            salida += "0";
        }
        salida += min;
        salida += ":";
        if (seg <= 9) {
            salida += "0";
        }
        salida += seg + ":";
        if (micros <= 9) {
            salida += "0";
        }
        salida += micros;
        return salida;
    }

    static public void espera(int e) {
        try {
            Thread.sleep(e);
        } catch (InterruptedException ex) {
        }
    }

    class MiAsync extends AsyncTask<Void, String, Boolean> {
        @Override
        protected void onPreExecute() {
            parar.setEnabled(true);
            inio.setEnabled(false);
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            while (true) {
                if (pausado) {
                    synchronized (l_cronometro) {
                        try {
                            l_cronometro.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                espera(10);

                if (!pausado) {
                    crono();
                    publishProgress(formato());
                }

            if(isCancelled()){
                    break;
                }
            }
            return true;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            l_cronometro.setText(values[0]);
        }
    }

}
