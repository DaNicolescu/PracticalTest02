package ro.pub.cs.systems.eim.practicaltest02;

import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends Thread {
    private String address;
    private int port;
    private String currencyType;
    private TextView textView;

    private Socket socket;

    public ClientThread(String address, int port, String currencyType, TextView textView) {
        this.address = address;
        this.port = port;
        this.currencyType = currencyType;
        this.textView = textView;
    }

    @Override
    public void run() {
        try {
            socket = new Socket(address, port);
            if (socket == null) {
                Log.e(Constants.TAG, "[CLIENT THREAD] Could not create socket!");
                return;
            }
            BufferedReader bufferedReader = Utilities.getReader(socket);
            PrintWriter printWriter = Utilities.getWriter(socket);
            if (bufferedReader == null || printWriter == null) {
                Log.e(Constants.TAG, "[CLIENT THREAD] Buffered Reader / Print Writer are null!");
                return;
            }

            printWriter.println(currencyType);
            printWriter.flush();

            String information;
            while ((information = bufferedReader.readLine()) != null) {
                final String finalizedInformation = information;
                textView.post(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(finalizedInformation);
                    }
                });
            }
        } catch (IOException ioException) {

        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException ioException) {
                    Log.e(Constants.TAG, "[CLIENT THREAD] An exception has occurred: " + ioException.getMessage());
                    ioException.printStackTrace();
                }
            }
        }
    }
}
