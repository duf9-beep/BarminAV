package com.example.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class UserActivity extends AppCompatActivity {

    private TextView welcomeTextView;
    private TextView roleTextView;
    private CardView equipmentCard;
    private CardView repairsCard;
    private CardView workersCard;
    private Button logoutButton;
    private DataStorage dataStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        dataStorage = DataStorage.getInstance();
        User currentUser = dataStorage.getCurrentUser();

        if (currentUser == null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }

        initViews();
        setupUserInterface(currentUser);
        setupClickListeners();
    }

    private void initViews() {
        welcomeTextView = findViewById(R.id.welcomeTextView);
        roleTextView = findViewById(R.id.roleTextView);
        equipmentCard = findViewById(R.id.equipmentCard);
        repairsCard = findViewById(R.id.repairsCard);
        workersCard = findViewById(R.id.workersCard);
        logoutButton = findViewById(R.id.logoutButton);
    }

    private void setupUserInterface(User user) {
        welcomeTextView.setText(user.getFullName());

        String roleText = "Уровень доступа: ";

        repairsCard.setVisibility(View.GONE);
        workersCard.setVisibility(View.GONE);

        switch (user.getRole()) {
            case USER:
                roleText += "Пользователь";
                break;

            case MANAGER:
                roleText += "Менеджер";
                repairsCard.setVisibility(View.VISIBLE);
                break;

            case ADMIN:
                roleText += "Администратор";
                repairsCard.setVisibility(View.VISIBLE);
                workersCard.setVisibility(View.VISIBLE);
                break;
        }

        roleTextView.setText(roleText);
    }

    private void setupClickListeners() {
        equipmentCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserActivity.this,
                        UserEquipmentActivity.class));
            }
        });

        repairsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserActivity.this,
                        RepairActivity.class));
            }
        });

        workersCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserActivity.this,
                        WorkerListActivity.class));
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataStorage.logout();
                startActivity(new Intent(UserActivity.this,
                        MainActivity.class));
                finish();
            }
        });
    }
}