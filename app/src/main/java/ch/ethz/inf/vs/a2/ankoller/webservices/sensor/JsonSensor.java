package ch.ethz.inf.vs.a2.ankoller.webservices.sensor;

import android.renderscript.ScriptGroup;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import ch.ethz.inf.vs.a2.ankoller.webservices.http.RemoteServerConfiguration;

import static ch.ethz.inf.vs.a2.ankoller.webservices.http.RemoteServerConfiguration.HOST;
import static ch.ethz.inf.vs.a2.ankoller.webservices.http.RemoteServerConfiguration.REST_PORT;

/**
 * Created by anja on 13.10.2017.
 */
public class JsonSensor extends AbstractSensor implements RemoteServerConfiguration {

    private static final String TAG = "HttpRawSensor > Log";

    public static final String SENSOR_PATH = "/sunspots/Spot1/sensors/temperature";

    @Override
    public String executeRequest() throws Exception {

        Log.d(TAG, "executing request...");

        URL url = new URL("http://" + HOST + ":" + REST_PORT + SENSOR_PATH);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestProperty("Accept", "application/json");

        String response = "";

        try {
            InputStream is = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            String temp;
            while ((temp = reader.readLine()) != null) {
                response += "\n" + temp;
            }

            reader.close();
        } finally {
            urlConnection.disconnect();
        }

        return response;
    }

    @Override
    public double parseResponse(String response) {

        Log.d(TAG, "JsonSensor response gotten: " + response);

        double result;

        try {
            JSONObject json = new JSONObject(response);
            result = json.getDouble("value");
        } catch (JSONException e) {
            Log.d(TAG, "Json exception occurred: " + e.toString());
            result = Double.NaN;
        }
        return result;
    }
}

