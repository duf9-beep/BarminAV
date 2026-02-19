package com.example.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class EquipmentCardActivity extends AppCompatActivity {

    private TextView nameTextView;
    private TextView inventoryTextView;
    private TextView modelTextView;
    private TextView manufacturerTextView;
    private TextView operationTimeTextView;
    private TextView locationTextView;
    private TextView lastMaintenanceTextView;
    private TextView nextMaintenanceTextView;
    private TextView statusTextView;
    private Button editButton;
    private Button reportButton;
    private DataStorage dataStorage;
    private Equipment equipment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment_card);

        dataStorage = DataStorage.getInstance();

        String equipmentId = getIntent().getStringExtra("equipment_id");
        equipment = dataStorage.getEquipmentById(equipmentId);

        if (equipment == null) {
            Toast.makeText(this, "Оборудование не найдено", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        initViews();
        displayEquipment();
        setupButtons();
    }

    private void initViews() {
        nameTextView = findViewById(R.id.equipmentNameTextView);
        inventoryTextView = findViewById(R.id.inventoryTextView);
        modelTextView = findViewById(R.id.modelTextView);
        manufacturerTextView = findViewById(R.id.manufacturerTextView);
        operationTimeTextView = findViewById(R.id.operationTimeTextView);
        locationTextView = findViewById(R.id.locationTextView);
        lastMaintenanceTextView = findViewById(R.id.lastMaintenanceTextView);
        nextMaintenanceTextView = findViewById(R.id.nextMaintenanceTextView);
        statusTextView = findViewById(R.id.statusTextView);
        editButton = findViewById(R.id.editButton);
        reportButton = findViewById(R.id.reportButton);
    }

    private void displayEquipment() {
        nameTextView.setText(equipment.getName());
        inventoryTextView.setText("Инвентарный номер: " + equipment.getInventoryNumber());
        modelTextView.setText(equipment.getModel());
        manufacturerTextView.setText(equipment.getManufacturer());
        operationTimeTextView.setText(equipment.getOperationTime());
        locationTextView.setText(equipment.getLocation());
        lastMaintenanceTextView.setText("• Дата последнего обслуживания: " +
                equipment.getLastMaintenance());
        nextMaintenanceTextView.setText("• Следующее обслуживание: " +
                equipment.getNextMaintenance());

        String status = equipment.getStatus();
        statusTextView.setText("• Статус: " + status);

        int statusColor;
        if (status.equals("Исправен")) {
            statusColor = getResources().getColor(R.color.cps_green);
        } else if (status.equals("В ремонте")) {
            statusColor = getResources().getColor(R.color.cps_orange);
        } else {
            statusColor = getResources().getColor(R.color.cps_red);
        }
        statusTextView.setTextColor(statusColor);
    }

    private void setupButtons() {
        User currentUser = dataStorage.getCurrentUser();
        if (currentUser != null && (currentUser.getRole() == UserRole.MANAGER ||
                currentUser.getRole() == UserRole.ADMIN)) {
            editButton.setVisibility(View.VISIBLE);
        } else {
            editButton.setVisibility(View.GONE);
        }

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EquipmentCardActivity.this,
                        EditEquipmentActivity.class);
                intent.putExtra("equipment_id", equipment.getId());
                startActivity(intent);
            }
        });

        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EquipmentCardActivity.this,
                        DetailRepairActivity.class);
                intent.putExtra("equipment_id", equipment.getId());
                startActivity(intent);
            }
        });
    }
}