package com.example.herost;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;

public class EndGameActivity extends AppCompatActivity {


    private String YADANDSHEMLINK = "https://www.yadvashem.org/he/holocaust/video-testimonies.html";
    private String BRAVELINE="https://brave-together.com/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);
        Button yadAndShemBtn = findViewById(R.id.yadAndShemBtn);
        Button braveBtn = findViewById(R.id.braveLinkBtn);
        Button returnMainMenuBtn = findViewById(R.id.returnToMainMenu);

        yadAndShemBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(YADANDSHEMLINK));
                startActivity(intent);
            }
        });

        braveBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(BRAVELINE));
                startActivity(intent);
            }
        });


        returnMainMenuBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(EndGameActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}