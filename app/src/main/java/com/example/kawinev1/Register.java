package com.example.kawinev1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.content.Intent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Register extends AppCompatActivity {


    public EditText userTXT, CorreoTXT, ContrasenaTXT, ContrasenaTXTR;
    public Button RegistrarUsuarioBTN, VolverBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        userTXT = findViewById(R.id.inputUser);
        CorreoTXT = findViewById(R.id.editEmail);
        ContrasenaTXT = findViewById(R.id.inputPassword);
        ContrasenaTXTR = findViewById(R.id.inputRepeatPassword);
        RegistrarUsuarioBTN = findViewById(R.id.btnRegister);
        VolverBTN = findViewById(R.id.btnVolver);


        Log.d("RegisterActivity", "onCreate: Activity initialized");
        VolverBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this, MainActivity.class);
                startActivity(intent);
            }
        });
        RegistrarUsuarioBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("RegisterActivity", "onClick: Register button clicked");

                String user = userTXT.getText().toString().trim();
                String correo = CorreoTXT.getText().toString().trim();
                String password = ContrasenaTXT.getText().toString().trim();

                if (!user.isEmpty() && !correo.isEmpty() && !password.isEmpty()) {
                    registrarUsuario(user, correo, password);
                } else {
                    Toast.makeText(Register.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void registrarUsuario(final String user, final String email, final String password) {
        Log.d("RegisterActivity", "registrarUsuario: Starting registration process");

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://192.168.68.106/kawine/register.php");
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                    String post_data = URLEncoder.encode("user", "UTF-8") + "=" + URLEncoder.encode(user, "UTF-8") + "&"
                            + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&"
                            + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");

                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();

                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                    final StringBuilder result = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        result.append(line);
                    }

                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();

                    Log.d("RegisterActivity", "Server Response: " + result.toString());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (result.toString().trim().equals("success")) { // Usar trim() para evitar espacios adicionales
                                Toast.makeText(Register.this, "Usuario registrado con éxito", Toast.LENGTH_LONG).show();

                                // Redirigir a la actividad de login
                                Intent intent = new Intent(Register.this, Login.class);
                                startActivity(intent);
                                finish(); // Cerrar la actividad actual
                            } else {
                                Toast.makeText(Register.this, "Error en el registro: " + result.toString(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                } catch (Exception e) {
                    Log.e("RegisterActivity", "Error in registration process", e);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Register.this, "Error en la conexión: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        }).start();
    }
}
