package com.example.mobile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;
import java.util.List;

public class RepairActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private LinearLayout inRepairContainer;
    private LinearLayout historyContainer;
    private TextView inRepairCount;
    private TextView completedCount;
    private TextView plannedCount;
    private Button viewAllInRepairButton;
    private Button viewAllHistoryButton;
    private DataStorage dataStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair);

        dataStorage = DataStorage.getInstance();

        if (dataStorage.getRepairRequests() == null) {

            createTestRequests();
        }

        initViews();
        setupTabs();
        loadInRepairData();
        loadHistoryData();
        updateStatistics();
        setupButtons();
    }

    private void initViews() {
        tabLayout = findViewById(R.id.tabLayout);
        inRepairContainer = findViewById(R.id.inRepairContainer);
        historyContainer = findViewById(R.id.historyContainer);
        inRepairCount = findViewById(R.id.inRepairCount);
        completedCount = findViewById(R.id.completedCount);
        plannedCount = findViewById(R.id.plannedCount);
        viewAllInRepairButton = findViewById(R.id.viewAllInRepairButton);
        viewAllHistoryButton = findViewById(R.id.viewAllHistoryButton);
    }

    private void setupTabs() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    inRepairContainer.setVisibility(View.VISIBLE);
                    historyContainer.setVisibility(View.GONE);
                } else {
                    inRepairContainer.setVisibility(View.GONE);
                    historyContainer.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        inRepairContainer.setVisibility(View.VISIBLE);
        historyContainer.setVisibility(View.GONE);
    }

    private void setupButtons() {
        viewAllInRepairButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RepairActivity.this,
                        "Все оборудование в ремонте", Toast.LENGTH_SHORT).show();
            }
        });

        viewAllHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RepairActivity.this,
                        "Вся история ремонтов", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createTestRequests() {
        List<Equipment> equipmentList = dataStorage.getAllEquipment();
        if (equipmentList != null && !equipmentList.isEmpty()) {
            for (int i = 0; i < Math.min(3, equipmentList.size()); i++) {
                Equipment eq = equipmentList.get(i);
                RepairRequest request = new RepairRequest(
                        String.valueOf(System.currentTimeMillis() + i),
                        eq.getId(),
                        eq.getName(),
                        "Тестовый пользователь",
                        "Тестовая неисправность",
                        "15.02.2025",
                        i == 0 ? "В работе" : "Новая"
                );
                dataStorage.addRepairRequest(request);
            }
        }
    }

    private void loadInRepairData() {
        inRepairContainer.removeAllViews();

        List<RepairRequest> allRequests = dataStorage.getRepairRequests();
        List<RepairRequest> inRepair = new ArrayList<>();

        if (allRequests != null) {
            for (RepairRequest req : allRequests) {
                if (req.getStatus().equals("В работе") || req.getStatus().equals("Новая")) {
                    inRepair.add(req);
                }
            }
        }

        if (inRepair.isEmpty()) {
            View emptyView = getLayoutInflater().inflate(R.layout.item_empty_state,
                    inRepairContainer, false);
            @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView emptyText =
                    emptyView.findViewById(R.id.emptyTextView);
            if (emptyText != null) {
                emptyText.setText("Нет оборудования в ремонте");
            }
            inRepairContainer.addView(emptyView);
            return;
        }

        for (final RepairRequest request : inRepair) {
            try {
                View itemView = getLayoutInflater().inflate(R.layout.item_repair_card,
                        inRepairContainer, false);

                TextView nameTextView = itemView.findViewById(R.id.equipmentNameTextView);
                TextView dateTextView = itemView.findViewById(R.id.dateTextView);
                @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView statusTextView =
                        itemView.findViewById(R.id.statusTextView);

                if (nameTextView != null) {
                    nameTextView.setText(request.getEquipmentName());
                }
                if (dateTextView != null) {
                    dateTextView.setText("С " + request.getDate());
                }
                if (statusTextView != null) {
                    statusTextView.setText(request.getStatus());
                }

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(RepairActivity.this,
                                DetailRepairActivity.class);
                        intent.putExtra("request_id", request.getId());
                        startActivity(intent);
                    }
                });

                inRepairContainer.addView(itemView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void loadHistoryData() {
        historyContainer.removeAllViews();

        List<RepairRequest> allRequests = dataStorage.getRepairRequests();
        List<RepairRequest> history = new ArrayList<>();

        if (allRequests != null) {
            for (RepairRequest req : allRequests) {
                if (req.getStatus().equals("Завершена")) {
                    history.add(req);
                }
            }
        }

        if (history.isEmpty()) {
            View emptyView = getLayoutInflater().inflate(R.layout.item_empty_state,
                    historyContainer, false);
            @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView emptyText =
                    emptyView.findViewById(R.id.emptyTextView);
            if (emptyText != null) {
                emptyText.setText("История ремонтов пуста");
            }
            historyContainer.addView(emptyView);
            return;
        }

        for (final RepairRequest request : history) {
            try {
                View itemView = getLayoutInflater().inflate(R.layout.item_history_card,
                        historyContainer, false);

                TextView nameTextView = itemView.findViewById(R.id.equipmentNameTextView);
                TextView dateTextView = itemView.findViewById(R.id.dateTextView);
                TextView descriptionTextView = itemView.findViewById(R.id.descriptionTextView);

                if (nameTextView != null) {
                    nameTextView.setText(request.getEquipmentName());
                }
                if (dateTextView != null) {
                    dateTextView.setText(request.getDate());
                }
                if (descriptionTextView != null) {
                    descriptionTextView.setText(request.getProblemDescription());
                }

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(RepairActivity.this,
                                DetailReportActivity.class);
                        intent.putExtra("request_id", request.getId());
                        startActivity(intent);
                    }
                });

                historyContainer.addView(itemView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void updateStatistics() {
        List<RepairRequest> allRequests = dataStorage.getRepairRequests();
        int inRepair = 0;
        int completed = 0;

        if (allRequests != null) {
            for (RepairRequest req : allRequests) {
                if (req.getStatus().equals("В работе") || req.getStatus().equals("Новая")) {
                    inRepair++;
                } else if (req.getStatus().equals("Завершена")) {
                    completed++;
                }
            }
        }

        int planned = 8;

        inRepairCount.setText(String.valueOf(inRepair));
        completedCount.setText(String.valueOf(completed));
        plannedCount.setText(String.valueOf(planned));
    }
}