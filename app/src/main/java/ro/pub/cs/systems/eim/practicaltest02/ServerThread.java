package ro.pub.cs.systems.eim.practicaltest02;

import android.util.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import cz.msebera.android.httpclient.client.ClientProtocolException;

public class ServerThread extends Thread {
    private int port;
    private ServerSocket serverSocket;

    private BitcoinData data;

    public ServerThread(int port) {
        this.port = port;

        try {
            this.serverSocket = new ServerSocket(this.port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.data = new BitcoinData();


    }

    @Override
    public void run() {
        UpdateDataThread updateDataThread = new UpdateDataThread(this);
        updateDataThread.start();

        try {
            while (!Thread.currentThread().isInterrupted()) {
                Log.i(Constants.TAG, "[SERVER THREAD] Waiting for a client invocation...");

                Socket socket = serverSocket.accept();

                Log.i(Constants.TAG, "[SERVER THREAD] A connection request was received from " + socket.getInetAddress() + ":" + socket.getLocalPort());
                CommunicationThread communicationThread = new CommunicationThread(this, socket);
                communicationThread.start();
            }
        } catch (ClientProtocolException clientProtocolException) {
            Log.e(Constants.TAG, "[SERVER THREAD] An exception has occurred: " + clientProtocolException.getMessage());
                clientProtocolException.printStackTrace();
        } catch (IOException ioException) {
            Log.e(Constants.TAG, "[SERVER THREAD] An exception has occurred: " + ioException.getMessage());
                ioException.printStackTrace();
        }
    }

    public BitcoinData getData() {
        return this.data;
    }
}
