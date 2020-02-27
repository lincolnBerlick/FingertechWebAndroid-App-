package com.fingertech.fingertechweb.Socket;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ServiceSocket extends Service {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    Thread thread;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("service", "onStartCommand: ");
        socket();
        return super.onStartCommand(intent, flags, startId);


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

                        in = new BufferedReader(
                                new InputStreamReader(clientSocket.getInputStream()));
                        out = new PrintWriter(clientSocket.getOutputStream(), true);
                        String input = in.readLine();
                        out.println("received: " + "enviado");

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

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i("service", "    public IBinder onBind(Intent intent) {\n: ");
        return null;
    }
}