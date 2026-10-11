package com.example.firstaidhero;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private DatabaseHelper dbHelper;
    private List<Emergency> emergencyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listViewEmergencies);
        dbHelper = new DatabaseHelper(this);

        // نسخ وعرض قاعدة البيانات
        dbHelper.createDatabase();
        
        // جلب الحالات من قاعدة البيانات
        emergencyList = dbHelper.getAllEmergencies();
        
        List<String> titlesList = new ArrayList<>();
        for (Emergency e : emergencyList) {
            titlesList.add(e.getTitle() + " (" + e.getDangerLevel() + ")");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                titlesList
        );
        listView.setAdapter(adapter);

        // إضافة حدث النقر على أي حالة لعرض التفاصيل والشرح الخطوات
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Emergency selectedEmergency = emergencyList.get(position);
                
                // الانتقال إلى شاشة التفاصيل وإرسال رقم الحالة معها
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("EMERGENCY_ID", selectedEmergency.getId());
                startActivity(intent);
            }
        });
    }
}