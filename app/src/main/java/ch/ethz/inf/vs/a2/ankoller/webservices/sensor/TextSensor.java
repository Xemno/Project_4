package ch.ethz.inf.vs.a2.ankoller.webservices.sensor;

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
    //create requests by invoking openConnection() on a java.net.URL object
    //set header fields with setRequestProperty(header,value) on the
    //HttpURLConnection object
private static final String SENSOR_PATH = "/sunspots/Spot1/sensors/temperature";
    @Override
    public String executeRequest() throws Exception {
        //create a URL
        URL url= new URL("http://"+ HOST+ ":" + REST_PORT + SENSOR_PATH);
        //create a HTTPURLConnection object
        HttpURLConnection urlConnection= (HttpURLConnection)url.openConnection();
        //set header fields
        urlConnection.setRequestProperty("Accept", "text/plain");
       String response= "";
      // inputstream add and separate with \n and at end disconnect
        try{
            InputStream inputStream= new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(inputStream));
            String responsestring;
            while((responsestring=bufferedReader.readLine())!=null){
                response +="\n" + responsestring;
            }
            bufferedReader.close();
        } finally{
            urlConnection.disconnect();
        }
        return response;
    }
    //sent and receive the url connection and at the end disconnect, after every
    //header we have a newline


    @Override
    public double parseResponse(String response) {
        //similar to RawHttpSensor
        double result;
        try{
            result=Double.valueOf(response);
        }catch(Exception e){
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
