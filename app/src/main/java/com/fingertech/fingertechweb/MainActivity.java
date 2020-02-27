package com.fingertech.fingertechweb;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.fingertech.fingertechweb.Socket.ServiceSocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import butterknife.BindView;
import butterknife.ButterKnife;



public class MainActivity extends AppCompatActivity {

    Nitgen nitgen;


    @BindView(R.id.textView)
    TextView textView2;


 public static final int SERVERPORT = 9000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this,this);

        Intent intent = new Intent(MainActivity.this, ServiceSocket.class);
        startService(intent);


    }










    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "onPause", Toast.LENGTH_LONG).show();






        return;

    }


    public class Servicesocket extends IntentService {

        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;

        Thread thread;


        public Servicesocket(String name) {
            super(name);
        }

        @Override
        protected void onHandleIntent(@Nullable Intent intent) {
            socket();

        }

        public void socket(){


            thread = new Thread(new Runnable() {

                @Override
                public void run() {

                    ServerSocket s = null;
                    clientSocket = null;
                    try {
                        s = new ServerSocket(9000,0,  InetAddress.getByName("192.168.1.190"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    while (!Thread.currentThread().isInterrupted()){
                        try {





                            clientSocket = s.accept();
                            addelementosonthread("aberto");
                            in = new BufferedReader(
                                    new InputStreamReader(clientSocket.getInputStream()));
                            out = new PrintWriter(clientSocket.getOutputStream(), true);
                            String input = in.readLine();
                            out.println("received: " + "enviado");
                            addelementosonthread(input);
                            System.out.println("socket");

                            in.close();
                            out.close();


                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }}
            });
            thread.start();
        }

    }
    public void addelementosonthread( String text){
        this.runOnUiThread(() -> {
            Toast.makeText(this,text,Toast.LENGTH_LONG).show();
            textView2.setText(text);
        });
    }
}
