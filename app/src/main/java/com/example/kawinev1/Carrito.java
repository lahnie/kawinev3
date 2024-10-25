package com.example.kawinev1;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Carrito extends AppCompatActivity {

    private Spinner itemSpinner;
    private Button addToCartButton;
    private TextView cartItems;
    private List<String> cart = new ArrayList<>();
    private List<String> itemsFromDB = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);

        itemSpinner = findViewById(R.id.itemSpinner);
        addToCartButton = findViewById(R.id.addToCartButton);
        cartItems = findViewById(R.id.cartItems);

        fetchItemsFromDB();

        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedItem = itemSpinner.getSelectedItem().toString();
                addItemToCart(selectedItem);
            }
        });
    }

    private void fetchItemsFromDB() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Replace with your database URL
                    URL url = new URL("http://yourserver.com/get_items.php");
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("GET");

                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String line;
                    final List<String> fetchedItems = new ArrayList<>();
                    while ((line = bufferedReader.readLine()) != null) {
                        fetchedItems.add(line); // Assuming each line is an item name
                    }

                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();

                    // Update spinner with fetched data
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            itemsFromDB = fetchedItems;
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(Carrito.this, android.R.layout.simple_spinner_item, itemsFromDB);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            itemSpinner.setAdapter(adapter);
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Carrito.this, "Error fetching items", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        }).start();
    }

    private void addItemToCart(String item) {
        cart.add(item);
        updateCartView();
    }

    private void updateCartView() {
        if (cart.isEmpty()) {
            cartItems.setText("Cart is empty");
        } else {
            StringBuilder cartContent = new StringBuilder();
            for (String item : cart) {
                cartContent.append(item).append("\n");
            }
            cartItems.setText(cartContent.toString());
        }
    }
}