package com.example.absencemonitoring;

import android.database.Cursor;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    EmployeeAdapter adapter;
    List<Employee> list, list1;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        dbHelper = new DBHelper(this);



        list = new ArrayList<>();
        list1 = new ArrayList<>();
        list.add(new Employee("علی ارجمندی", "کارمند", true));
        list.add(new Employee("علی ارجمندی", "کارمند", true));
        list.add(new Employee("علی ارجمندی", "کارمند", true));
        list.add(new Employee("علی ارجمندی", "کارمند", false));
        list.add(new Employee("علی ارجمندی", "کارمند", false));
        list.add(new Employee("علی ارجمندی", "کارمند", false));
        list.add(new Employee("علی ارجمندی", "کارمند", false));
        list.add(new Employee("علی ارجمندی", "کارمند", false));
        list.add(new Employee("علی ارجمندی", "کارمند", false));
        list.add(new Employee("علی ارجمندی", "کارمند", false));
        list.add(new Employee("علی ارجمندی", "کارمند", false));
        list.add(new Employee("علی ارجمندی", "کارمند", false));
        list.add(new Employee("علی ارجمندی", "کارمند", false));


        recyclerView = findViewById(R.id.recyclerview);
        adapter = new EmployeeAdapter(this, list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);

        addData("کارمند ۱", 0, "", "");
        addData("کارمند ۲", 0, "", "");
        addData("کارمند ۳", 0, "", "");
        addData("کارمند ۴", 0, "", "");
        addData("کارمند ۵", 0, "", "");
        addData("کارمند ۶", 0, "", "");
        addData("کارمند ۷", 0, "", "");
        addData("کارمند ۸", 0, "", "");
        addData("کارمند ۹", 0, "", "");
        addData("کارمند ۱۰", 0, "", "");
        addData("کارمند ۱۱", 0, "", "");
        addData("کارمند ۱۲", 0, "", "");
        addData("کارمند ۱۳", 0, "", "");
        addData("کارمند ۱۴", 0, "", "");

        Cursor data = dbHelper.getData();
        while (data.moveToNext()) {
        }


    }

    public void addData(String name, int status, String begin, String duration) {
        boolean insertData = dbHelper.addData(name, status, begin, duration);
        if (insertData) {
            Toast.makeText(getApplicationContext(), "ok", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "shit", Toast.LENGTH_SHORT).show();
        }
    }
}
