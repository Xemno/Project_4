package ch.ethz.inf.vs.a2.ankoller.webservices.sensor;

import android.renderscript.ScriptGroup;

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
    public static final String SENSOR_PATH= "/sunspots/Spot1/sensors/temperature";
    @Override
    //similar to textsensor also crete url connection but another head
    //headerfile for accept is json
    public String executeRequest() throws Exception {
    URL url= new URL("http://" + HOST + ":" + REST_PORT + SENSOR_PATH);
        HttpURLConnection httpURLConnection= (HttpURLConnection)url.openConnection();
        httpURLConnection.setRequestProperty("Accept", "application/json");

        String response="";
        try{
            InputStream inputStream= new BufferedInputStream(httpURLConnection.getInputStream());
            BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(inputStream));
            String responsetemp;
            while((responsetemp= bufferedReader.readLine())!= null){
                response += "\n" + responsetemp;
            }
            bufferedReader.close();
        }finally {
            httpURLConnection.disconnect();
        }

        return response;
    }

    @Override
    public double parseResponse(String response) {

        double result;
        try{
            JSONObject jsonObject= new JSONObject(response);
            result= jsonObject.getDouble("value");


        }catch (Exception e){
            result=Double.NaN;

        }
        return result;
    }

    @Override
    public void getTemperature() {

    }

    @Override
    public void registerListener(SensorListener listener) {

    }

    @Override
    public void unregisterListener(SensorListener listener) {

    }
}
