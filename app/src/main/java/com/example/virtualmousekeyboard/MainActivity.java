package com.example.virtualmousekeyboard;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    TextInputEditText txtIP;
    Button btnKeyboard, btnMouse, btnSetIP;
    TextView lblIP;

    private String OldIP;
    private Boolean IsConnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtIP = findViewById(R.id.txtIP);
        btnKeyboard = findViewById(R.id.btnKeyboard);
        btnMouse = findViewById(R.id.btnMouse);
        btnSetIP = findViewById(R.id.btnSetIP);
        lblIP = findViewById(R.id.lblIP);

        btnMouse.setOnClickListener(v -> {
            Log.d("socket method: ", String.valueOf(IsConnected));
            if (IsConnected) {
                Intent activity2Intent = new Intent(MainActivity.this, Mouse.class);
                startActivity(activity2Intent);
            }
        });

        btnKeyboard.setOnClickListener(v -> {
            if (IsConnected) {
                Intent activity2Intent = new Intent(MainActivity.this, MyKeyboard.class);
                startActivity(activity2Intent);
            }
        });

        btnSetIP.setOnClickListener(v -> {
            String ip = String.valueOf(txtIP.getText());
            if (ip.isEmpty() || !GlobalClass.validate(ip)) {
                Toast.makeText(getApplicationContext(), "Enter a valid IP.", Toast.LENGTH_SHORT).show();
            } else {

                if (GlobalClass.ipAddress != null && !GlobalClass.ipAddress.equals(ip)) {
                    OldIP = GlobalClass.ipAddress;
                    IsConnected = false;
                }

                GlobalClass.ipAddress = ip;
                Sender sender = new Sender();
                sender.execute("Connected");
                Toast.makeText(getApplicationContext(), "IP: " + ip, Toast.LENGTH_SHORT).show();

                lblIP.setText("IP Address: " + ip.toString() + ":4276");
                txtIP.setText("");

                new Thread(() -> {
                    try {
                        isIpConnect(GlobalClass.ipAddress);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }).start();
            }
        });
    }

    public void isIpConnect(String ip) {
        Socket socket = null;
        try {
            socket = new Socket(ip, 4276);
            IsConnected = true;
            socket.close();
            Log.d("Socket Method: ", String.valueOf(IsConnected));
        } catch (Exception e) {
            Log.d("Socket Method Error: ", e.toString());
            e.printStackTrace();
            IsConnected = false;
            Log.d("Socket Method: ", String.valueOf(IsConnected));
        }
    }
}