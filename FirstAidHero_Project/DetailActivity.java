package com.example.firstaidhero;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private TextView titleView, dangerView, symptomsView;
    private ListView stepsListView;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        titleView = findViewById(R.id.textDetailTitle);
        dangerView = findViewById(R.id.textDetailDanger);
        symptomsView = findViewById(R.id.textDetailSymptoms);
        stepsListView = findViewById(R.id.listViewSteps);

        dbHelper = new DatabaseHelper(this);

        // استقبال رقم الحالة التي اختارها المستخدم
        int emergencyId = getIntent().getIntExtra("EMERGENCY_ID", 1);

        // جلب تفاصيل الحالة وشرحها من قاعدة البيانات
        Emergency emergency = dbHelper.getEmergencyById(emergencyId);
        if (emergency != null) {
            titleView.setText(emergency.getTitle());
            dangerView.setText("مستوى الخطورة: " + emergency.getDangerLevel());
            symptomsView.setText(emergency.getSymptoms());
        }

        // جلب خطوات الإسعاف الخاصة بهذه الحالة
        List<Step> stepsList = dbHelper.getStepsForEmergency(emergencyId);
        List<String> stepTexts = new ArrayList<>();
        for (Step s : stepsList) {
            stepTexts.add("الخطوة " + s.getStepNumber() + ": " + s.getInstructionText());
        }

        // عرض خطوات الإسعاف في القائمة
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                stepTexts
        );
        stepsListView.setAdapter(adapter);
    }
}