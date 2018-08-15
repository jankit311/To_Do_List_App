package com.thispc.to_do;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
Spinner spin;
String s1,s2;
Button b1,b2,b3;
EditText etname;
TextView tvdata;
SQLiteDatabase sd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1=findViewById(R.id.btn_add);
        b2=findViewById(R.id.btn_show);
        tvdata=findViewById(R.id.tv_data);
        b3=findViewById(R.id.update);
        etname=findViewById(R.id.name);
        spin=findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(MainActivity.this,R.array.status,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(this);

        sd=openOrCreateDatabase("dbname",0,null);
        sd.execSQL("create table if not exists tname(name varchar(150),status varchar(150))");
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sd.execSQL("insert into tname values('"+etname.getText().toString()+"','"+s1+"')");

            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor c=sd.rawQuery("select * from tname",null);

                if(c.moveToFirst()) {

                    do {
                        tvdata.append("Task: "+c.getString(0)+" Status: "+c.getString(1)+"\n");
                    }while (c.moveToNext());
                }
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                s2=etname.getText().toString();
                ContentValues contentValues = new ContentValues();
                contentValues.put("name",s2);
                contentValues.put("status",s1);
                sd.update("tname",contentValues,"name = ?",new String[]{s2});
            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        s1 = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Toast.makeText(this, "select the status of task", Toast.LENGTH_SHORT).show();
    }
}
