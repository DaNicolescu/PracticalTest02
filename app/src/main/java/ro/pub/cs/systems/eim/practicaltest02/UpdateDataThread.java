package ro.pub.cs.systems.eim.practicaltest02;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.util.EntityUtils;

public class UpdateDataThread extends Thread {
    private ServerThread serverThread;

    public UpdateDataThread(ServerThread serverThread) {
        this.serverThread = serverThread;
    }

    @Override
    public void run() {
        BitcoinData data = serverThread.getData();

        while (true) {
            try {
                Log.i(Constants.TAG, "Getting the information from the webservice...");
                HttpClient httpClient = new DefaultHttpClient();
                String pageSourceCode = "";

                HttpGet httpGet = new HttpGet("https://api.coindesk.com/v1/bpi/currentprice/EUR.json");

                HttpResponse httpGetResponse = httpClient.execute(httpGet);
                HttpEntity httpGetEntity = httpGetResponse.getEntity();

                if (httpGetEntity != null) {
                    pageSourceCode = EntityUtils.toString(httpGetEntity);
                }

                if (pageSourceCode == null) {
                    Log.e(Constants.TAG, "Error getting the information from the webservice!");
                    return;
                } else
                    Log.i(Constants.TAG, pageSourceCode );

                JSONObject content = new JSONObject(pageSourceCode);
                JSONObject bpi = content.getJSONObject("bpi");
                JSONObject usd = bpi.getJSONObject("USD");
                JSONObject eur = bpi.getJSONObject("EUR");

                String eurRate = eur.getString("rate");
                String usdRate = usd.getString("rate");

                data.setEur(eurRate);
                data.setUsd(usdRate);

                Thread.sleep(60000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
