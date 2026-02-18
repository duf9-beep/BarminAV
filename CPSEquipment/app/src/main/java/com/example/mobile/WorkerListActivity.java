package com.example.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class WorkerListActivity extends AppCompatActivity {

    private LinearLayout workerContainer;
    private EditText searchEditText;
    private Button addButton;
    private DataStorage dataStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_list);

        dataStorage = DataStorage.getInstance();

        initViews();
        displayWorkers();
    }

    private void initViews() {
        workerContainer = findViewById(R.id.workerContainer);
        searchEditText = findViewById(R.id.searchEditText);
        addButton = findViewById(R.id.addButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WorkerListActivity.this,
                        WorkerCardActivity.class);
                intent.putExtra("worker_id", "new");
                startActivity(intent);
            }
        });
    }

    private void displayWorkers() {
        workerContainer.removeAllViews();

        String[][] workers = {
                {"1", "Иванов Иван Иванович", "Инженер-технолог", "Активен"},
                {"2", "Петров Петр Петрович", "Мастер участка", "В отпуске"},
                {"3", "Сидорова Анна Сергеевна", "Начальник смены", "Активен"},
                {"4", "Козлов Дмитрий Алексеевич", "Механик", "Активен"}
        };

        for (final String[] worker : workers) {
            View itemView = LayoutInflater.from(this).inflate(R.layout.item_worker_card,
                    workerContainer, false);

            TextView nameTextView = itemView.findViewById(R.id.workerNameTextView);
            TextView positionTextView = itemView.findViewById(R.id.positionTextView);
            TextView statusTextView = itemView.findViewById(R.id.statusTextView);

            nameTextView.setText(worker[1]);
            positionTextView.setText(worker[2]);
            statusTextView.setText(worker[3]);

            if (worker[3].equals("Активен")) {
                statusTextView.setTextColor(getResources().getColor(R.color.cps_green));
            } else {
                statusTextView.setTextColor(getResources().getColor(R.color.cps_orange));
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(WorkerListActivity.this,
                            WorkerCardActivity.class);
                    intent.putExtra("worker_id", worker[0]);
                    startActivity(intent);
                }
            });

            workerContainer.addView(itemView);
        }
    }
}