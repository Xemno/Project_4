package ch.ethz.inf.vs.a2.ankoller.webservices.sensor;

import android.util.Log;

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

public class TextSensor extends AbstractSensor implements RemoteServerConfiguration {
 //implement missing methods
    //create requests by invoking openConnection()
    //on a java url object
    //set header fields with setRequestProperty(header,value) on the HTTPURLConnection

    private static final String TAG = "TextSensor > Log";

    private static final String SENSOR_PATH = "/sunspots/Spot1/sensors/temperature";

    @Override
    public String executeRequest() throws Exception {
        //create a url
        URL url = new URL("http://" + HOST + ":" + REST_PORT + SENSOR_PATH);
       //create a HttpURLconnection object
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestProperty("Accept", "text/plain");
        //inputStream add and separate with \n and at the end disconnect

        String response = "";

        try {
            InputStream is = new BufferedInputStream(httpURLConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            String temp;
            while ((temp = reader.readLine()) != null) {
                response += "\n" + temp;
            }

            reader.close();
        } finally {
            httpURLConnection.disconnect();
        }

        return response;
    }
    //sent and receive url connection and at end dissconnect, new line for every header

    @Override
    public double parseResponse(String response) {
        Log.d(TAG, "TextSensor response gotten: " + response);

        double result;
        try{
            result = Double.valueOf(response);
        }catch(Exception e){
            result = Double.NaN;
        }
        return result;
    }

}

