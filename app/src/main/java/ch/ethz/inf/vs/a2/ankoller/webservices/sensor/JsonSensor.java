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

    public static final String SENSOR_PATH = "/sunspots/Spot1/sensors/temperature";

    @Override
    public String executeRequest() throws Exception {

        URL url = new URL("http://" + HOST + ":" + REST_PORT + SENSOR_PATH);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestProperty("Accept", "application/json");

        String response = "";

        try {
            //similart to textsensor also create url connection but another headderfile
            //similary create input and outputstram and open and close connection
            InputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String temp;
            while ((temp = bufferedReader.readLine()) != null) {
                response += "\n" + temp;
            }

            bufferedReader.close();
        } finally {
            httpURLConnection.disconnect();
        }

        return response;
    }

    @Override
    public double parseResponse(String response) {

        double result;

        try {
            JSONObject jsonObject = new JSONObject(response);
            result = jsonObject.getDouble("value");
        } catch (JSONException e) {
            result = Double.NaN;
        }
        return result;
    }
}

