package com.example.mobile;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DetailReportActivity extends AppCompatActivity {

    private TextView equipmentNameTextView;
    private TextView statusTextView;
    private TextView workDoneTextView;
    private TextView executorTextView;
    private TextView repairDateTextView;
    private TextView nextMaintenanceTextView;
    private Button downloadButton;
    private Button backButton;
    private DataStorage dataStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_report);

        dataStorage = DataStorage.getInstance();

        String requestId = getIntent().getStringExtra("request_id");
        final RepairRequest request = findRequestById(requestId);

        if (request == null) {
            finish();
            return;
        }

        initViews();
        displayReport(request);

        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.widget.Toast.makeText(DetailReportActivity.this,
                        "Отчет скачивается...", android.widget.Toast.LENGTH_SHORT).show();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initViews() {
        equipmentNameTextView = findViewById(R.id.equipmentNameTextView);
        statusTextView = findViewById(R.id.statusTextView);
        workDoneTextView = findViewById(R.id.workDoneTextView);
        executorTextView = findViewById(R.id.executorTextView);
        repairDateTextView = findViewById(R.id.repairDateTextView);
        nextMaintenanceTextView = findViewById(R.id.nextMaintenanceTextView);
        downloadButton = findViewById(R.id.downloadButton);
        backButton = findViewById(R.id.backButton);
    }

    private void displayReport(RepairRequest request) {
        Equipment equipment = dataStorage.getEquipmentById(request.getEquipmentId());

        equipmentNameTextView.setText(equipment.getName());
        statusTextView.setText("Завершен");

        String workDone = "• Замена подшипников шпинделя\n" +
                "• Диагностика электроники\n" +
                "• Калибровка осей\n" +
                "• Замена смазки направляющих\n" +
                "• Тестовый запуск в течение 4 часов";
        workDoneTextView.setText(workDone);

        executorTextView.setText("Исполнитель: Иванов А.А. (ремонтная бригада №3)");
        repairDateTextView.setText("Дата ремонта: " + request.getDate() + " - 14.02.2025");
        nextMaintenanceTextView.setText("Следующее ТО: 14.05.2025");
    }

    private RepairRequest findRequestById(String id) {
        for (RepairRequest req : dataStorage.getRepairRequests()) {
            if (req.getId().equals(id)) return req;
        }
        return null;
    }
}