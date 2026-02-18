package com.example.mobile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText loginEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private DataStorage dataStorage;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataStorage = DataStorage.getInstance();

        loginEditText = findViewById(R.id.loginEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = loginEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (login.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity.this,
                            "Заполните все поля", Toast.LENGTH_SHORT).show();
                    return;
                }

                User user = dataStorage.authenticate(login, password);

                if (user != null) {
                    Intent intent = new Intent(MainActivity.this, UserActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(MainActivity.this,
                            "Неверный логин или пароль", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}