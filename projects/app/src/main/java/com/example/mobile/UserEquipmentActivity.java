package com.example.mobile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserEquipmentActivity extends AppCompatActivity {

    private EditText searchEditText;
    private LinearLayout equipmentContainer;
    private TextView totalCountTextView;
    private TextView activeCountTextView;
    private TextView repairCountTextView;
    private DataStorage dataStorage;
    private List<Equipment> allEquipment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_equipment);

        dataStorage = DataStorage.getInstance();
        allEquipment = dataStorage.getAllEquipment();

        initViews();
        setupSearch();
        displayEquipment(allEquipment);
        updateStatistics();
    }

    private void initViews() {
        searchEditText = findViewById(R.id.searchEditText);
        equipmentContainer = findViewById(R.id.equipmentContainer);
        totalCountTextView = findViewById(R.id.totalCountTextView);
        activeCountTextView = findViewById(R.id.activeCountTextView);
        repairCountTextView = findViewById(R.id.repairCountTextView);
    }

    private void setupSearch() {
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterEquipment(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void filterEquipment(String query) {
        List<Equipment> filtered = new ArrayList<>();
        if (query.isEmpty()) {
            filtered = allEquipment;
        } else {
            for (Equipment eq : allEquipment) {
                if (eq.getName().toLowerCase().contains(query.toLowerCase()) ||
                        eq.getInventoryNumber().toLowerCase().contains(query.toLowerCase())) {
                    filtered.add(eq);
                }
            }
        }
        displayEquipment(filtered);
    }

    private void displayEquipment(List<Equipment> equipmentList) {
        equipmentContainer.removeAllViews();

        for (final Equipment eq : equipmentList) {
            View itemView = LayoutInflater.from(this).inflate(R.layout.item_equipment_user,
                    equipmentContainer, false);

            TextView nameTextView = itemView.findViewById(R.id.equipmentNameTextView);
            TextView invNumberTextView = itemView.findViewById(R.id.invNumberTextView);
            Button viewButton = itemView.findViewById(R.id.viewButton);
            Button reportButton = itemView.findViewById(R.id.reportButton);

            nameTextView.setText(eq.getName());
            invNumberTextView.setText("Инв. №: " + eq.getInventoryNumber());

            viewButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(UserEquipmentActivity.this,
                            EquipmentCardActivity.class);
                    intent.putExtra("equipment_id", eq.getId());
                    startActivity(intent);
                }
            });

            reportButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showRepairDialog(eq);
                }
            });

            equipmentContainer.addView(itemView);
        }
    }

    private void showRepairDialog(final Equipment equipment) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Сообщить о неисправности");
        builder.setMessage("Оборудование: " + equipment.getName());

        final EditText input = new EditText(this);
        input.setHint("Опишите проблему...");
        builder.setView(input);

        builder.setPositiveButton("Отправить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String problem = input.getText().toString().trim();
                if (problem.isEmpty()) {
                    Toast.makeText(UserEquipmentActivity.this,
                            "Опишите проблему", Toast.LENGTH_SHORT).show();
                    return;
                }

                RepairRequest request = new RepairRequest(
                        String.valueOf(System.currentTimeMillis()),
                        equipment.getId(),
                        equipment.getName(),
                        dataStorage.getCurrentUser().getFullName(),
                        problem,
                        DateFormat.getDateInstance().format(new Date()),
                        "Новая"
                );

                dataStorage.addRepairRequest(request);
                Toast.makeText(UserEquipmentActivity.this,
                        "Заявка отправлена", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Отмена", null);
        builder.show();
    }

    private void updateStatistics() {
        int active = 0;
        int needRepair = 0;

        for (Equipment eq : allEquipment) {
            if (eq.getStatus().equals("Исправен")) active++;
            if (eq.getStatus().equals("Требует ремонта") ||
                    eq.getStatus().equals("В ремонте")) needRepair++;
        }

        totalCountTextView.setText("Всего записей: " + allEquipment.size());
        activeCountTextView.setText("Активных: " + active);
        repairCountTextView.setText("Требуют ремонта: " + needRepair);
    }
}