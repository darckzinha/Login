package com.wilkercastro.login;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class MainLogin extends AppCompatActivity {

    private EditText editLogar, editSenha;
    private Button btnEntrar;

    private String HOST = "http://wilkercastro.com/login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_login);

        editLogar = findViewById(R.id.logar);
        editSenha = findViewById(R.id.senhaLogar);
        btnEntrar = findViewById(R.id.btnEntrar);

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String login = editLogar.getText().toString();
                String senha = editSenha.getText().toString();

                String URL = HOST + "/logar.php";
                if (login.isEmpty() && senha.isEmpty()) {
                    Toast.makeText(MainLogin.this, "Todos os campos são obrigatórios", Toast.LENGTH_SHORT).show();
                } else {
                    Ion.with(MainLogin.this)
                            .load(URL)
                            .setBodyParameter("login_app", login)
                            .setBodyParameter("senha_app", senha)
                            .asJsonObject()
                            .setCallback(new FutureCallback<JsonObject>() {
                                @Override
                                public void onCompleted(Exception e, JsonObject result) {
                                    try {
                                        String RETORNO = result.get("LOGIN").getAsString();

                                        if (RETORNO.equals("ERRO")) {
                                            Toast.makeText(MainLogin.this, "Ops! Login ou senha incorretos", Toast.LENGTH_SHORT).show();
                                        }else if (RETORNO.equals("SUCESSO")) {
                                            Intent abrePrincipal = new Intent(MainLogin.this, MesasActivity.class);
                                            startActivity(abrePrincipal);
                                        } else {
                                            Toast.makeText(MainLogin.this, "Ops! Ocorreu um erro!", Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (Exception erro) {
                                        Toast.makeText(MainLogin.this, "Ops! Ocorreu um erro, "+ erro, Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                }
            }
        });

    }
}
