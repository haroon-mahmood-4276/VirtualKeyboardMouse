package com.example.virtualmousekeyboard;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    TextInputEditText txtIP;
    Button btnKeys, btnSetIP;
    TextView lblIP;

    public String OldIP;
    private Boolean IsConnected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtIP = findViewById(R.id.txtIP);
        btnKeys = findViewById(R.id.btnKeys);
        btnSetIP = findViewById(R.id.btnSetIP);
        lblIP = findViewById(R.id.lblIP);

        btnKeys.setOnClickListener(v -> {
            if (IsConnected)
                startActivity(new Intent(MainActivity.this, Mouse.class));
            else
                Toast.makeText(MainActivity.this, "Please Connect to the PC's IP", Toast.LENGTH_SHORT).show();
        });

        btnSetIP.setOnClickListener(v -> {
            String ip = String.valueOf(txtIP.getText());
            if (ip.isEmpty() || !GlobalClass.validate(ip)) {
                Toast.makeText(MainActivity.this, "Enter a valid IP.", Toast.LENGTH_SHORT).show();
            } else {

                if (GlobalClass.ipAddress != null && !GlobalClass.ipAddress.equals(ip)) {
                    OldIP = GlobalClass.ipAddress;
                    IsConnected = false;
                }

                GlobalClass.ipAddress = ip;
                Sender sender = new Sender();
                sender.execute("Connected");
                Toast.makeText(MainActivity.this, "IP: " + ip, Toast.LENGTH_SHORT).show();

                new Thread(() -> {
                    try {
                        isIpConnect(GlobalClass.ipAddress);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }).start();

                if(IsConnected) {
                    lblIP.setText(String.format("IP Address: %s:4276", ip));
                    txtIP.setText("");
                }
            }
        });
    }

    public void isIpConnect(String ip) {
        Socket socket;
        try {
            socket = new Socket(ip, 4276);
            IsConnected = true;
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
            IsConnected = false;
        }
    }
}