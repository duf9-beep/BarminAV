package com.example.mobile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class ManagerEquipmentActivity extends AppCompatActivity {

    private LinearLayout equipmentContainer;
    private Button addButton;
    private DataStorage dataStorage;
    private TextView totalTextView;
    private TextView inRepairTextView;
    private TextView workingTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_equipment);

        dataStorage = DataStorage.getInstance();

        initViews();
        displayEquipment();
        updateStatistics();
    }

    private void initViews() {
        equipmentContainer = findViewById(R.id.equipmentContainer);
        addButton = findViewById(R.id.addButton);
        totalTextView = findViewById(R.id.totalTextView);
        inRepairTextView = findViewById(R.id.inRepairTextView);
        workingTextView = findViewById(R.id.workingTextView);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManagerEquipmentActivity.this,
                        EditEquipmentActivity.class);
                intent.putExtra("equipment_id", "new");
                startActivity(intent);
            }
        });
    }

    private void displayEquipment() {
        equipmentContainer.removeAllViews();
        List<Equipment> equipmentList = dataStorage.getAllEquipment();

        for (final Equipment eq : equipmentList) {
            View itemView = LayoutInflater.from(this).inflate(R.layout
                    .item_equipment_manager, equipmentContainer, false);

            @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView nameTextView =
                    itemView.findViewById(R.id.equipmentNameTextView);
            @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView invNumberTextView =
                    itemView.findViewById(R.id.invNumberTextView);
            @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView statusTextView =
                    itemView.findViewById(R.id.statusTextView);
            @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button viewButton =
                    itemView.findViewById(R.id.viewButton);
            @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button repairButton =
                    itemView.findViewById(R.id.repairButton);

            nameTextView.setText(eq.getName());
            invNumberTextView.setText("Инв. №: " + eq.getInventoryNumber());
            statusTextView.setText(eq.getStatus());

            int statusColor;
            if (eq.getStatus().equals("Исправен")) {
                statusColor = getResources().getColor(R.color.cps_green);
            } else if (eq.getStatus().equals("В ремонте")) {
                statusColor = getResources().getColor(R.color.cps_orange);
            } else {
                statusColor = getResources().getColor(R.color.cps_red);
            }
            statusTextView.setTextColor(statusColor);

            viewButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ManagerEquipmentActivity.this,
                            EquipmentCardActivity.class);
                    intent.putExtra("equipment_id", eq.getId());
                    startActivity(intent);
                }
            });

            repairButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ManagerEquipmentActivity.this,
                            DetailRepairActivity.class);
                    intent.putExtra("equipment_id", eq.getId());
                    startActivity(intent);
                }
            });

            equipmentContainer.addView(itemView);
        }
    }

    private void updateStatistics() {
        int total = dataStorage.getAllEquipment().size();
        int inRepair = 0;
        int working = 0;

        for (Equipment eq : dataStorage.getAllEquipment()) {
            if (eq.getStatus().equals("В ремонте")) inRepair++;
            if (eq.getStatus().equals("Исправен")) working++;
        }

        totalTextView.setText("Всего единиц: " + total);
        inRepairTextView.setText("В ремонте: " + inRepair);
        workingTextView.setText("Исправны: " + working);
    }
}