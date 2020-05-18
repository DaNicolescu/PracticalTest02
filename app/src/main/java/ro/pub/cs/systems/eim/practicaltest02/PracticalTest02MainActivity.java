package ro.pub.cs.systems.eim.practicaltest02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

public class PracticalTest02MainActivity extends AppCompatActivity {

    private EditText serverPort;
    private Button serverButton;

    private EditText clientAddress;
    private EditText clientPort;
    private Spinner currencyType;
    private Button clientButton;

    private TextView textView;

    private ServerThread serverThread;

    private ServerButtonListener serverButtonListener = new ServerButtonListener();
    private class ServerButtonListener implements Button.OnClickListener {

        @Override
        public void onClick(View view) {
            String serverPortString = serverPort.getText().toString();
            if (serverPortString == null) {
                Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] Server port should be filled!", Toast.LENGTH_SHORT).show();
                return;
            }
            serverThread = new ServerThread(Integer.parseInt(serverPortString));

            serverThread.start();
        }

    }

    private ClientButtonListener clientButtonListener = new ClientButtonListener();
    private class ClientButtonListener implements Button.OnClickListener {

        @Override
        public void onClick(View view) {
            String clientAddressString = clientAddress.getText().toString();
            String clientPortString = clientPort.getText().toString();

            String informationType = currencyType.getSelectedItem().toString();

            textView.setText("");

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test02_main);

        this.serverPort = (EditText)findViewById(R.id.server_port);
        this.serverButton = (Button)findViewById(R.id.server_start);

        this.serverButton.setOnClickListener(serverButtonListener);

        this.clientAddress = (EditText)findViewById(R.id.client_address);
        this.clientPort = (EditText)findViewById(R.id.client_port);

        this.currencyType = (Spinner)findViewById(R.id.currency_type);

        this.clientButton = (Button)findViewById(R.id.client_start);

        this.clientButton.setOnClickListener(clientButtonListener);

        this.textView = (TextView) findViewById(R.id.text_view);
    }
}
