package com.example.crimealert;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.opencsv.CSVReader;

import java.io.InputStreamReader;
import java.util.ArrayList;

public class ReportActivity extends AppCompatActivity {
    CSVReader reader;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        textView=(TextView) findViewById(R.id.textView);

        ReadCsv();
    }

    public void ReadCsv() {
        String s="";


        ArrayList<String> location = new ArrayList<>();

        try {
            InputStreamReader is = new InputStreamReader(getAssets()
                    .open("CDb.csv"));

            reader = new CSVReader(is);
            String[] column;
            int c = 0;

            while ((column = reader.readNext()) != null) {
                for (int i = 2; i < column.length; i++) {
                    System.out.println(column[i]);
                    s=s+column[i];
                    s+=" ";
                    if(i==5)
                    s+="\n";

                }

            }
        } catch (Exception e) {
        }
        textView.setText(s);

    }
    public void backagain(View v){
        Intent inten=new Intent(this,Main3Activity.class);
        startActivity(inten);

    }


}
