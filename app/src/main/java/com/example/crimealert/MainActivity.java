package com.example.crimealert;


import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageButton ib;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ib=(ImageButton)findViewById(R.id.imageButton3);


        Toast.makeText(this, "Welcome!", Toast.LENGTH_SHORT).show();


        Animation animation=AnimationUtils.loadAnimation(this,R.anim.zoomin);
        ib.startAnimation(animation);






    }

    public void next(View view){

        Intent intent = new Intent(getApplicationContext(),Login.class);
        startActivity(intent);


    }
}





