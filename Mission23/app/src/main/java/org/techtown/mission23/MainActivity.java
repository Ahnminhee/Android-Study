package org.techtown.mission23;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Spinner spinner;
    private Spinner spinner2;

    ArrayList<String> arrayList;
    ArrayList<String> arrayList2;

    ArrayAdapter<String> arrayAdapter;
    ArrayAdapter<String> arrayAdapter2;

    String outputColor;
    String outputThickness;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arrayList = new ArrayList<>();
        arrayList.add("색상");
        arrayList.add("WHITE");
        arrayList.add("BLACK");
        arrayList.add("YELLOW");
        arrayList.add("RED");
        arrayList.add("BLUE");

        arrayList2 = new ArrayList<>();
        arrayList2.add("굵기");
        arrayList2.add("1pt");
        arrayList2.add("2pt");
        arrayList2.add("3pt");

        arrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item,
                arrayList);

        arrayAdapter2 = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item,
                arrayList2);

        spinner = findViewById(R.id.color);
        spinner2 = findViewById(R.id.thickness);

        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                outputColor = spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner2.setAdapter(arrayAdapter2);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                outputThickness = spinner2.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        send(outputColor, outputThickness);

        Toast.makeText(this, outputColor + "색상" + outputThickness + "굵기" , Toast.LENGTH_LONG).show();
    }

    public void send(String color, String thickness) {
        Intent intent = new Intent(getApplicationContext(), DrawView.class);

        intent.putExtra("color", color);
        intent.putExtra("thickness", thickness);

        startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

}
