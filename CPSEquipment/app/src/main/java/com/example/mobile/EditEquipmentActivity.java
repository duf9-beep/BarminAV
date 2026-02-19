package com.example.mobile;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class EditEquipmentActivity extends AppCompatActivity {

    private EditText nameEditText;
    private EditText modelEditText;
    private EditText manufacturerEditText;
    private EditText operationTimeEditText;
    private EditText locationEditText;
    private Button saveButton;
    private Button deleteButton;
    private DataStorage dataStorage;
    private Equipment equipment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_equipment);

        dataStorage = DataStorage.getInstance();

        String equipmentId = getIntent().getStringExtra("equipment_id");

        if (equipmentId.equals("new")) {
            equipment = createNewEquipment();
        } else {
            equipment = dataStorage.getEquipmentById(equipmentId);
        }

        if (equipment == null) {
            Toast.makeText(this, "Оборудование не найдено", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        initViews();
        loadEquipmentData();
        setupListeners();
    }

    private Equipment createNewEquipment() {
        String newId = String.valueOf(System.currentTimeMillis());
        return new Equipment(newId, "", "", "", "",
                "", "", "Исправен", "", "");
    }

    private void initViews() {
        nameEditText = findViewById(R.id.nameEditText);
        modelEditText = findViewById(R.id.modelEditText);
        manufacturerEditText = findViewById(R.id.manufacturerEditText);
        operationTimeEditText = findViewById(R.id.operationTimeEditText);
        locationEditText = findViewById(R.id.locationEditText);
        saveButton = findViewById(R.id.saveButton);
        deleteButton = findViewById(R.id.deleteButton);
    }

    private void loadEquipmentData() {
        nameEditText.setText(equipment.getName());
        modelEditText.setText(equipment.getModel());
        manufacturerEditText.setText(equipment.getManufacturer());
        operationTimeEditText.setText(equipment.getOperationTime());
        locationEditText.setText(equipment.getLocation());
    }

    private void setupListeners() {
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveChanges();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteConfirmation();
            }
        });
    }

    private void saveChanges() {
        String name = nameEditText.getText().toString().trim();
        String model = modelEditText.getText().toString().trim();
        String manufacturer = manufacturerEditText.getText().toString().trim();
        String operationTime = operationTimeEditText.getText().toString().trim();
        String location = locationEditText.getText().toString().trim();

        if (name.isEmpty() || model.isEmpty() || manufacturer.isEmpty()) {
            Toast.makeText(this, "Заполните обязательные поля",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        equipment.setName(name);
        equipment.setModel(model);
        equipment.setManufacturer(manufacturer);
        equipment.setOperationTime(operationTime);
        equipment.setLocation(location);

        dataStorage.updateEquipment(equipment);
        Toast.makeText(this, "Изменения сохранены", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void showDeleteConfirmation() {
        new AlertDialog.Builder(this)
                .setTitle("Удаление оборудования")
                .setMessage("Вы уверены, что хотите удалить " + equipment.getName() + "?")
                .setPositiveButton("Удалить", (dialog, which) -> {
                    dataStorage.deleteEquipment(equipment.getId());
                    Toast.makeText(this, "Оборудование удалено",
                            Toast.LENGTH_SHORT).show();
                    finish();
                })
                .setNegativeButton("Отмена", null)
                .show();
    }
}