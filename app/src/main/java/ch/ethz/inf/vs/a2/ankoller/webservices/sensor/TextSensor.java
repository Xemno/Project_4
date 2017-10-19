package ch.ethz.inf.vs.a2.ankoller.webservices.sensor;

import java.net.HttpURLConnection;
import java.net.URL;

import static ch.ethz.inf.vs.a2.ankoller.webservices.http.RemoteServerConfiguration.HOST;
import static ch.ethz.inf.vs.a2.ankoller.webservices.http.RemoteServerConfiguration.REST_PORT;

/**
 * Created by anja on 13.10.2017.
 */

class TextSensor extends AbstractSensor implements Sensor {
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
      //TODO inputstream add and separate with \n and at end disconnect
        return response;
    }

    @Override
    public double parseResponse(String response) {
        return 0;
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
