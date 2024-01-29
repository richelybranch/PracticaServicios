package com.rycry.practicaservicios;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.textclassifier.TextLinks;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.rycry.practicaservicios.R;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    EditText edNum1, edNum2;
    Button btnIngresar;
    TextView tvResultado;

    String respuesta="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edNum1 = findViewById(R.id.edNum1);
        edNum2 = findViewById(R.id.edNum2);
        btnIngresar = findViewById(R.id.btnIngresar);
        tvResultado = findViewById(R.id.tvResultado);

        // Agrega el listener al botón
        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConsumirAPI();
            }
        });
    }


    public void ConsumirAPI() {
        String promtuario = "https://ejemplo2apimovil20240128220859.azurewebsites.net/api/Operaciones?a=" + edNum1.getText() + "&b=" + edNum2.getText();

        OkHttpClient cliente = new OkHttpClient();

        Request get = new Request.Builder().url(promtuario).build();

        cliente.newCall(get).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Manejar la falla aquí
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody responseBody = null; // Declarar la variable fuera del bloque try-catch

                try {
                    responseBody = response.body();
                    if (!response.isSuccessful()) {
                        throw new IOException("Respuesta inesperada" + response);
                    }
                    respuesta = responseBody.string();

                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvResultado.setText(respuesta);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (responseBody != null) {
                        responseBody.close();
                    }
                }
            }


        });
    }
}


