package com.example.kawinev1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ViewFlipper;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    ViewFlipper flipper;
    Button prevButton, nextButton, goToCarritoButton;
    ImageButton imgBtnUser, imgBtnCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        flipper = findViewById(R.id.view_flipper);
        prevButton = findViewById(R.id.prev_button);
        nextButton = findViewById(R.id.next_button);
        goToCarritoButton = findViewById(R.id.goToCarrito);
        imgBtnUser = findViewById(R.id.imgBtnUser);
        imgBtnCart = findViewById(R.id.imgBtnCart);


        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                flipper.setInAnimation(MainActivity.this, android.R.anim.slide_in_left);
                flipper.setOutAnimation(MainActivity.this, android.R.anim.slide_out_right);

                flipper.showPrevious();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                flipper.setInAnimation(MainActivity.this, R.anim.slide_right);
                flipper.setOutAnimation(MainActivity.this, R.anim.slide_left);

                flipper.showNext();
            }
        });

        goToCarritoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Carrito.class);
                startActivity(intent);
            }
        });

        imgBtnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
            }
        });

        imgBtnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Carrito.class);
                startActivity(intent);
            }
        });
    }
}