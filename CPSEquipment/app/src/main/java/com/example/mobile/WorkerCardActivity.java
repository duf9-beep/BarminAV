package com.example.mobile;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class WorkerCardActivity extends AppCompatActivity {

    private EditText fullNameEditText;
    private Spinner accessSpinner;
    private EditText loginEditText;
    private EditText passwordEditText;
    private EditText positionEditText;
    private EditText departmentEditText;
    private Button saveButton;
    private Button deleteButton;
    private DataStorage dataStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_card);

        dataStorage = DataStorage.getInstance();

        String workerId = getIntent().getStringExtra("worker_id");

        initViews();
        setupSpinner();

        if (!workerId.equals("new")) {
            loadWorkerData(workerId);
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveWorker();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteConfirmation();
            }
        });
    }

    private void initViews() {
        fullNameEditText = findViewById(R.id.fullNameEditText);
        accessSpinner = findViewById(R.id.accessSpinner);
        loginEditText = findViewById(R.id.loginEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        positionEditText = findViewById(R.id.positionEditText);
        departmentEditText = findViewById(R.id.departmentEditText);
        saveButton = findViewById(R.id.saveButton);
        deleteButton = findViewById(R.id.deleteButton);
    }

    private void setupSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.access_levels, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        accessSpinner.setAdapter(adapter);
    }

    private void loadWorkerData(String workerId) {
        fullNameEditText.setText("Иванов Иван Иванович");
        loginEditText.setText("ivanov.ii");
        passwordEditText.setText("password");
        positionEditText.setText("Инженер-технолог");
        departmentEditText.setText("Технический отдел");

        accessSpinner.setSelection(0);
    }

    private void saveWorker() {
        String fullName = fullNameEditText.getText().toString().trim();
        String login = loginEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String position = positionEditText.getText().toString().trim();
        String department = departmentEditText.getText().toString().trim();

        if (fullName.isEmpty() || login.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Заполните обязательные поля",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "Сотрудник сохранен", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void showDeleteConfirmation() {
        new AlertDialog.Builder(this)
                .setTitle("Удаление сотрудника")
                .setMessage("Вы уверены, что хотите удалить сотрудника?")
                .setPositiveButton("Удалить", (dialog, which) -> {
                    Toast.makeText(this, "Сотрудник удален", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .setNegativeButton("Отмена", null)
                .show();
    }
}