package com.example.mobile;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DetailRepairActivity extends AppCompatActivity {

    private TextView equipmentNameTextView;
    private TextView statusTextView;
    private TextView descriptionTextView;
    private TextView dateTextView;
    private Button historyButton;
    private Button reportReadyButton;
    private DataStorage dataStorage;
    private RepairRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_repair);

        dataStorage = DataStorage.getInstance();

        String requestId = getIntent().getStringExtra("request_id");
        request = findRequestById(requestId);

        if (request == null) {
            finish();
            return;
        }

        initViews();
        displayRequest();
        setupButtons();
    }

    private void initViews() {
        equipmentNameTextView = findViewById(R.id.equipmentNameTextView);
        statusTextView = findViewById(R.id.statusTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        dateTextView = findViewById(R.id.dateTextView);
        historyButton = findViewById(R.id.historyButton);
        reportReadyButton = findViewById(R.id.reportReadyButton);
    }

    private void displayRequest() {
        equipmentNameTextView.setText(request.getEquipmentName());
        statusTextView.setText(request.getStatus());
        descriptionTextView.setText(request.getProblemDescription());
        dateTextView.setText("Дата отправки в ремонт: " + request.getDate());
    }

    private void setupButtons() {
        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailRepairActivity.this,
                        RepairActivity.class);
                intent.putExtra("tab", 1);
                startActivity(intent);
            }
        });

        reportReadyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCompleteDialog();
            }
        });


        User currentUser = dataStorage.getCurrentUser();
        if (currentUser != null && currentUser.getRole() == UserRole.USER) {
            reportReadyButton.setVisibility(View.GONE);
        }
    }

    private void showCompleteDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Завершение ремонта")
                .setMessage("Подтвердите завершение ремонта и создание отчета")
                .setPositiveButton("Завершить", (dialog, which) -> {
                    request.setStatus("Завершена");
                    Intent intent = new Intent(DetailRepairActivity.this,
                            DetailReportActivity.class);
                    intent.putExtra("request_id", request.getId());
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton("Отмена", null)
                .show();
    }

    private RepairRequest findRequestById(String id) {
        for (RepairRequest req : dataStorage.getRepairRequests()) {
            if (req.getId().equals(id)) return req;
        }
        return null;
    }
}